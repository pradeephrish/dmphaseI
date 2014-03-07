package com.dm.driver;

import com.dm.file.SaveToFile;
import com.dm.logger.Logger;
import com.dm.model.SnopesModel;
import com.dm.parser.ParserMain;

public class Driver {
	public static void main(String[] args) {
		
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
				"http://www.snopes.com/toothbrush.asp",
				"http://www.snopes.com/horrors/madmen/backseat.asp",
				"http://www.snopes.com/critters/farce/wildcat.asp",
				"http://www.snopes.com/horrors/madmen/lightout.asp",
				"http://www.snopes.com/gardener.asp",
				"http://www.snopes.com/hitfloor.asp",
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
		
		ParserMain parserMain = new ParserMain();
		Logger.log("Fetching Urls", true);
		
		for (int i = 0; i < sublinks.length; i++) {
			String link = sublinks[i].replace("htm","asp");
			SnopesModel model = parserMain.getModelFromUrl(sublinks[i]);
			SaveToFile.saveToFile(model, sublinks[i]);
		}
		
	}
}
