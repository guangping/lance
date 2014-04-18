/*    */ package com.ztesoft.common.util.convert;
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
/* 23 */     ObjectSerialUtils bo = new ObjectSerialUtils();
/* 24 */     HashMap vo = new HashMap();
/* 25 */     vo.put("test1", "1");
/* 26 */     vo.put("test2", "2");
/* 27 */     String c = bo.writeObjectToString(vo);
/* 28 */     Object vo1 = bo.readStringToObject(c);
/* 29 */     System.out.print(vo1);
/*    */   }
/*    */ 
/*    */   public Object readStringToObject(String s) throws IOException, ClassNotFoundException {
/* 33 */     ObjectInputStream ois = null;
/*    */     try {
/* 35 */       String _s = s;
/* 36 */       byte[] data = getByteFromBASE64(_s);
/* 37 */       ois = new ObjectInputStream(new ByteArrayInputStream(data));
/* 38 */       Object o = ois.readObject();
/* 39 */       return o;
/*    */     } finally {
/* 41 */       ois.close();
/*    */     }
/*    */   }
/*    */ 
/* 45 */   public String writeObjectToString(Object obj) throws IOException { ByteArrayOutputStream baos = null;
/* 46 */     ObjectOutputStream oos = null;
/*    */     try {
/* 48 */       baos = new ByteArrayOutputStream();
/* 49 */       oos = new ObjectOutputStream(baos);
/* 50 */       oos.writeObject(obj);
/* 51 */       return getBASE64(baos.toByteArray());
/*    */     }
/*    */     finally {
/* 54 */       baos.close();
/* 55 */       oos.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getBASE64(byte[] inByte)
/*    */   {
/* 63 */     BASE64Encoder enc = new BASE64Encoder();
/* 64 */     String strObj = enc.encode(inByte);
/* 65 */     return strObj;
/*    */   }
/*    */   public static byte[] getByteFromBASE64(String s) {
/* 68 */     if (s == null) return null;
/* 69 */     BASE64Decoder decoder = new BASE64Decoder();
/*    */     try {
/* 71 */       return decoder.decodeBuffer(s);
/*    */     } catch (Exception e) {
/*    */     }
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   public static String getBASE64(String s)
/*    */   {
/* 79 */     if (s == null) return null;
/* 80 */     return new BASE64Encoder().encode(s.getBytes());
/*    */   }
/*    */   public static String getFromBASE64(String s) {
/* 83 */     if (s == null) return null;
/* 84 */     BASE64Decoder decoder = new BASE64Decoder();
/*    */     try {
/* 86 */       byte[] b = decoder.decodeBuffer(s);
/* 87 */       return new String(b); } catch (Exception e) {
/*    */     }
/* 89 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.convert.ObjectSerialUtils
 * JD-Core Version:    0.6.2
 */