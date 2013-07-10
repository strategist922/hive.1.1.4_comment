package com.taobao.base64;

public abstract interface Encoder
{
  public abstract Object encode(Object paramObject)
    throws EncoderException;
}