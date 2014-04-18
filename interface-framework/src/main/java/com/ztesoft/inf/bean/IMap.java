package com.ztesoft.inf.bean;

import java.util.List;
import java.util.Map;

public abstract interface IMap
{
  public abstract String getNodeTag();

  public abstract List<IMap> getSubNodes();

  public abstract void addSubNode(IMap paramIMap);

  public abstract Map convertToMap();
}

