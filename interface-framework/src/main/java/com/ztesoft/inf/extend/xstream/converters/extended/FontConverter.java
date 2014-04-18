/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import java.awt.Font;
/*    */ import java.util.Map;
/*    */ import javax.swing.plaf.FontUIResource;
/*    */ 
/*    */ public class FontConverter
/*    */   implements Converter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 31 */     return (type.getName().equals("java.awt.Font")) || (type.getName().equals("javax.swing.plaf.FontUIResource"));
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 37 */     Font font = (Font)source;
/* 38 */     Map attributes = font.getAttributes();
/* 39 */     writer.startNode("attributes");
/* 40 */     context.convertAnother(attributes);
/* 41 */     writer.endNode();
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/* 46 */     reader.moveDown();
/* 47 */     Map attributes = (Map)context.convertAnother(null, Map.class);
/* 48 */     reader.moveUp();
/* 49 */     Font font = Font.getFont(attributes);
/* 50 */     if (context.getRequiredType() == FontUIResource.class) {
/* 51 */       return new FontUIResource(font);
/*    */     }
/* 53 */     return font;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.FontConverter
 * JD-Core Version:    0.6.2
 */