package com.taobao.data.hive.hook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;

public class RDBMSStorageHandler implements StorageHandler<TaskModel>, Configurable {

  private final Log LOG = LogFactory.getLog(RDBMSStorageHandler.class);

  private Configuration conf;
  private final PersistenceManager pm;
  private Transaction currentTransaction;
  private final PersistenceManagerFactory pmf;

  public RDBMSStorageHandler(Configuration conf) {
    this.conf = conf;
    pmf = JDOHelper.getPersistenceManagerFactory(getDataSourceProps());
    pm = pmf.getPersistenceManager();
  }

  private Properties getDataSourceProps() {
    Properties prop = new Properties();
    Iterator<Map.Entry<String, String>> iter = conf.iterator();
    while (iter.hasNext()) {
      Map.Entry<String, String> e = iter.next();
      if (e.getKey().contains("datanucleus") || e.getKey().contains("jdo")) {
        prop.setProperty(e.getKey(), e.getValue());
      }
    }
    return prop;
  }

  @SuppressWarnings("unchecked")
  @Override
  public TaskModel get(TaskModel model) {
    TaskModel tm = null;
    try {
      currentTransaction = pm.currentTransaction();
      currentTransaction.begin();
      Query query = pm.newQuery(MTaskModel.class, "MD5 == md5");
      query.declareParameters("java.lang.String md5");
      Collection<MTaskModel> result =
          (Collection<MTaskModel>) query.execute(model.getMD5());
      Iterator<MTaskModel> iter = result.iterator();
      MTaskModel mtm = null;
      while (iter.hasNext()) {
        mtm = iter.next();
        tm = TaskModel.fromXml(new ByteArrayInputStream(
              mtm.getXMLString().getBytes()));
        assert tm != null : "Task model deserialize failed, when parsing xml:" + mtm.getXMLString();
        if (tm.compareTo(model) == 0) {
          break;
        }
        tm = null;
      }
      currentTransaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (currentTransaction.isActive()) {
        currentTransaction.rollback();
        return null;
      }
    }
    return tm;
  }


  @Override
  public boolean put(TaskModel model) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    TaskModel.toXml(out, model);
    MTaskModel mtm = new MTaskModel(model.getMD5(), out.toString());
    try {
      currentTransaction = pm.currentTransaction();
      currentTransaction.begin();
      pm.makePersistent(mtm);
      currentTransaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (currentTransaction.isActive()) {
        currentTransaction.rollback();
        return false;
      }
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean remove(TaskModel model) {
    TaskModel tm = null;
    boolean success = false;
    try {
      currentTransaction = pm.currentTransaction();
      currentTransaction.begin();
      Query query = pm.newQuery(MTaskModel.class, "MD5 == md5");
      query.declareParameters("java.lang.String md5");
      Collection<MTaskModel> result =
          (Collection<MTaskModel>) query.execute(model.getMD5());
      Iterator<MTaskModel> iter = result.iterator();
      MTaskModel mtm = null;
      while (iter.hasNext()) {
        mtm = (MTaskModel) iter.next();
        tm = TaskModel.fromXml(new ByteArrayInputStream(
              mtm.getXMLString().getBytes()));
        assert tm != null : "Task model deserialize failed, when parsing xml:" + mtm.getXMLString();
        if (tm.compareTo(model) == 0) {
          pm.deletePersistent(mtm);
          success = true;
        }
      }
      currentTransaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (currentTransaction.isActive()) {
        currentTransaction.rollback();
        success = false;
      }
    }
    return success;
  }

  @Override
  public void close() {
    if (currentTransaction != null && currentTransaction.isActive()) {
      currentTransaction.rollback();
    }
    if (pm != null) {
      pm.close();
    }
  }

  @Override
  public Configuration getConf() {
    return conf;
  }

  @Override
  public void setConf(Configuration conf) {
    this.conf = conf;
  }

}
