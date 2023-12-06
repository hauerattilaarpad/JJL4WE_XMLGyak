package xpathjjl4we;




import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XPathJJL4WE
{

    public static void main(String[] args)
    {

        try {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("studentJJL4WE.xml");

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        XPathExpression expr = xpath.compile("/class/student");
        //XPathExpression expr = xpath.compile("/class/student[@id='2']");
        //XPathExpression expr = xpath.compile("//student");
        //XPathExpression expr = xpath.compile("/class/student[2]");
        //XPathExpression expr = xpath.compile("/class/student[last()]");
        //XPathExpression expr = xpath.compile("/class/student[last()-1]");
        //XPathExpression expr = xpath.compile("/class/student[position() <= 2]");
        //XPathExpression expr = xpath.compile("/class/*");
        //XPathExpression expr = xpath.compile("/class/student[@*]");
        //XPathExpression expr = xpath.compile("//*");
        //XPathExpression expr = xpath.compile("/class/student[kor>20]");
        //XPathExpression expr = xpath.compile("class/student/keresztnev | class/student/vezeteknev");

        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println("Student " + (i + 1) + ":");
            System.out.println("ID: " + nodeList.item(i).getAttributes().getNamedItem("id").getTextContent());
            System.out.println("Keresztnév: " + nodeList.item(i).getChildNodes().item(1).getTextContent());
            System.out.println("Vezetéknév: " + nodeList.item(i).getChildNodes().item(3).getTextContent());
            System.out.println("Becenév: " + nodeList.item(i).getChildNodes().item(5).getTextContent());
            System.out.println("Kor: " + nodeList.item(i).getChildNodes().item(7).getTextContent());
            System.out.println("--------------------");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    }}
    