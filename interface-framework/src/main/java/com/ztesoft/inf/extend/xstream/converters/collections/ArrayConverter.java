/*    */ package com.ztesoft.inf.extend.xstream.converters.collections;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArrayConverter extends AbstractCollectionConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 34 */     return type.isArray();
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 40 */     int length = Array.getLength(source);
/* 41 */     for (int i = 0; i < length; i++) {
/* 42 */       Object item = Array.get(source, i);
/* 43 */       writeItem(item, context, writer);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/* 52 */     List items = new ArrayList();
/* 53 */     while (reader.hasMoreChildren()) {
/* 54 */       reader.moveDown();
/* 55 */       Object item = readItem(reader, context, null);
/*    */ 
/* 58 */       items.add(item);
/* 59 */       reader.moveUp();
/*    */     }
/*    */ 
/* 64 */     Object array = Array.newInstance(context.getRequiredType().getComponentType(), items.size());
/*    */ 
/* 66 */     int i = 0;
/* 67 */     for (Iterator iterator = items.iterator(); iterator.hasNext(); ) {
/* 68 */       Array.set(array, i++, iterator.next());
/*    */     }
/* 70 */     return array;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.collections.ArrayConverter
 * JD-Core Version:    0.6.2
 */