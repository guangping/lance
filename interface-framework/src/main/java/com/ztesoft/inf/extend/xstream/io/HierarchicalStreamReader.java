package com.ztesoft.inf.extend.xstream.io;

import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
import java.util.Iterator;

public abstract interface HierarchicalStreamReader
{
  public abstract boolean hasMoreChildren();

  public abstract void moveDown();

  public abstract void moveUp();

  public abstract String getNodeName();

  public abstract String getValue();

  public abstract String getAttribute(String paramString);

  public abstract String getAttribute(int paramInt);

  public abstract int getAttributeCount();

  public abstract String getAttributeName(int paramInt);

  public abstract Iterator getAttributeNames();

  public abstract void appendErrors(ErrorWriter paramErrorWriter);

  public abstract void close();

  public abstract HierarchicalStreamReader underlyingReader();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader
 * JD-Core Version:    0.6.2
 */