package org.apache.hadoop.hive.ql.exec;


public class TimedOutChildProcess extends Thread {

  private final int EXIT_ABNORMAL = -1;

  private final Process process;

  private int exitVal;

  /**
   * Thread Constructor
   * @param process The child process to run
   */
  public TimedOutChildProcess(Process process) {
    this.process = process;
    this.exitVal = EXIT_ABNORMAL;
  }

  @Override
  public void run() {
    try {
      exitVal = process.waitFor();
    } catch (InterruptedException e) {
      e.printStackTrace();
      exitVal = EXIT_ABNORMAL;
    }
  }

  /**
   * Get exit value
   * @return exit value, or -1 for abnormal exit
   */
  public int getExitVal() {
    return exitVal;
  }
}
