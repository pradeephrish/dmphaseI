package com.dm.file;

import com.dm.model.SnopesModel;

public class SaveToFile {
	public static void main(String[] args) {
		SnopesModel model = new SnopesModel();
		model.setClaim("");
		model.setClaim("Evacuees from Louisiana, Mississippi, or Alabama can get two months' of food stamps and free gasoline from the Texas DHHS by showing their driver's licenses.");
		//set other fields
		String url = "url from sample";
	}
	/*
	 * Shoudld save to txt file - derive file name from url
	 */
	public void saveToFile(SnopesModel snopesModel,String url){
		
	}
}
