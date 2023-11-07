package hu.saxjjl4we;



import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxJJL4WE {
	public static void main(String[] args) {
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			
			SAXParser saxParser = saxParserFactory.newSAXParser();
			
			SaxHandler handler = new SaxHandler();
			
			saxParser.parse(new File("JJL4WE_kurzusfelvetel.xml"), handler);
					
		} catch (ParserConfigurationException | SAXException  | IOException e) {
			e.printStackTrace();	
		}
	}	
}

class SaxHandler extends DefaultHandler {
	
	private int indent = 0;


private String formatAttributes(Attributes attributes) {
	int attrLength = attributes.getLength();
	if (attrLength == 0) {
		return "";
	}
	StringBuilder sb = new StringBuilder(", {");
	for (int i=0;i<attrLength;i++) {
		sb.append(attributes.getLocalName(i) + "=" +attributes.getValue(i));
		if (i < attrLength - 1) {
			sb.append(", ");
		}
	}
	sb.append("}");
	return sb.toString();
}

private void indent(){
	for (int i =0; i < indent; i++){
		System.out.printf(" ");
	}
}


@Override
public void startElement(String uri, String localName, String qName, Attributes attributes){
	indent++;
	indent();	
	if (qName == "hnev" | qName == "szulev" | qName == "kurzusnev" | qName == "kredit" | qName == "hely" | qName == "idopont" | qName == "oktato" | qName == "oraado") {
		System.out.printf(qName + formatAttributes(attributes) + " start ");
		System.out.println("");
	}
	else {
		System.out.println(qName + formatAttributes(attributes) + " start ");
	}
		
}

@Override
public void endElement(String uri, String localName, String qName) {
	
	indent();
	indent--;
	if (qName == "hnev" | qName == "szulev" | qName == "kurzusnev" | qName == "kredit") {
		System.out.printf(qName + " end\n");
	}
	else {
		System.out.printf(qName + " end");
	}
}

@Override
public void characters(char ch[], int start, int length) {
	String chars = new String(ch, start, length).trim();
	if (!chars.isEmpty()) {
		indent++;
		indent();
		indent--;
		System.out.printf(chars);
	}
	
}

}

