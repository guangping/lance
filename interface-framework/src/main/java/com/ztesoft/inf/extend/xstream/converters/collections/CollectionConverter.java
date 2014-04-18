/*    */ package com.ztesoft.inf.extend.xstream.converters.collections;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.core.JVM;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CollectionConverter extends AbstractCollectionConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 41 */     return (type.equals(ArrayList.class)) || (type.equals(HashSet.class)) || (type.equals(LinkedList.class)) || (type.equals(Vector.class)) || ((JVM.is14()) && (type.getName().equals("java.util.LinkedHashSet")));
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 51 */     Collection collection = (Collection)source;
/* 52 */     for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
/* 53 */       Object item = iterator.next();
/* 54 */       writeItem(item, context, writer);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/* 60 */     Collection collection = (Collection)createCollection(context.getRequiredType(), context);
/*    */ 
/* 62 */     populateCollection(reader, context, collection);
/* 63 */     return collection;
/*    */   }
/*    */ 
/*    */   protected void populateCollection(HierarchicalStreamReader reader, UnmarshallingContext context, Collection collection)
/*    */   {
/* 68 */     while (reader.hasMoreChildren()) {
/* 69 */       reader.moveDown();
/* 70 */       Object item = readItem(reader, context, collection);
/* 71 */       collection.add(item);
/* 72 */       reader.moveUp();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.collections.CollectionConverter
 * JD-Core Version:    0.6.2
 */