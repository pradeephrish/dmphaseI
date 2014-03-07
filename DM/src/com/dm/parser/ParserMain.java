package com.dm.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftTagTypes;
import net.htmlparser.jericho.Source;


public class ParserMain {
	public static void main(String[] args) {
		//racial humar , group 30 verify
		//http://www.snopes.com/racial/racial.asp
		
		//sub-links
		//String subUrls= {"","","",""};
		
		
		String url[] = {"http://www.snopes.com/racial/arts/arts.asp"};
		
		
		
		MicrosoftTagTypes.register();
		MasonTagTypes.register();
		ParserMain parserMain = new ParserMain();
		parserMain.getLinksFromMainLink(url[0]);
	}
	
	/*
	 * Let's keep it for future
	 */
	public List<String> getLinksFromMainLink(String url){
		
		List<String> linksToBeParsed = new ArrayList<String>();
		
		try {
			Source source = new Source(new URL(url));
			
			
			List<Element> elements = source.getAllElements(HTMLElementName.TABLE);
			/*for (Element element : elements) {
				System.out.println("=============");
				System.out.println(element.toString());
				System.out.println("=============end=");
			}*/
			Element element = elements.get(4);
			List<Element> imageRows = element.getAllElements(HTMLElementName.A);
			//System.out.println(element);
			for (Element element2 : imageRows) {
				//Element urlLink = element2.getAllElements(HTMLElementName.A).get(0);
				//System.out.println(urlLink.getAttributeValue("Href"));
				//System.out.println(element2);
				
				String attributeValue = element2.getAttributeValue("href");
				System.out.println(attributeValue);
				
				
				if(attributeValue!=null){
					linksToBeParsed.add("http://www.snopes.com"+attributeValue);
					System.out.println("http://www.snopes.com"+attributeValue);
				}
				
				
				System.out.println("**********");
			}
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return linksToBeParsed;
	}
}
