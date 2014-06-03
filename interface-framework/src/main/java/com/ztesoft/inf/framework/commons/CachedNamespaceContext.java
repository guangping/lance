package com.ztesoft.inf.framework.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

public class CachedNamespaceContext implements NamespaceContext {

	protected Map<String, String> prefix2Uri = new HashMap<String, String>();
	protected Map<String, String> uri2Prefix = new HashMap<String, String>();
	protected static final String DEFAULT_NS = "DEFAULT";

	public CachedNamespaceContext() {
	}
	public CachedNamespaceContext(Map namespaces) {
		putInCache(namespaces);
	}
	public void putInCache(Map<String, String> namespaces) {
		if (namespaces == null)
			return;
		for (String key : namespaces.keySet()) {
			putInCache(key, namespaces.get(key));
		}
	}
	public String getNamespaceURI(String prefix) {
		return prefix2Uri.get(prefix);
	}
	public void putInCache(String prefix, String uri) {
		if (prefix == null)
			throw new IllegalArgumentException("prefix could not  be null");
		prefix2Uri.put(prefix, uri);
		uri2Prefix.put(uri, prefix);
	}
	public String getPrefix(String namespaceURI) {
		return uri2Prefix.get(namespaceURI);
	}
	public Iterator getPrefixes(String namespaceURI) {
		// Not implemented
		return null;
	}
}
