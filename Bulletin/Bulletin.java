
import java.io.*;

import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

public class Bulletin {
	private static BufferedReader in;
	private static StreamResult out;
	private static TransformerHandler th;
	private static int itemCounter = 1;
	private static int eventCounter = 1;
	private static String[] events = new String[10];


	public static void main(String[] args) {
		begin();
	}

	public static void begin() {
		try {
			in = new BufferedReader(new FileReader(new File("")));//location of txt file output by Powerschool
			out = new StreamResult(new File(""));//location of XML file that will be updated by this program
			openXml();
			String str;
			while ((str = in.readLine()) != null) {
				if(str.indexOf("<B>")>-1){
					getElement(str);
					process();
					itemCounter++;
					clearStringArray(events);
				}



			}
			in.close();
			closeXml();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		th = tf.newTransformerHandler();

		// pretty XML output
		Transformer serializer = th.getTransformer();
		serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");

		th.setResult(out);
		th.startDocument();
		th.startElement(null, null, "bulletinItems", null);
	}

	public static void process() throws SAXException {

		th.startElement(null, null, "item", null);
		th.startElement(null, null, "title", null);
		th.characters(getEventsArray()[0].toCharArray(), 0, getEventsArray()[0].length());
		th.endElement(null, null, "title");
		for(int x=1;x<getEventsArray().length;x++){
			if(getEventsArray()[x] !=null){
				th.startElement(null, null, "event", null);
				th.characters(getEventsArray()[x].toCharArray(), 0, getEventsArray()[x].length());
				th.endElement(null, null, "event");
			
			}
		}
		th.endElement(null, null, "item");
	}

	public static void closeXml() throws SAXException {
		th.endElement(null, null, "bulletinItems");
		th.endDocument();
	}
	public static void getElement(String s){
		String element;
		for(int y=0;y<s.length();y++){
			if(s.charAt(y) == '<' && s.charAt(y+1) == 'p' && s.charAt(y+2) == '>'){
				if(y+3 < s.indexOf("</p")){
					element = s.substring(y+3,s.indexOf("</p>"));
					events[eventCounter] = element;									
					eventCounter++;
					String[] parts = s.split("</p>",2);
					getElement(parts[1]);
				}
				
			}
			else if(s.charAt(y) == '<' && s.charAt(y+1) == 'B' && s.charAt(y+2) == '>'){
				
				element = s.substring(y+3, s.indexOf("</B>"));
				events[0]=element;
				eventCounter=1;
				String[] parts = s.split("</B>", 2);
				getElement(parts[1]);
			}
			
		}
	}

	public static void clearStringArray(String[] array){
		for(int x=0;x<array.length;x++){
			array[x] = null;
		}
	}

	public static String[] getEventsArray(){
		return events;
	}

}
