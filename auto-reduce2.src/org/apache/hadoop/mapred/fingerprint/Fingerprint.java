package org.apache.hadoop.mapred.fingerprint;

import java.util.ArrayList;
import java.util.List;

public class Fingerprint
{
  private List<Feature> features = new ArrayList();

  public void addFeature(Feature f) {
    this.features.add(f);
  }

  public String toString()
  {
    switch (this.features.size()) {
    case 0:
      return "";
    case 1:
      return ((Feature)this.features.get(0)).getFeature();
    }
    StringBuilder sb = new StringBuilder();
    for (Feature f : this.features) {
      sb.append(f.getFeature());
    }
    return Md5Util.md5sum(sb.toString().getBytes());
  }
}