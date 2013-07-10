package org.apache.hadoop.hive.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.protocol.TProtocol;

public class SequentialHiveClientInvocationHandler implements InvocationHandler {

  private final HiveClient genuineHiveClient;
  private static final List<Method> interfaceMethods =
    Arrays.asList(HiveInterface.class.getMethods());

  public SequentialHiveClientInvocationHandler(TProtocol prot) {
    genuineHiveClient = new HiveClient(prot);
  }

  private boolean isInterfaceMethod(Method method) {
    return interfaceMethods.contains(method);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (isInterfaceMethod(method)) {
      synchronized (genuineHiveClient) {
        return method.invoke(genuineHiveClient, args);
      }
    } else {
      return method.invoke(genuineHiveClient, args);
    }
  }

}
