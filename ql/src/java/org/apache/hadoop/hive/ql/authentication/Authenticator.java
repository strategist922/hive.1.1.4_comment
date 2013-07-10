package org.apache.hadoop.hive.ql.authentication;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.thrift.protocol.TProtocol;

public interface Authenticator extends Configurable {

  /**
   * Local mode login
   * @param ss
   * @return
   */
  public boolean login(SessionState ss);

  /**
   * Remote (thrift) mode login
   * @param ss
   * @param protocol
   * @return
   */
  public boolean login(SessionState ss, TProtocol protocol);

  /**
   * Server side validate
   * @return
   */
  public boolean validate(SessionState ss, TProtocol protocol);

}
