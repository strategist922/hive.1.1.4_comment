package com.taobao.mrsstd.hiveudf.calculate;

import java.io.PrintStream;

public class Stack<T>
{
  public StackNode<T> stackTop;
  public int count;

  public void push(T info)
  {
    StackNode node = new StackNode();
    node.info = info;
    node.link = this.stackTop;
    this.stackTop = node;
    this.count += 1;
  }

  public void pop() {
    if (this.stackTop == null) {
      System.out.println("null stack");
    } else {
      this.stackTop = this.stackTop.link;
      this.count -= 1;
    }
  }

  public boolean isEmpty()
  {
    return this.count == 0;
  }

  public T top() {
    if (this.stackTop == null) {
      return null;
    }
    return this.stackTop.info;
  }

  public String toString() {
    Stack other = new Stack();
    while (this.count != 0) {
      Object top = top();
      pop();
      other.push(top);
    }
    StringBuffer buf = new StringBuffer();
    while (other.count != 0) {
      buf.append(other.top());
      other.pop();
    }
    return buf.toString();
  }
}