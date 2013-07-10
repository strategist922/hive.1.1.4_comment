package org.apache.hadoop.hive.ql.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.InvalidUserException;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

public class GenericAuthenticator implements Authenticator {

  private static MessageDigest digest = null;

  static {
    try {
      digest = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  /**
   * Log in with local authentication
   *
   * @return
   */
  @Override
  public boolean login(SessionState ss) {
    LogHelper console = SessionState.getConsole();
    String userName = ss.getUserName();
    String password = ss.getPassword();

    if (userName == null || password == null ||
        userName.isEmpty() || password.isEmpty()) {
      console.printError("Login failed: User name or password not specified.");
      return false;
    }

    org.apache.hadoop.hive.ql.metadata.User user = null;


    digest.reset();
    digest.update(password.getBytes());
    byte[] buffer = digest.digest();

    try {
      user = ss.getDb().getUser(userName, true);
    } catch (InvalidUserException iue) {
      console.printError("User " + userName + " does not exist");
      return false;
    } catch (HiveException he) {
      console.printError("Exception raised from " +
          org.apache.hadoop.util.StringUtils.stringifyException(he));
      return false;
    }
    byte[] realPassword = user.getPassword();

    boolean passed = MessageDigest.isEqual(realPassword, buffer);
    if (!passed) {
      console.printError("Login failed: Invalid user name or password");
    }
    return passed;
  }

  /**
   * Log in with remote authentication
   *
   * @return
   */
  @Override
  public boolean login(SessionState ss, TProtocol protocol) {
    LogHelper console = SessionState.getConsole();
    String userName = ss.getUserName();
    String password = ss.getPassword();

    if (userName == null || password == null ||
        userName.isEmpty() || password.isEmpty()) {
      console.printError("Login failed: User name or password not specified.");
      return false;
    }

    TTransport trans = protocol.getTransport();

    // open connection
    try {
      trans.open();

      // fetch scramble from server which is a string encrypted by sha1
      byte[] scramble = protocol.readBinary();

      // calculate value
      byte[] value = null;
      digest.reset();
      digest.update(password.getBytes());
      value = digest.digest();
      digest.reset();
      digest.update(value);
      digest.update(scramble);
      value = digest.digest();

      // send value back to server
      protocol.writeString(userName);
      protocol.writeBinary(value);
      trans.flush();
      return protocol.readBool();
    } catch (TException e) {
      console.printError("Thrift communication failed for: " +
          org.apache.hadoop.util.StringUtils.stringifyException(e));
      return false;
    }
  }

  @Override
  public Configuration getConf() {
    return null;
  }

  @Override
  public void setConf(Configuration conf) {
  }

  @Override
  public boolean validate(SessionState ss, TProtocol protocol) {
    TTransport trans = protocol.getTransport();
    boolean passed = false;
    // generate a random long
    long rand = new Random(System.currentTimeMillis()).nextLong();

    digest.reset();
    digest.update(String.valueOf(rand).getBytes());
    // write scrmable to client;
    byte[] curScramble = digest.digest();

    try {
      protocol.writeBinary(curScramble);
      trans.flush();

      // read user name and buff
      String userName = protocol.readString();
      byte[] buff = protocol.readBinary();

      // retrieve the real password
      org.apache.hadoop.hive.ql.metadata.User user = null;
      try {
        user = ss.getDb().getUser(userName, true);
      } catch (InvalidUserException e) {
        protocol.writeBool(passed);
        trans.flush();
        throw new TException("Invalid user: " + userName, e);
      } catch (HiveException e) {
        protocol.writeBool(passed);
        trans.flush();
        throw new TException(e);
      }
      byte[] passwd = user.getPassword();
      digest.reset();
      digest.update(passwd);
      digest.update(curScramble);

      // verify it
      passed = MessageDigest.isEqual(digest.digest(), buff);
      protocol.writeBool(passed);
      trans.flush();
      if (passed) {
        ss.setUserName(userName);
      }
    } catch (TException e) {
      e.printStackTrace();
      trans.close();
    }

    return passed;
  }
}
