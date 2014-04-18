package com.ztesoft.inf.extend.xstream.io.xml;

import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import java.util.List;

public abstract interface DocumentWriter extends HierarchicalStreamWriter
{
  public abstract List getTopLevelNodes();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.DocumentWriter
 * JD-Core Version:    0.6.2
 */