/*    */ package com.ztesoft.inf.extend.xstream.io.path;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import com.ztesoft.inf.extend.xstream.io.WriterWrapper;
/*    */ import com.ztesoft.inf.extend.xstream.io.xml.XmlFriendlyWriter;
/*    */ 
/*    */ public class PathTrackingWriter extends WriterWrapper
/*    */ {
/*    */   private final PathTracker pathTracker;
/*    */   private final boolean isXmlFriendly;
/*    */ 
/*    */   public PathTrackingWriter(HierarchicalStreamWriter writer, PathTracker pathTracker)
/*    */   {
/* 34 */     super(writer);
/* 35 */     this.isXmlFriendly = (writer.underlyingWriter() instanceof XmlFriendlyWriter);
/* 36 */     this.pathTracker = pathTracker;
/*    */   }
/*    */ 
/*    */   public void startNode(String name)
/*    */   {
/* 41 */     this.pathTracker.pushElement(this.isXmlFriendly ? ((XmlFriendlyWriter)this.wrapped.underlyingWriter()).escapeXmlName(name) : name);
/*    */ 
/* 43 */     super.startNode(name);
/*    */   }
/*    */ 
/*    */   public void startNode(String name, Class clazz)
/*    */   {
/* 48 */     this.pathTracker.pushElement(this.isXmlFriendly ? ((XmlFriendlyWriter)this.wrapped.underlyingWriter()).escapeXmlName(name) : name);
/*    */ 
/* 50 */     super.startNode(name, clazz);
/*    */   }
/*    */ 
/*    */   public void endNode()
/*    */   {
/* 55 */     super.endNode();
/* 56 */     this.pathTracker.popElement();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.path.PathTrackingWriter
 * JD-Core Version:    0.6.2
 */