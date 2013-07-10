package com.taobao.hive.util;

public class FastSearch
{
  private static final int STRINGLIB_BLOOM_WIDTH = 64;

  private static final void STRINGLIB_BLOOM_ADD(long mask, byte ch)
  {
    mask |= 1L << (ch & 0x3F);
  }

  private static final long STRINGLIB_BLOOM(long mask, byte ch)
  {
    return mask & 1L << (ch & 0x3F);
  }

  public static int fastSearch(byte[] s, int n, byte[] p, int m, int maxcount, Mode mode)
  {
    int count = 0;

    int w = n - m;

    if ((w < 0) || ((mode == Mode.FAST_COUNT) && (maxcount == 0))) {
      return -1;
    }

    if (m <= 1) {
      if (m <= 0) {
        return -1;
      }
      if (mode == Mode.FAST_COUNT) {
        for (int i = 0; i < n; i++)
          if (s[i] == p[0]) {
            count++;
            if (count == maxcount)
              return maxcount;
          }
        return count;
      }if (mode == Mode.FAST_SEARCH) {
        for (int i = 0; i < n; i++)
          if (s[i] == p[0])
            return i;
      }
      for (int i = n - 1; i > -1; i--) {
        if (s[i] == p[0])
          return i;
      }
      return -1;
    }

    int mlast = m - 1;
    int skip = mlast - 1;
    long mask = 0L;

    if (mode != Mode.FAST_RSEARCH)
    {
      for (int i = 0; i < mlast; i++) {
        STRINGLIB_BLOOM_ADD(mask, p[i]);
        if (p[i] == p[mlast]) {
          skip = mlast - i - 1;
        }
      }
      STRINGLIB_BLOOM_ADD(mask, p[mlast]);

      for (i = 0; i <= w; i++)
      {
        if (s[(i + m - 1)] == p[(m - 1)])
        {
          for (int j = 0; (j < mlast) && 
            (s[(i + j)] == p[j]); j++);
          if (j == mlast)
          {
            if (mode != Mode.FAST_COUNT)
              return i;
            count++;
            if (count == maxcount)
              return maxcount;
            i += mlast;
          }
          else if ((i + m < n) && (STRINGLIB_BLOOM(mask, s[(i + m)]) != 0L)) {
            i += m;
          } else {
            i += skip;
          }
        }
        else if ((i + m < n) && (STRINGLIB_BLOOM(mask, s[(i + m)]) != 0L)) {
          i += m;
        }

      }

    }

    STRINGLIB_BLOOM_ADD(mask, p[0]);

    for (int i = mlast; i > 0; i--) {
      STRINGLIB_BLOOM_ADD(mask, p[i]);
      if (p[i] == p[0]) {
        skip = i - 1;
      }
    }
    for (i = w; i >= 0; i--) {
      if (s[i] == p[0])
      {
        for (int j = mlast; (j > 0) && 
          (s[(i + j)] == p[j]); j--);
        if (j == 0)
        {
          return i;
        }
        if ((i - 1 > -1) && (STRINGLIB_BLOOM(mask, s[(i - 1)]) != 0L))
          i -= m;
        else {
          i -= skip;
        }
      }
      else if ((i - 1 > -1) && (STRINGLIB_BLOOM(mask, s[(i - 1)]) != 0L)) {
        i -= m;
      }

    }

    if (mode != Mode.FAST_COUNT)
      return -1;
    return count;
  }

  public static enum Mode
  {
    FAST_COUNT, FAST_SEARCH, FAST_RSEARCH;
  }
}