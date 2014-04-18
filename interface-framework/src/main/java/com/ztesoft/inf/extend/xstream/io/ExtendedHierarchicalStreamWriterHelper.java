/*    */ package com.ztesoft.inf.extend.xstream.io;
/*    */ 
/*    */ public class ExtendedHierarchicalStreamWriterHelper
/*    */ {
/*    */   public static void startNode(HierarchicalStreamWriter writer, String name, Class clazz)
/*    */   {
/* 17 */     if ((writer instanceof ExtendedHierarchicalStreamWriter))
/* 18 */       ((ExtendedHierarchicalStreamWriter)writer).startNode(name, clazz);
/*    */     else
/* 20 */       writer.startNode(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper
 * JD-Core Version:    0.6.2
 */