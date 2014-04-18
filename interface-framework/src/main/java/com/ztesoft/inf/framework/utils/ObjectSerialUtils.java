/*    */ package com.ztesoft.inf.framework.utils;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ import sun.misc.BASE64Decoder;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class ObjectSerialUtils
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 18 */     ObjectSerialUtils bo = new ObjectSerialUtils();
/* 19 */     HashMap vo = new HashMap();
/* 20 */     vo.put("test1", "1");
/* 21 */     vo.put("test2", "2");
/* 22 */     String c = bo.writeObjectToString(vo);
/* 23 */     Object vo1 = bo.readStringToObject(c);
/* 24 */     System.out.print(vo1);
/*    */   }
/*    */ 
/*    */   public Object readStringToObject(String s) throws IOException, ClassNotFoundException {
/* 28 */     ObjectInputStream ois = null;
/*    */     try {
/* 30 */       String _s = s;
/* 31 */       byte[] data = getByteFromBASE64(_s);
/* 32 */       ois = new ObjectInputStream(new ByteArrayInputStream(data));
/* 33 */       Object o = ois.readObject();
/* 34 */       return o;
/*    */     } finally {
/*    */       try {
/* 37 */         ois.close();
/*    */       } catch (Exception e) {
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public String writeObjectToString(Object obj) throws IOException {
/* 44 */     ByteArrayOutputStream baos = null;
/* 45 */     ObjectOutputStream oos = null;
/*    */     try {
/* 47 */       baos = new ByteArrayOutputStream();
/* 48 */       oos = new ObjectOutputStream(baos);
/* 49 */       oos.writeObject(obj);
/* 50 */       return getBASE64(baos.toByteArray());
/*    */     }
/*    */     finally {
/* 53 */       baos.close();
/* 54 */       oos.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getBASE64(byte[] inByte)
/*    */   {
/* 62 */     BASE64Encoder enc = new BASE64Encoder();
/* 63 */     String strObj = enc.encode(inByte);
/* 64 */     return strObj;
/*    */   }
/*    */   public static byte[] getByteFromBASE64(String s) {
/* 67 */     if (s == null) return null;
/* 68 */     BASE64Decoder decoder = new BASE64Decoder();
/*    */     try {
/* 70 */       return decoder.decodeBuffer(s);
/*    */     } catch (Exception e) {
/*    */     }
/* 73 */     return null;
/*    */   }
/*    */ 
/*    */   public static String getBASE64(String s)
/*    */   {
/* 78 */     if (s == null) return null;
/* 79 */     return new BASE64Encoder().encode(s.getBytes());
/*    */   }
/*    */   public static String getFromBASE64(String s) {
/* 82 */     if (s == null) return null;
/* 83 */     BASE64Decoder decoder = new BASE64Decoder();
/*    */     try {
/* 85 */       byte[] b = decoder.decodeBuffer(s);
/* 86 */       return new String(b); } catch (Exception e) {
/*    */     }
/* 88 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.ObjectSerialUtils
 * JD-Core Version:    0.6.2
 */