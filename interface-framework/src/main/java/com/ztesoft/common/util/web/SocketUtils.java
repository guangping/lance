/*    */ package com.ztesoft.common.util.web;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class SocketUtils
/*    */ {
/*    */   public static Socket newClient(int soLinger, int timeout, int sendBuffer, int recBuffer)
/*    */     throws Exception
/*    */   {
/* 13 */     Socket socket = new Socket();
/* 14 */     socket.setSoLinger(true, soLinger);
/* 15 */     socket.setSoTimeout(timeout);
/* 16 */     socket.setSendBufferSize(sendBuffer);
/* 17 */     socket.setReceiveBufferSize(recBuffer);
/* 18 */     socket.setTcpNoDelay(true);
/* 19 */     return socket;
/*    */   }
/*    */   public static String sendAndRecieve(Socket socket, String reqString) throws Exception {
/* 22 */     PrintWriter out = null;
/* 23 */     BufferedReader in = null;
/*    */     try {
/* 25 */       out = new PrintWriter(socket.getOutputStream());
/* 26 */       out.println(reqString);
/* 27 */       out.flush();
/* 28 */       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/* 29 */       int bufSize = 4096;
/* 30 */       StringWriter received = new StringWriter(bufSize);
/* 31 */       char[] charBuf = new char[bufSize];
/* 32 */       int size = 0;
/*    */       do {
/* 34 */         size = in.read(charBuf, 0, bufSize);
/* 35 */         if (size > 0)
/* 36 */           received.write(charBuf, 0, size);
/*    */       }
/* 38 */       while (size == bufSize);
/* 39 */       return received.toString();
/*    */     } finally {
/* 41 */       if (out != null)
/*    */         try {
/* 43 */           out.close();
/*    */         }
/*    */         catch (Exception ex) {
/*    */         }
/* 47 */       if (in != null)
/*    */         try {
/* 49 */           in.close();
/*    */         }
/*    */         catch (Exception ex)
/*    */         {
/*    */         }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.web.SocketUtils
 * JD-Core Version:    0.6.2
 */