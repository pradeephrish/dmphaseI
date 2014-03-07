package com.dm.driver;

import com.dm.file.SaveToFile;
import com.dm.logger.Logger;
import com.dm.model.SnopesModel;
import com.dm.parser.ParserMain;

public class Driver {
	public static void main(String[] args) {
		
		String sublinks[] = {"http://www.snopes.com/music/artists/311.asp",
				"http://www.snopes.com/disney/films/sots.asp",
				"http://www.snopes.com/radiotv/tv/rascals.asp",
				"http://www.snopes.com/music/artists/presley1.asp",
				"http://www.snopes.com/music/artists/presley3.asp",
				"http://www.snopes.com/politics/quotes/laurynhill.asp",
				"http://www.snopes.com/history/american/statueofliberty.asp",
				"http://www.snopes.com/racial/humor/nascar.asp",
				"http://www.snopes.com/racial/humor/fearmost.asp",
				"http://www.snopes.com/racial/humor/housefire.asp",
				"http://www.snopes.com/racial/humor/churches.asp",
				"http://www.snopes.com/racial/business/hilfiger.asp",
				"http://www.snopes.com/racial/business/claiborne.asp",
				"http://www.snopes.com/business/alliance/marlboro.asp",
				"http://www.snopes.com/business/alliance/crown.asp",
				"http://www.snopes.com/business/alliance/troop.asp",
				"http://www.snopes.com/business/alliance/menthol.asp",
				"http://www.snopes.com/business/alliance/sanders.asp",
				"http://www.snopes.com/business/alliance/snapple.asp",
				"http://www.snopes.com/business/alliance/timberland.asp",
				"http://www.snopes.com/business/alliance/coors.asp",
				"http://www.snopes.com/racial/business/tshirts.asp",
				"http://www.snopes.com/racial/business/kosher.asp",
				"http://www.snopes.com/racial/business/starbucks.asp",
				"http://www.snopes.com/racial/business/cvs.asp",
				"http://www.snopes.com/racial/business/sofa.asp",
				"http://www.snopes.com/racial/business/arizona.asp",
				"http://www.snopes.com/racial/business/cuddlewithme.asp",
				"http://www.snopes.com/racial/business/ourslaves.asp",
				"http://www.snopes.com/racial/business/tropical.asp",
				"http://www.snopes.com/racial/business/abercrombie.asp",
				"http://www.snopes.com/holidays/thanksgiving/blackfriday.asp",
				"http://www.snopes.com/racial/language/names.asp",
				"http://www.snopes.com/language/offense/picnic.asp",
				"http://www.snopes.com/language/offense/buck.asp",
				"http://www.snopes.com/radiotv/tv/password.asp",
				"http://www.snopes.com/racial/language/hurricane.asp",
				"http://www.snopes.com/racial/language/le-a.asp",
				"http://www.snopes.com/crime/justice/grannies.asp",
				"http://www.snopes.com/horrors/parental/mutilate.asp",
				"http://www.snopes.com/horrors/animals/doberman.asp",
				"http://www.snopes.com/racial/crime/toothbrush.asp",
				"http://www.snopes.com/horrors/madmen/backseat.asp",
				"http://www.snopes.com/critters/farce/wildcat.asp",
				"http://www.snopes.com/crime/gangs/lightsout.asp",
				"http://www.snopes.com/racial/mistaken/gardener.asp",
				"http://www.snopes.com/racial/mistaken/hitfloor.asp",
				"http://www.snopes.com/business/taxes/immigrants.asp",
				"http://www.snopes.com/business/taxes/blacktax.asp",
				"http://www.snopes.com/business/taxes/blackssn.asp",
				"http://www.snopes.com/inboxer/outrage/bush.asp",
				"http://www.snopes.com/inboxer/outrage/heritage.htm",
				"http://www.snopes.com/inboxer/outrage/rosapark.htm",
				"http://www.snopes.com/inboxer/outrage/nofear.htm",
				"http://www.snopes.com/inboxer/pending/inaction.htm",
				"http://www.snopes.com/inboxer/pending/blackcol.htm",
				"http://www.snopes.com/inboxer/pending/voting.asp"};
		
		ParserMain parserMain = new ParserMain();
		Logger.log("Fetching Urls", true);
		
		for (int i = 0; i < sublinks.length; i++) {
			String link = sublinks[i].replace("htm","asp");
			SnopesModel model = parserMain.getModelFromUrl(link);
			SaveToFile.saveToFile(model, sublinks[i]);
		}
		
	}
}
