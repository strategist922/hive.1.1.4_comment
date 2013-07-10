package com.taobao.base64;

public abstract interface Decoder
{
  public abstract Object decode(Object paramObject)
    throws DecoderException;
}