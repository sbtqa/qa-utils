package ru.sbtqa.tag.qautils.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.sbtqa.tag.qautils.parsers.exception.ParserException;
import ru.sbtqa.tag.qautils.parsers.interfaces.Parser;
import ru.sbtqa.tag.qautils.parsers.interfaces.callback.ParserCallback;

/**
 * Xml parser
 *
 * @author Konstantin Maltsev <mkypers@gmail.com>
 */
public class XmlParser implements Parser, ParserCallback {

    private static final Logger log = LoggerFactory.getLogger(XmlParser.class);

    @Override
    public String call(ParserItem item) {
        String result = "";
        try {
            result = read(item.getSource(), item.getPath());
        } catch (ParserException e) {
            log.error("Error to get value by path {} in source {}", item.getPath(), item.getSource(), e);
        }

        return result;
    }

    @Override
    public String read(String source, String xpath) throws ParserException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            //TODO take source encode from props
            Document xmlDocument = builderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(source.getBytes("UTF-8")));
            return read(xmlDocument, xpath);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new ParserException("Failed to cast string source to xml document", ex);
        }
    }

    /**
     * Applies this xpath to provided source as document.
     * 
     * @param source a {@link org.w3c.dom.Document} object
     * @param xpath the path to apply
     * @return a string matched by the given path
     * @throws ParserException if an error occurred while applying the path on source
     */
    public String read(Document source, String xpath) throws ParserException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            return (String) xPath.compile(xpath).evaluate(source, XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            throw new ParserException("Failed to get value by path " + xpath + " in source " + source, ex);
        }
    }

    /**
     * Applies this xpath to provided source.
     * 
     * @param source a string source
     * @param xpath the path to apply
     * @return a {@link org.w3c.dom.NodeList} object matched by the given path
     * @throws ParserException if an error occurred while applying the path on source
     */
    public NodeList getNodeList(String source, String xpath) throws ParserException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            //TODO take source encode from props
            Document xmlDocument = builderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(source.getBytes("UTF-8")));
            return (NodeList) xPath.compile(xpath).evaluate(xmlDocument, XPathConstants.NODESET);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            throw new ParserException("Failed to get value by path " + xpath + " in source " + source, ex);
        }
    }

    /**
     * Applies this xpath to provided source.
     * 
     * @param source a {@link org.w3c.dom.Document} object
     * @param xpath the path to apply
     * @return a {@link org.w3c.dom.NodeList} object matched by the given path
     * @throws ParserException if an error occurred while applying the path on source
     */
    public NodeList getNodeList(Document source, String xpath) throws ParserException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            return (NodeList) xPath.compile(xpath).evaluate(source, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            throw new ParserException("Failed to get value by path " + xpath + " in source " + source, ex);
        }
    }
}
