package com.dm.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import com.dm.model.SnopesModel;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftTagTypes;
import net.htmlparser.jericho.Source;


public class ParserMain {
	public static void main(String[] args) {
		//racial humar , group 30 verify
		//http://www.snopes.com/racial/racial.asp , grabed sublinks from main page
		
		String url[] = {"http://www.snopes.com/racial/arts/arts.asp","http://www.snopes.com/racial/humor/humor.asp","http://www.snopes.com/racial/business/business.asp","http://www.snopes.com/racial/business/business.asp","http://www.snopes.com/racial/language/language.asp","http://www.snopes.com/racial/crime/crime.asp","http://www.snopes.com/racial/mistaken/mistaken.asp","http://www.snopes.com/racial/govern/govern.asp"};
		MicrosoftTagTypes.register();
		MasonTagTypes.register();
		ParserMain parserMain = new ParserMain();
		
		//saved links to following array, no need to parse main page again
		/*for (int i = 0; i < url.length; i++) {
			parserMain.getLinksFromMainLink(url[i]);
		}*/
		
		String sublinks[] = {"http://www.snopes.com/music/artists/311.asp",
				"http://www.snopes.com/disney/films/sots.htm",
				"http://www.snopes.com/radiotv/tv/rascals.htm",
				"http://www.snopes.com/music/artists/presley1.asp",
				"http://www.snopes.com/music/artists/presley3.asp",
				"http://www.snopes.com/quotes/lauryn.htm",
				"http://www.snopes.com/history/american/liberty.htm",
				"http://www.snopes.comnascar.asp",
				"http://www.snopes.comfearmost.asp",
				"http://www.snopes.comhousefire.asp",
				"http://www.snopes.comchurches.asp",
				"http://www.snopes.comhilfiger.asp",
				"http://www.snopes.comclaiborne.asp",
				"http://www.snopes.com/business/alliance/marlboro.asp",
				"http://www.snopes.com/business/alliance/crown.asp",
				"http://www.snopes.com/business/alliance/troop.asp",
				"http://www.snopes.com/business/alliance/menthol.asp",
				"http://www.snopes.com/business/alliance/sanders.asp",
				"http://www.snopes.com/business/alliance/snapple.asp",
				"http://www.snopes.com/business/alliance/timberland.asp",
				"http://www.snopes.com/business/alliance/coors.asp",
				"http://www.snopes.comtshirts.asp",
				"http://www.snopes.comkosher.asp",
				"http://www.snopes.comstarbucks.asp",
				"http://www.snopes.comcvs.asp",
				"http://www.snopes.comsofa.asp",
				"http://www.snopes.comarizona.asp",
				"http://www.snopes.comcuddlewithme.asp",
				"http://www.snopes.comourslaves.asp",
				"http://www.snopes.com/racial/business/tropical.asp",
				"http://www.snopes.comabercrombie.asp",
				"http://www.snopes.com/holidays/thanksgiving/blackfriday.asp",
				"http://www.snopes.comhilfiger.asp",
				"http://www.snopes.comclaiborne.asp",
				"http://www.snopes.com/business/alliance/marlboro.asp",
				"http://www.snopes.com/business/alliance/crown.asp",
				"http://www.snopes.com/business/alliance/troop.asp",
				"http://www.snopes.com/business/alliance/menthol.asp",
				"http://www.snopes.com/business/alliance/sanders.asp",
				"http://www.snopes.com/business/alliance/snapple.asp",
				"http://www.snopes.com/business/alliance/timberland.asp",
				"http://www.snopes.com/business/alliance/coors.asp",
				"http://www.snopes.comtshirts.asp",
				"http://www.snopes.comkosher.asp",
				"http://www.snopes.comstarbucks.asp",
				"http://www.snopes.comcvs.asp",
				"http://www.snopes.comsofa.asp",
				"http://www.snopes.comarizona.asp",
				"http://www.snopes.comcuddlewithme.asp",
				"http://www.snopes.comourslaves.asp",
				"http://www.snopes.com/racial/business/tropical.asp",
				"http://www.snopes.comabercrombie.asp",
				"http://www.snopes.com/holidays/thanksgiving/blackfriday.asp",
				"http://www.snopes.comnames.asp",
				"http://www.snopes.com/language/offense/picnic.htm",
				"http://www.snopes.com/language/offense/buck.htm",
				"http://www.snopes.com/radiotv/tv/password.htm",
				"http://www.snopes.comhurricane.asp",
				"http://www.snopes.comle-a.asp",
				"http://www.snopes.com/crime/justice/grannies.asp",
				"http://www.snopes.com/horrors/parental/mutilate.asp",
				"http://www.snopes.com/horrors/animals/doberman.asp",
				"http://www.snopes.comtoothbrush.asp",
				"http://www.snopes.com/horrors/madmen/backseat.asp",
				"http://www.snopes.com/critters/farce/wildcat.asp",
				"http://www.snopes.com/horrors/madmen/lightout.asp",
				"http://www.snopes.comgardener.asp",
				"http://www.snopes.comhitfloor.asp",
				"http://www.snopes.com/business/taxes/immigrants.asp",
				"http://www.snopes.com/business/taxes/blacktax.asp",
				"http://www.snopes.com/business/taxes/blackssn.asp",
				"http://www.snopes.com/inboxer/outrage/bush.htm",
				"http://www.snopes.com/inboxer/outrage/heritage.htm",
				"http://www.snopes.com/inboxer/outrage/rosapark.htm",
				"http://www.snopes.com/inboxer/outrage/nofear.htm",
				"http://www.snopes.com/inboxer/pending/inaction.htm",
				"http://www.snopes.com/inboxer/pending/blackcol.htm",
				"http://www.snopes.com/inboxer/pending/voting.asp"};
		
		//parse one url test
		parserMain.getModelFromUrl("http://www.snopes.com/holidays/thanksgiving/blackfriday.asp");
		
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
				if(attributeValue!=null){
					linksToBeParsed.add("http://www.snopes.com"+attributeValue);
					System.out.println("http://www.snopes.com"+attributeValue);
				}
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
	
	public SnopesModel getModelFromUrl(String url){
		System.out.println(url);
		SnopesModel snopesModel = new SnopesModel();		
		Source source;
		try {
		source = new Source(new URL(url));
		List<Element> articleClass = source.getAllElementsByClass("article_text");
		Element element = articleClass.get(0);
		String text = new Source(element.toString()).getTextExtractor().toString();
		//text has everything,  substring and set the model
		System.out.println(text);
		//get source element
		Element sources = source.getAllElements(HTMLElementName.DL).get(0);
		System.out.println("^^^^^^^^^");
		System.out.println(new Source(sources.toString()).getTextExtractor().toString().trim());
		String sourceString = new Source(sources.toString()).getTextExtractor().toString().trim();
		snopesModel.setSource(sourceString);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed for url : "+url); 
		}
		return null;
	}
	
	public static String getTextContent(Element elem) {
	    String text = elem.getContent().toString();
	    final List<Element> children = elem.getChildElements();
	    for (Element child : children) {
	    	System.out.println("Child String is ");
	    	System.out.println(child.toString());
	        //text = text.replace(child.toString(), "");
	    }
	    return text;
	}
	
}
