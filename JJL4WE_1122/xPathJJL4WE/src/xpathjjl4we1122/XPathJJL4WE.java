package xpathjjl4we1122;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XPathJJL4WE
{

    public static void main(String[] args) {
        try {

            File inputFile = new File("JJL4WE_orarend.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            /*Oktató módosítása*/
            modifyElementValue(doc, "1", "oktato", "Nagy Bence");

            /*Helyszín módosítása*/
            modifyElementValue(doc, "3", "helyszin", "XXXIII A1 magasfsz");

            /*Tárgy módosítása*/
            modifyElementValue(doc, "5", "targy", "Lineáris algebra");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.STANDALONE, "no");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("orarendJJL4WE1.xml"));
            transformer.transform(source, result);

            printModifiedElements(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modifyElementValue(Document doc, String id, String elementName, String newValue) {
        NodeList nodeList = doc.getElementsByTagName("ora");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(id)) {
                    NodeList targetElements = element.getElementsByTagName(elementName);
                    if (targetElements.getLength() > 0) {
                        targetElements.item(0).setTextContent(newValue);
                    }
                }
            }
        }
    }

    private static void printModifiedElements(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("ora");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String tipus = element.getAttribute("tipus");
                String targy = element.getElementsByTagName("targy").item(0).getTextContent();
                String nap = element.getElementsByTagName("nap").item(0).getTextContent();
                String tol = element.getElementsByTagName("tol").item(0).getTextContent();
                String ig = element.getElementsByTagName("ig").item(0).getTextContent();
                String helyszin = element.getElementsByTagName("helyszin").item(0).getTextContent();
                String oktato = element.getElementsByTagName("oktato").item(0).getTextContent();

                System.out.println(String.format("ID: %s, Típus: %s, Tárgy: %s, Nap: %s, Időpont: %s-%s, Helyszín: %s, Oktató: %s",
                        id, tipus, targy, nap, tol, ig, helyszin, oktato));
            }
        }
    }

}