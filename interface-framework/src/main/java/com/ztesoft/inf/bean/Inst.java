/*     */ package com.ztesoft.inf.bean;
/*     */ 
/*     */ import com.ztesoft.common.util.Clazz;
/*     */ import com.ztesoft.common.util.ListUtil;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.inf.bean.annotation.AttrNodeTag;
/*     */ import com.ztesoft.inf.bean.annotation.ClassAttrTag;
/*     */ import com.ztesoft.inf.bean.annotation.LeafNodeTag;
/*     */ import com.ztesoft.inf.bean.annotation.NodeTag;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Inst
/*     */   implements IMap, IXml
/*     */ {
/*     */ 
/*     */   @ClassAttrTag
/*  21 */   private List<Inst> subNodes = new ArrayList();
/*     */ 
/*     */   @ClassAttrTag
/*  23 */   private List subNodesList = new LinkedList();
/*     */ 
/*     */   @ClassAttrTag
/*  26 */   private boolean isLoadSubNodesList = false;
/*     */ 
/*     */   @ClassAttrTag
/*     */   private String nodeTag;
/*     */ 
/*     */   @ClassAttrTag
/*     */   private String nodeValue;
/*     */ 
/*     */   @ClassAttrTag
/*  35 */   private Map<String, String> attributes = new HashMap();
/*     */ 
/*     */   public Map<String, String> getAttributes()
/*     */   {
/*  39 */     List fields = Clazz.getAllDeclaredFields(getClass());
/*  40 */     if (this.attributes.isEmpty()) {
/*  41 */       for (Field field : fields)
/*     */       {
/*  43 */         AttrNodeTag attrNodeTag = (AttrNodeTag)field.getAnnotation(AttrNodeTag.class);
/*     */ 
/*  46 */         if (attrNodeTag != null) {
/*  47 */           String attrName = StringUtils.isEmptyDefault(new String[] { attrNodeTag.name(), field.getName() });
/*     */ 
/*  49 */           String attrValue = (String)Clazz.getFiledObject(this, field);
/*     */ 
/*  51 */           this.attributes.put(attrName, attrValue);
/*     */         }
/*     */       }
/*     */     }
/*  55 */     return this.attributes;
/*     */   }
/*     */ 
/*     */   public String toXML()
/*     */   {
/*  60 */     List fields = Clazz.getAllDeclaredFields(getClass());
/*  61 */     StringBuilder attrCotent = new StringBuilder();
/*  62 */     StringBuilder content = new StringBuilder();
/*     */ 
/*  64 */     for (Field field : fields)
/*     */     {
/*  66 */       ClassAttrTag classAttrTag = (ClassAttrTag)field.getAnnotation(ClassAttrTag.class);
/*  67 */       if (classAttrTag == null)
/*     */       {
/*  71 */         AttrNodeTag attrNodeTag = (AttrNodeTag)field.getAnnotation(AttrNodeTag.class);
/*     */ 
/*  73 */         if (attrNodeTag != null) {
/*  74 */           String attrName = StringUtils.isEmptyDefault(new String[] { attrNodeTag.name(), field.getName() });
/*     */ 
/*  76 */           String attrValue = (String)Clazz.getFiledObject(this, field);
/*  77 */           attrCotent.append(toAttrItemXml(attrName, attrValue));
/*     */         }
/*     */         else
/*     */         {
/*  81 */           LeafNodeTag leafNodeTag = (LeafNodeTag)field.getAnnotation(LeafNodeTag.class);
/*  82 */           if (leafNodeTag != null)
/*  83 */             content.append(toLeafNodeXml(leafNodeTag, field));
/*     */           else
/*  85 */             content.append(toLeafNodeXml(field));
/*     */         }
/*     */       }
/*     */     }
/*  89 */     content.append(StringUtils.safe(this.nodeValue));
/*  90 */     return toOutXml(attrCotent.toString(), content.toString());
/*     */   }
/*     */ 
/*     */   public String toXML(Map xmlMap) {
/*  94 */     StringBuilder outStr = new StringBuilder();
/*  95 */     if (xmlMap == null) {
/*  96 */       return "";
/*     */     }
/*  98 */     for (Iterator it = xmlMap.keySet().iterator(); it.hasNext(); ) {
/*  99 */       String key = (String)it.next();
/* 100 */       Object sub = xmlMap.get(key);
/* 101 */       if ((sub instanceof List)) {
/* 102 */         List sublist = (List)sub;
/* 103 */         for (Map node : sublist)
/* 104 */           toNodeXml(outStr, key, toXML(node));
/*     */       }
/* 106 */       else if ((sub instanceof Map)) {
/* 107 */         toNodeXml(outStr, key, toXML((Map)sub));
/*     */       } else {
/* 109 */         toNodeXml(outStr, key, sub == null ? "" : sub.toString());
/*     */       }
/*     */     }
/* 112 */     return outStr.toString();
/*     */   }
/*     */ 
/*     */   private void toNodeXml(StringBuilder outStr, String key, String content) {
/* 116 */     if (outStr == null) {
/* 117 */       return;
/*     */     }
/* 119 */     outStr.append("<").append(key).append(">");
/* 120 */     outStr.append(content);
/* 121 */     outStr.append("</").append(key).append(">");
/*     */   }
/*     */ 
/*     */   private String toLeafNodeXml(LeafNodeTag leafNodeTag, Field field)
/*     */   {
/* 127 */     String headTag = StringUtils.isEmptyDefault(new String[] { leafNodeTag.name(), field.getName() });
/*     */ 
/* 129 */     return toLeafNodeXml(headTag, field);
/*     */   }
/*     */ 
/*     */   private String toLeafNodeXml(Field field) {
/* 133 */     String headTag = field.getName();
/* 134 */     return toLeafNodeXml(headTag, field);
/*     */   }
/*     */ 
/*     */   private String toLeafNodeXml(String headTag, Field field)
/*     */   {
/* 139 */     Object filedValue = Clazz.getFiledObject(this, field);
/* 140 */     if ((filedValue instanceof IXml)) {
/* 141 */       return ((IXml)filedValue).toXML();
/*     */     }
/* 143 */     if ((filedValue instanceof List)) {
/* 144 */       StringBuilder content = new StringBuilder("");
/* 145 */       List filedObjects = (List)filedValue;
/* 146 */       for (IXml bean : filedObjects) {
/* 147 */         content.append(bean.toXML());
/*     */       }
/*     */ 
/* 150 */       return content.toString();
/* 151 */     }if (((filedValue instanceof String)) || ((filedValue instanceof Byte)) || ((filedValue instanceof Short)) || ((filedValue instanceof Boolean)) || ((filedValue instanceof Integer)) || ((filedValue instanceof Character)) || ((filedValue instanceof Double)) || ((filedValue instanceof Float)) || ((filedValue instanceof Long)))
/*     */     {
/* 158 */       return toHeadTag(headTag, String.valueOf(filedValue));
/*     */     }
/* 160 */     return "";
/*     */   }
/*     */ 
/*     */   private String toHeadTag(String headName, String content) {
/* 164 */     if (StringUtils.isNotEmpty(headName)) {
/* 165 */       return toNodeXml(headName, content);
/*     */     }
/* 167 */     return content;
/*     */   }
/*     */ 
/*     */   private String toNodeXml(String headTag, String content) {
/* 171 */     return toNodeXml(headTag, headTag, content);
/*     */   }
/*     */ 
/*     */   private String toNodeXml(String headTag, String footTag, String content) {
/* 175 */     StringBuilder outStr = new StringBuilder();
/* 176 */     outStr.append("<").append(headTag).append(">");
/* 177 */     outStr.append(content);
/* 178 */     outStr.append("</").append(footTag).append(">");
/* 179 */     return outStr.toString();
/*     */   }
/*     */ 
/*     */   private String toAttrItemXml(String attrName, String attrValue) {
/* 183 */     return attrName + " = \"" + attrValue + "\" ";
/*     */   }
/*     */ 
/*     */   public String toOutXml(String attrCotent, String content) {
/* 187 */     String headTag = getNodeTag() + (StringUtils.isNotEmpty(attrCotent) ? " " + attrCotent : "");
/*     */ 
/* 189 */     String footTag = getNodeTag();
/* 190 */     return toNodeXml(headTag, footTag, content);
/*     */   }
/*     */ 
/*     */   public String getNodeTag() {
/* 194 */     NodeTag nodeTagAnn = (NodeTag)getClass().getAnnotation(NodeTag.class);
/* 195 */     String nodeTagAnnName = nodeTagAnn != null ? nodeTagAnn.name() : "";
/*     */ 
/* 197 */     return StringUtils.isEmptyDefault(new String[] { this.nodeTag, nodeTagAnnName, getClass().getSimpleName() });
/*     */   }
/*     */ 
/*     */   public void setNodeTag(String nodeTag)
/*     */   {
/* 202 */     this.nodeTag = nodeTag;
/*     */   }
/*     */ 
/*     */   public String getNodeValue() {
/* 206 */     return this.nodeValue;
/*     */   }
/*     */ 
/*     */   public void setNodeValue(String nodeValue) {
/* 210 */     this.nodeValue = nodeValue;
/*     */   }
/*     */ 
/*     */   public void loadFromMap(Map map)
/*     */   {
/* 215 */     this.attributes.putAll(map);
/*     */   }
/*     */ 
/*     */   public void setAttributes(Map attributes) {
/* 219 */     this.attributes = attributes;
/*     */   }
/*     */ 
/*     */   public void setAttribute(String name, String value) {
/* 223 */     this.attributes.put(name, value);
/*     */   }
/*     */ 
/*     */   public String getAttribute(String name)
/*     */   {
/* 228 */     return (String)this.attributes.get(name);
/*     */   }
/*     */ 
/*     */   public void setSubNodes(List<Inst> subNodes) {
/* 232 */     this.subNodes = subNodes;
/*     */   }
/*     */ 
/*     */   public Map toMap() {
/* 236 */     Map xmlMap = new LinkedHashMap();
/* 237 */     xmlMap.put(getNodeTag(), convertToMap());
/* 238 */     return xmlMap;
/*     */   }
/*     */ 
/*     */   public Map convertToMap() {
/* 242 */     Map map = new LinkedHashMap();
/* 243 */     map.putAll(this.attributes);
/* 244 */     if (getSubNodes() != null) {
/* 245 */       for (IMap subNode : getSubNodes())
/* 246 */         if (subNode != null)
/*     */         {
/* 249 */           List nodelist = (List)map.get(subNode.getNodeTag());
/*     */ 
/* 251 */           if (nodelist == null) {
/* 252 */             nodelist = new LinkedList();
/*     */           }
/* 254 */           nodelist.add(subNode.convertToMap());
/* 255 */           map.put(subNode.getNodeTag(), nodelist);
/*     */         }
/*     */     }
/* 258 */     return map;
/*     */   }
/*     */ 
/*     */   public List<IMap> getSubNodes() {
/* 262 */     clearSubNodesList();
/* 263 */     refreshSubNodesList();
/*     */ 
/* 265 */     if (!ListUtil.isEmpty(this.subNodesList)) {
/* 266 */       loadSubNodesList();
/*     */     }
/* 268 */     return this.subNodes;
/*     */   }
/*     */ 
/*     */   protected void clearSubNodesList() {
/* 272 */     if (this.subNodesList != null)
/* 273 */       this.subNodesList.clear();
/*     */   }
/*     */ 
/*     */   protected void refreshSubNodesList()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void loadSubNodesList()
/*     */   {
/* 283 */     if (this.isLoadSubNodesList == true) {
/* 284 */       return;
/*     */     }
/* 286 */     if (ListUtil.isEmpty(this.subNodesList)) {
/* 287 */       return;
/*     */     }
/* 289 */     for (List moreSubNodes : this.subNodesList) {
/* 290 */       addSubNode(moreSubNodes);
/*     */     }
/* 292 */     this.isLoadSubNodesList = true;
/*     */   }
/*     */ 
/*     */   public void addSubNode(IMap subNode)
/*     */   {
/* 299 */     this.subNodes.add((Inst)subNode);
/*     */   }
/*     */ 
/*     */   public void addSubNode(List<IMap> subNodes)
/*     */   {
/* 306 */     if (!ListUtil.isEmpty(subNodes))
/* 307 */       for (IMap subNode : subNodes)
/* 308 */         this.subNodes.add((Inst)subNode);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 314 */     if (this.attributes != null) {
/* 315 */       this.attributes.clear();
/*     */     }
/* 317 */     if (this.subNodes != null)
/* 318 */       this.subNodes.clear();
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.bean.Inst
 * JD-Core Version:    0.6.2
 */