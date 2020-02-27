package com.packagename.myapp.util;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * User: tor
 * Date: 25.02.2020
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public class XSLTransformer implements java.io.Serializable {
    private static transient HashMap<String, XSLTransformer> cache = new HashMap<String, XSLTransformer>();

    private String xslResourceURL;
    private Document stylesheet;

    private Transformer transformer;
    private Class loader;

    private XSLTransformer(String xslResourceURL, Class loader) {
        this.xslResourceURL = xslResourceURL;
        this.loader = loader;
    }

    public Document loadXMLFromString(@NotNull String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name())));
    }
    public StringBuffer transformToString(Document xmlDocument) throws Exception {
        StringWriter stringWriter = new StringWriter(15000);
        Result result = new StreamResult(stringWriter);

        transform(xmlDocument, result);

        return stringWriter.getBuffer();
    }

    public void transform(Document xmlDocument, Result result) throws Exception {
        ensureInitialized();


        DOMSource source = new DOMSource(xmlDocument);

        try {
            synchronized (transformer) {
                transformer.transform(source, result);
            }
        } catch (TransformerException ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }
    }

    public Document transform(Document xmlDocument) throws Exception {
        ensureInitialized();

        DOMResult result = new DOMResult();
        DOMSource source = new DOMSource(xmlDocument);

        try {
            synchronized (transformer) {
                transformer.transform(source, result);
            }
        } catch (TransformerException ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }

        return (Document) result.getNode();
    }

    private synchronized void ensureInitialized() throws Exception {
        if (transformer == null) {
            try {
                java.net.URL url = loader.getResource("/" + xslResourceURL);

                TransformerFactory tf = TransformerFactory.newInstance();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                builderFactory.setNamespaceAware(true);
                DocumentBuilder builder = builderFactory.newDocumentBuilder();

                InputStream is = url.openStream();

                stylesheet = builder.parse(is);

                transformer = tf.newTransformer(new DOMSource(stylesheet));

                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception(ex);
            }
        }
    }

    public static XSLTransformer newInstance(String xslResourceURL) {
        return newInstance(xslResourceURL, XSLTransformer.class);
    }

    public static synchronized XSLTransformer newInstance(String xslResourceURL, Class callerClass) {
        XSLTransformer p = cache.get(xslResourceURL);

        if (p == null) {
            p = new XSLTransformer(xslResourceURL, callerClass);
            cache.put(xslResourceURL, p);
        }

        return p;
    }
}
