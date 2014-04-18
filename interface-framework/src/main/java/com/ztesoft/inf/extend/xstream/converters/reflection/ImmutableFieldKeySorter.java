/*    */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ImmutableFieldKeySorter
/*    */   implements FieldKeySorter
/*    */ {
/*    */   public Map sort(Class type, Map keyedByFieldKey)
/*    */   {
/* 24 */     return keyedByFieldKey;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.ImmutableFieldKeySorter
 * JD-Core Version:    0.6.2
 */