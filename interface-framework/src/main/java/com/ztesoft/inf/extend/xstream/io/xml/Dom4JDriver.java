/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.io.xml;

import java.io.FilterWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocument;

import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.io.StreamException;

public class Dom4JDriver extends AbstractXmlDriver {

	private DocumentFactory documentFactory;
	private OutputFormat outputFormat;

	public Dom4JDriver() {
		this(new DocumentFactory(), OutputFormat.createPrettyPrint());
		outputFormat.setTrimText(false);
	}

	public Dom4JDriver(XmlFriendlyReplacer replacer) {
		this(new DocumentFactory(), OutputFormat.createPrettyPrint(), replacer);
	}

	public Dom4JDriver(DocumentFactory documentFactory,
			OutputFormat outputFormat) {
		this(documentFactory, outputFormat, new XmlFriendlyReplacer());
	}

	/**
	 * @since 1.2
	 */
	public Dom4JDriver(DocumentFactory documentFactory,
			OutputFormat outputFormat, XmlFriendlyReplacer replacer) {
		super(replacer);
		this.documentFactory = documentFactory;
		this.outputFormat = outputFormat;
	}

	public DocumentFactory getDocumentFactory() {
		return documentFactory;
	}

	public void setDocumentFactory(DocumentFactory documentFactory) {
		this.documentFactory = documentFactory;
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}

	public HierarchicalStreamReader createReader(Reader text) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(text);
			return new Dom4JReader(document, xmlFriendlyReplacer());
		} catch (DocumentException e) {
			throw new StreamException(e);
		}
	}

	public HierarchicalStreamReader createReader(Element element) {
		return new Dom4JReader(element, xmlFriendlyReplacer());
	}

	public HierarchicalStreamReader createReader(InputStream in) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			return new Dom4JReader(document, xmlFriendlyReplacer());
		} catch (DocumentException e) {
			throw new StreamException(e);
		}
	}

	public HierarchicalStreamWriter createWriter(final Writer out) {
		final HierarchicalStreamWriter[] writer = new HierarchicalStreamWriter[1];
		final FilterWriter filter = new FilterWriter(out) {

			@Override
			public void close() {
				writer[0].close();
			}
		};
		writer[0] = new Dom4JXmlWriter(new XMLWriter(filter, outputFormat),
				xmlFriendlyReplacer());
		return writer[0];
	}

	public DocumentWriter createDoumentWriter() {
		Dom4JWriter writer = new Dom4JWriter(new DefaultDocument());
		return writer;
	}

	public HierarchicalStreamWriter createWriter(final OutputStream out) {
		final Writer writer = new OutputStreamWriter(out);
		return createWriter(writer);
	}
}
