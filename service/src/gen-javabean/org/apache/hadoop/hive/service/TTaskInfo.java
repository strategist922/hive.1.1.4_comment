/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.hadoop.hive.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import org.apache.log4j.Logger;

import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.protocol.*;

public class TTaskInfo implements TBase, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("TTaskInfo");
  private static final TField INFO_MAP_FIELD_DESC = new TField("infoMap", TType.MAP, (short)1);

  private Map<String,String> infoMap;
  public static final int INFOMAP = 1;

  private final Isset __isset = new Isset();
  private static final class Isset implements java.io.Serializable {
  }

  public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    put(INFOMAP, new FieldMetaData("infoMap", TFieldRequirementType.DEFAULT, 
        new MapMetaData(TType.MAP, 
            new FieldValueMetaData(TType.STRING), 
            new FieldValueMetaData(TType.STRING))));
  }});

  static {
    FieldMetaData.addStructMetaDataMap(TTaskInfo.class, metaDataMap);
  }

  public TTaskInfo() {
  }

  public TTaskInfo(
    Map<String,String> infoMap)
  {
    this();
    this.infoMap = infoMap;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTaskInfo(TTaskInfo other) {
    if (other.isSetInfoMap()) {
      Map<String,String> __this__infoMap = new HashMap<String,String>();
      for (Map.Entry<String, String> other_element : other.infoMap.entrySet()) {

        String other_element_key = other_element.getKey();
        String other_element_value = other_element.getValue();

        String __this__infoMap_copy_key = other_element_key;

        String __this__infoMap_copy_value = other_element_value;

        __this__infoMap.put(__this__infoMap_copy_key, __this__infoMap_copy_value);
      }
      this.infoMap = __this__infoMap;
    }
  }

  @Override
  public TTaskInfo clone() {
    return new TTaskInfo(this);
  }

  public int getInfoMapSize() {
    return (this.infoMap == null) ? 0 : this.infoMap.size();
  }

  public void putToInfoMap(String key, String val) {
    if (this.infoMap == null) {
      this.infoMap = new HashMap<String,String>();
    }
    this.infoMap.put(key, val);
  }

  public Map<String,String> getInfoMap() {
    return this.infoMap;
  }

  public void setInfoMap(Map<String,String> infoMap) {
    this.infoMap = infoMap;
  }

  public void unsetInfoMap() {
    this.infoMap = null;
  }

  // Returns true if field infoMap is set (has been asigned a value) and false otherwise
  public boolean isSetInfoMap() {
    return this.infoMap != null;
  }

  public void setFieldValue(int fieldID, Object value) {
    switch (fieldID) {
    case INFOMAP:
      if (value == null) {
        unsetInfoMap();
      } else {
        setInfoMap((Map<String,String>)value);
      }
      break;

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  public Object getFieldValue(int fieldID) {
    switch (fieldID) {
    case INFOMAP:
      return getInfoMap();

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
  public boolean isSet(int fieldID) {
    switch (fieldID) {
    case INFOMAP:
      return isSetInfoMap();
    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TTaskInfo)
      return this.equals((TTaskInfo)that);
    return false;
  }

  public boolean equals(TTaskInfo that) {
    if (that == null)
      return false;

    boolean this_present_infoMap = true && this.isSetInfoMap();
    boolean that_present_infoMap = true && that.isSetInfoMap();
    if (this_present_infoMap || that_present_infoMap) {
      if (!(this_present_infoMap && that_present_infoMap))
        return false;
      if (!this.infoMap.equals(that.infoMap))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id)
      {
        case INFOMAP:
          if (field.type == TType.MAP) {
            {
              TMap _map0 = iprot.readMapBegin();
              this.infoMap = new HashMap<String,String>(2*_map0.size);
              for (int _i1 = 0; _i1 < _map0.size; ++_i1)
              {
                String _key2;
                String _val3;
                _key2 = iprot.readString();
                _val3 = iprot.readString();
                this.infoMap.put(_key2, _val3);
              }
              iprot.readMapEnd();
            }
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
          break;
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.infoMap != null) {
      oprot.writeFieldBegin(INFO_MAP_FIELD_DESC);
      {
        oprot.writeMapBegin(new TMap(TType.STRING, TType.STRING, this.infoMap.size()));
        for (Map.Entry<String, String> _iter4 : this.infoMap.entrySet())        {
          oprot.writeString(_iter4.getKey());
          oprot.writeString(_iter4.getValue());
        }
        oprot.writeMapEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TTaskInfo(");
    boolean first = true;

    sb.append("infoMap:");
    if (this.infoMap == null) {
      sb.append("null");
    } else {
      sb.append(this.infoMap);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check that fields of type enum have valid values
  }

}

