package org.apache.hadoop.hive.ql.hooks;

import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;

public interface CleanHook {
  /**
   * The run command that is called when driver is cleaning.
   *
   * @param response
   *          The response of driver execute.
   * @param context
   *          The query context.
   * @param sess
   *          The session state.
   */
  void run(CommandProcessorResponse response, Context context,
      SessionState ss) throws Exception;
}
