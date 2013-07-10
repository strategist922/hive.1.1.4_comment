package com.taobao.base64;

public abstract interface BinaryDecoder extends Decoder
{
  public abstract byte[] decode(byte[] paramArrayOfByte)
    throws DecoderException;
}