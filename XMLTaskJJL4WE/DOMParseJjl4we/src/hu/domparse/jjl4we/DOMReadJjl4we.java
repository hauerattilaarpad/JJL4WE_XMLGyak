package hu.domparse.jjl4we;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMReadJjl4we {
    public static void main(String[] args) {
        try {
            File newXmlFile = new File("XMLReadjjl4we.xml");
            StreamResult newXmlStream = new StreamResult(newXmlFile);

            Document document = parseXML("XMLJJL4WE.xml");
            writeDocument(document, newXmlStream);

            System.out.println("Formázott xml:\n\n" + formatXML(document));
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Document parseXML(String fileName) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        cleanDocument(document.getDocumentElement());
        return document;
    }

    private static void cleanDocument(Node root) {
        NodeList nodes = root.getChildNodes();
        List<Node> toDelete = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.TEXT_NODE && nodes.item(i).getTextContent().strip().isEmpty()) {
                toDelete.add(nodes.item(i));
            } else {
                cleanDocument(nodes.item(i));
            }
        }
        for (Node node : toDelete) {
            root.removeChild(node);
        }
    }

    public static void writeDocument(Document document, StreamResult output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        transformer.transform(source, output);
    }

    public static String formatXML(Document document) {
        return "<?xml version=\"" + document.getXmlVersion() + "\" encoding=\"" + document.getXmlEncoding() + "\" ?>" +
               formatElement(document.getDocumentElement(), 0);
    }

    public static String formatElement(Node node, int indent) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return "";
        }

        StringBuilder output = new StringBuilder("\n");
        output.append(indent(indent)).append("<").append(((Element) node).getTagName());

        if (node.hasAttributes()) {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                Node attribute = node.getAttributes().item(i);
                output.append(" ").append(attribute.getNodeName()).append("=\"").append(attribute.getNodeValue()).append("\"");
            }
        }
        output.append(">");

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.TEXT_NODE) {
                output.append(children.item(i).getTextContent());
            } else if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                output.append(formatElement(children.item(i), indent + 1));
            } else if (children.item(i).getNodeType() == Node.COMMENT_NODE) {
                output.append("\n").append(indent(indent + 1)).append("<!--").append(((Comment) children.item(i)).getData()).append("-->");
            }
        }
        output.append("\n").append(indent(indent)).append("</").append(((Element) node).getTagName()).append(">");

        return output.toString();
    }

    private static String indent(int indent) {
        return "   ".repeat(indent);
    }
}


