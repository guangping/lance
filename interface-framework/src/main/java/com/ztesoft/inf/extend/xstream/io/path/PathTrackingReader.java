/*    */ package com.ztesoft.inf.extend.xstream.io.path;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.ReaderWrapper;
/*    */ 
/*    */ public class PathTrackingReader extends ReaderWrapper
/*    */ {
/*    */   private final PathTracker pathTracker;
/*    */ 
/*    */   public PathTrackingReader(HierarchicalStreamReader reader, PathTracker pathTracker)
/*    */   {
/* 33 */     super(reader);
/* 34 */     this.pathTracker = pathTracker;
/* 35 */     pathTracker.pushElement(getNodeName());
/*    */   }
/*    */ 
/*    */   public void moveDown()
/*    */   {
/* 40 */     super.moveDown();
/* 41 */     this.pathTracker.pushElement(getNodeName());
/*    */   }
/*    */ 
/*    */   public void moveUp()
/*    */   {
/* 46 */     super.moveUp();
/* 47 */     this.pathTracker.popElement();
/*    */   }
/*    */ 
/*    */   public void appendErrors(ErrorWriter errorWriter)
/*    */   {
/* 52 */     errorWriter.add("path", this.pathTracker.getPath().toString());
/* 53 */     super.appendErrors(errorWriter);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.path.PathTrackingReader
 * JD-Core Version:    0.6.2
 */