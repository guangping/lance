package com.ztesoft.inf.extend.xstream.io;

public abstract interface HierarchicalStreamWriter
{
  public abstract void startNode(String paramString);

  public abstract void addAttribute(String paramString1, String paramString2);

  public abstract void setValue(String paramString);

  public abstract void endNode();

  public abstract void flush();

  public abstract void close();

  public abstract HierarchicalStreamWriter underlyingWriter();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter
 * JD-Core Version:    0.6.2
 */