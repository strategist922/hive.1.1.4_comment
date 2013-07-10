package com.taobao.base64;

public abstract interface BinaryEncoder extends Encoder
{
  public abstract byte[] encode(byte[] paramArrayOfByte)
    throws EncoderException;
}