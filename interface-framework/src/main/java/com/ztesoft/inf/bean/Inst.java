package com.ztesoft.inf.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.Clazz;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.bean.annotation.AttrNodeTag;
import com.ztesoft.inf.bean.annotation.ClassAttrTag;
import com.ztesoft.inf.bean.annotation.LeafNodeTag;
import com.ztesoft.inf.bean.annotation.NodeTag;

public class Inst implements IMap, IXml {
	@ClassAttrTag
	private List<Inst> subNodes = new ArrayList();
	@ClassAttrTag
	private List subNodesList = new LinkedList<List<IMap>>();
	// 是否已经加载子串定义列表
	@ClassAttrTag
	private boolean isLoadSubNodesList = false;
	// 本节点名称
	@ClassAttrTag
	private String nodeTag;
	// 节点值
	@ClassAttrTag
	private String nodeValue;
	// 属性
	@ClassAttrTag
	private Map<String, String> attributes = new HashMap<String, String>();

	public Map<String, String> getAttributes() {
		List<Field> fields = Clazz.getAllDeclaredFields(this.getClass());
		if (attributes.isEmpty()) {
			for (Field field : fields) {

				AttrNodeTag attrNodeTag = field
						.getAnnotation(AttrNodeTag.class);
				// 只支持字符串结构属性
				if (attrNodeTag != null) {
					String attrName = StringUtils.isEmptyDefault(
							attrNodeTag.name(), field.getName());
					String attrValue = (String) Clazz.getFiledObject(this,
							field);
					attributes.put(attrName, attrValue);
				}
			}
		}
		return attributes;
	}

	public String toXML() {
		// 1.获取所有属性
		List<Field> fields = Clazz.getAllDeclaredFields(this.getClass());
		StringBuilder attrCotent = new StringBuilder();
		StringBuilder content = new StringBuilder();
		// 2.解析field
		for (Field field : fields) {
			// 类属性字段,不用于拼装到xml
			ClassAttrTag classAttrTag = field.getAnnotation(ClassAttrTag.class);
			if (classAttrTag != null) {
				continue;
			}

			AttrNodeTag attrNodeTag = field.getAnnotation(AttrNodeTag.class);
			// 只支持字符串结构属性
			if (attrNodeTag != null) {
				String attrName = StringUtils.isEmptyDefault(
						attrNodeTag.name(), field.getName());
				String attrValue = (String) Clazz.getFiledObject(this, field);
				attrCotent.append(toAttrItemXml(attrName, attrValue));
				continue;
			}
			// 子节点
			LeafNodeTag leafNodeTag = field.getAnnotation(LeafNodeTag.class);
			if (leafNodeTag != null) {
				content.append(toLeafNodeXml(leafNodeTag, field));
			} else {
				content.append(toLeafNodeXml(field));
			}

		}
		content.append(StringUtils.safe(nodeValue));
		return toOutXml(attrCotent.toString(), content.toString());
	}

	public String toXML(Map xmlMap) {
		StringBuilder outStr = new StringBuilder();
		if (xmlMap == null) {
			return "";
		}
		for (Iterator it = xmlMap.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object sub = xmlMap.get(key);
			if (sub instanceof List) {
				List<Map> sublist = (List) sub;
				for (Map node : sublist) {
					toNodeXml(outStr, key, toXML(node));
				}
			} else if (sub instanceof Map) {
				toNodeXml(outStr, key, toXML((Map) sub));
			} else {
				toNodeXml(outStr, key, sub == null ? "" : sub.toString());
			}
		}
		return outStr.toString();
	}

	private void toNodeXml(StringBuilder outStr, String key, String content) {
		if (outStr == null) {
			return;
		}
		outStr.append("<").append(key).append(">");
		outStr.append(content);
		outStr.append("</").append(key).append(">");
	}

	// 子节点XML数据
	private String toLeafNodeXml(LeafNodeTag leafNodeTag, Field field) {

		String headTag = StringUtils.isEmptyDefault(leafNodeTag.name(),
				field.getName());
		return toLeafNodeXml(headTag, field);
	}

	private String toLeafNodeXml(Field field) {
		String headTag = field.getName();
		return toLeafNodeXml(headTag, field);
	}

	private String toLeafNodeXml(String headTag, Field field) {

		Object filedValue = Clazz.getFiledObject(this, field);
		if (filedValue instanceof IXml) {
			return ((IXml) filedValue).toXML();
			// return toHeadTag(headTag, ((IXml) filedValue).toXML());
		} else if (filedValue instanceof List) {
			StringBuilder content = new StringBuilder("");
			List<IXml> filedObjects = (List<IXml>) filedValue;
			for (IXml bean : filedObjects) {
				content.append(bean.toXML());
			}
			// return toHeadTag(headTag, content.toString());
			return content.toString();
		} else if (filedValue instanceof String || filedValue instanceof Byte
				|| filedValue instanceof Short || filedValue instanceof Boolean
				|| filedValue instanceof Integer
				|| filedValue instanceof Character
				|| filedValue instanceof Double || filedValue instanceof Float
				|| filedValue instanceof Long) {

			return toHeadTag(headTag, String.valueOf(filedValue));
		}
		return "";
	}

	private String toHeadTag(String headName, String content) {
		if (StringUtils.isNotEmpty(headName)) {
			return toNodeXml(headName, content);
		}
		return content;
	}

	private String toNodeXml(String headTag, String content) {
		return toNodeXml(headTag, headTag, content);
	}

	private String toNodeXml(String headTag, String footTag, String content) {
		StringBuilder outStr = new StringBuilder();
		outStr.append("<").append(headTag).append(">");
		outStr.append(content);
		outStr.append("</").append(footTag).append(">");
		return outStr.toString();
	}

	private String toAttrItemXml(String attrName, String attrValue) {
		return attrName + " = \"" + attrValue + "\" ";
	}

	public String toOutXml(String attrCotent, String content) {
		String headTag = this.getNodeTag()
				+ (StringUtils.isNotEmpty(attrCotent) ? " " + attrCotent : "");
		String footTag = this.getNodeTag();
		return toNodeXml(headTag, footTag, content);
	}

	public String getNodeTag() {
		NodeTag nodeTagAnn = this.getClass().getAnnotation(NodeTag.class);
		String nodeTagAnnName = nodeTagAnn != null ? nodeTagAnn.name() : "";

		return StringUtils.isEmptyDefault(this.nodeTag, nodeTagAnnName, this
				.getClass().getSimpleName());
	}

	public void setNodeTag(String nodeTag) {
		this.nodeTag = nodeTag;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	// 通用方法， 从Map中加载数据（基本数据）
	public void loadFromMap(Map map) {
		attributes.putAll(map);
	}

	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public void setAttribute(String name, String value) {
		this.attributes.put(name, value);

	}

	public String getAttribute(String name) {
		return (String) this.attributes.get(name);
	}

	public void setSubNodes(List<Inst> subNodes) {
		this.subNodes = subNodes;
	}

	public Map toMap() {
		Map xmlMap = new LinkedHashMap();
		xmlMap.put(this.getNodeTag(), this.convertToMap());
		return xmlMap;
	}

	public Map convertToMap() {
		Map map = new LinkedHashMap();
		map.putAll(this.attributes);
		if (this.getSubNodes() != null) {
			for (IMap subNode : this.getSubNodes()) {
				if (subNode == null) {
					continue;
				}
				List<Map> nodelist = (List<Map>) map.get(subNode.getNodeTag());

				if (nodelist == null) {
					nodelist = new LinkedList<Map>();
				}
				nodelist.add(subNode.convertToMap());
				map.put(subNode.getNodeTag(), nodelist);
			}
		}
		return map;
	}

	public List<IMap> getSubNodes() {
		this.clearSubNodesList();
		this.refreshSubNodesList();

		if (!ListUtil.isEmpty(this.subNodesList)) {
			loadSubNodesList();
		}
		return (List) this.subNodes;
	}

	protected void clearSubNodesList() {
		if (this.subNodesList != null) {
			this.subNodesList.clear();
		}
	}

	protected void refreshSubNodesList() {

	}

	/** 加载子串定义列表 */
	private void loadSubNodesList() {
		if (isLoadSubNodesList == true) {
			return;
		}
		if (ListUtil.isEmpty(this.subNodesList)) {
			return;
		}
		for (List<IMap> moreSubNodes : (List<List<IMap>>) this.subNodesList) {
			this.addSubNode(moreSubNodes);
		}
		isLoadSubNodesList = true;
	}

	/**
	 * 请使用addChildNode
	 */
	public void addSubNode(IMap subNode) {
		this.subNodes.add((Inst) subNode);
	}

	/**
	 * 请使用addChildNode
	 */
	public void addSubNode(List<IMap> subNodes) {
		if (!ListUtil.isEmpty(subNodes)) {
			for (IMap subNode : subNodes) {
				this.subNodes.add((Inst) subNode);
			}
		}
	}

	public void clear() {
		if (attributes != null) {
			attributes.clear();
		}
		if (this.subNodes != null) {
			subNodes.clear();
		}
	}

}
