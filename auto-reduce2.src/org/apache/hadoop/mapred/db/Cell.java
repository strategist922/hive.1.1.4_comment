package org.apache.hadoop.mapred.db;

public class Cell
{
  private Type type;
  private String name;
  private String value;

  public Cell(String name)
  {
    setType(Type.STRING);
    setName(name);
    setVal("");
  }
  public Cell(Type type, String name, String value) {
    setType(type);
    setName(name);
    setVal(value);
  }

  public Cell(String name, String value) {
    setType(Type.STRING);
    setName(name);
    setVal(value);
  }

  public Cell(String name, int value) {
    setType(Type.NUMBER);
    setName(name);
    setVal(String.valueOf(value));
  }

  public Cell(String name, long value) {
    setType(Type.NUMBER);
    setName(name);
    setVal(String.valueOf(value));
  }

  public void setType(Type type) {
    this.type = type;
  }
  public Type getType() {
    return this.type;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return this.name;
  }
  public void setVal(String colVal) {
    this.value = colVal;
  }
  public String getVal() {
    return this.value;
  }

  public static enum Type
  {
    NUMBER, 
    STRING, 
    FUNCTION;
  }
}