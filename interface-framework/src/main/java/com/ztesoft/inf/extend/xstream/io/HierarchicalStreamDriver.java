package com.ztesoft.inf.extend.xstream.io;

import com.ztesoft.inf.extend.xstream.io.xml.DocumentWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.dom4j.Element;

public abstract interface HierarchicalStreamDriver
{
  public abstract HierarchicalStreamReader createReader(Reader paramReader);

  public abstract HierarchicalStreamReader createReader(InputStream paramInputStream);

  public abstract HierarchicalStreamWriter createWriter(Writer paramWriter);

  public abstract HierarchicalStreamWriter createWriter(OutputStream paramOutputStream);

  public abstract DocumentWriter createDoumentWriter();

  public abstract HierarchicalStreamReader createReader(Element paramElement);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.HierarchicalStreamDriver
 * JD-Core Version:    0.6.2
 */