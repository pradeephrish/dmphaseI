package com.dm.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import com.dm.logger.Logger;
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
//		parserMain.getModelFromUrl("http://www.snopes.com/holidays/thanksgiving/blackfriday.asp");
//		parserMain.getModelFromUrl("http://www.snopes.com/inboxer/pending/inaction.asp");
		parserMain.getModelFromUrl("http://www.snopes.com/business/alliance/coors.asp");
		
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
	
		SnopesModel snopesModel = new SnopesModel();		
		Source source;
		try {
		source = new Source(new URL(url));
		System.out.println(url);
//		System.out.println(source.toString());
		String nSource = source.toString().replaceAll("STYLE=\"text-align: justify; margin-left: 15px;  margin-right: 15px\"", "CLASS=\"article_text\"");
//		System.out.println(nSource);
		source = new Source(nSource); //replace with new div class
//		System.out.println(source);
		List<Element> articleClass = source.getAllElementsByClass("article_text");
		Element element = articleClass.get(0);
		String text = new Source(element.toString()).getTextExtractor().toString();
		//text has everything,  substring and set the model
		System.out.println(text);
		
		snopesModel = snopesModelFromText(snopesModel, text);
		
		//get source element
		try{
		Element sources = source.getAllElements(HTMLElementName.DL).get(0);
		System.out.println("^^^^^^^^^");
		System.out.println(new Source(sources.toString()).getTextExtractor().toString().trim());
		String sourceString = new Source(sources.toString()).getTextExtractor().toString().trim();
		snopesModel.setSource(sourceString);
		}catch(IndexOutOfBoundsException indexOutOfBoundsException){
			indexOutOfBoundsException.printStackTrace();
			System.out.println("Doesn't have source, setting empty");
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//call logger
			System.out.println("Failed for url : "+url);
			Logger.log(url, false);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Failed for url : "+url);
			Logger.log(url, false);
		}
		return snopesModel;
	}
	
	private SnopesModel snopesModelFromText(SnopesModel model,String possibleText){
		Integer exampleIndex = possibleText.indexOf("Example:");
		if(exampleIndex==-1)
			exampleIndex = possibleText.indexOf("Examples:");
		
		if(exampleIndex!=-1){  //means there is example
		
		String claim = possibleText.substring(0, exampleIndex);
		
		//claim text contains status in the END 
		String[] words = claim.split(" ");
		String status = words[words.length-1];
		System.out.println("Status is "+status);
		//Check Status
		if(!status.toUpperCase().contains("TRUE")&&!status.toUpperCase().contains("FALSE"))
			status = "mixture";
		
		int claimSizeIndex = claim.length();
		
		
		
		claim = claim.replace("Claim:", "").trim();
		claim = claim.replace("Legend:","").trim();
		
		int sClaimIndex = claim.indexOf("Status:");
		if(sClaimIndex!=-1)
		claim =claim.substring(0,sClaimIndex);
		
		model.setClaim(claim);

		
		status=status.replace(".", "").toUpperCase();
		
		System.out.println(status);
		model.setStatus(status);
		
		Integer originsIndex = possibleText.indexOf("Origins:");
		String example = possibleText.substring(claimSizeIndex, originsIndex);
		
		example = example.replace("Example:","");
		example = example.replace("Examples:","").trim();
		
		model.setExample(example);
		Integer lastUpdated = possibleText.indexOf("Last updated:");
		String origins = possibleText.substring(originsIndex,lastUpdated);
		
		origins= origins.replace("Origins:", "").trim();
		
		model.setOrigins(origins);
		System.out.println(claim);
		System.out.println(example);
		System.out.println(origins);
		
		}else{
			
			Integer originsIndex = possibleText.indexOf("Origins:");
			
			String claim = possibleText.substring(0, originsIndex);
			
			
			//claim text contains status in the END 
			String[] words = claim.split(" ");
			String status = words[words.length-1];
			System.out.println("Status is "+status);
			//Check Status
			if(!status.toUpperCase().contains("TRUE")&&!status.toUpperCase().contains("FALSE"))
				status = "mixture";
			
			status=status.replace(".", "").toUpperCase();
			
			System.out.println(status);
			model.setStatus(status);
			
			
			String sclaim = claim.replace("Claim:", "");
			sclaim = sclaim.replace("Legend:","").trim();
			sclaim = sclaim.replace(status, "");
			
			model.setClaim(sclaim.replace(status, ""));
			
			
			
			Integer lastUpdated = possibleText.indexOf("Last updated:");
			String origins = possibleText.substring(originsIndex,lastUpdated);
			model.setOrigins(origins.replace("Origins:", "").trim());
			System.out.println(sclaim);
			System.out.println(origins.replace("Origins:", "").trim());
			
		}
		//Note claim can start with Legend, Remember to remove Start keywords eg. Claims:, Example:, Origins:
		
		return model;
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
