package com.dm.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.dm.model.SnopesModel;

public class SaveToFile {
	public static void main(String[] args) {
		SnopesModel model = new SnopesModel();
		model.setClaim("");
		model.setClaim("Evacuees from Louisiana, Mississippi, or Alabama can get two months' of food stamps and free gasoline from the Texas DHHS by showing their driver's licenses.");
		//set other fields
		
		model.setOrigins("");
		model.setOrigins("Evacuees from Louisiana, Mississippi, or Alabama can get two months' of food stamps and free gasoline from the Texas DHHS by showing their driver's licenses.");
		//set other fields
		
		model.setStatus("");
		model.setStatus("Evacuees from Louisiana, Mississippi, or Alabama can get two months' of food stamps and free gasoline from the Texas DHHS by showing their driver's licenses.");
		//set other fields
		
		model.setExample("");
		model.setExample("Evacuees from Louisiana, Mississippi, or Alabama can get two months' of food stamps and free gasoline from the Texas DHHS by showing their driver's licenses.");
		//set other fields
		String url = "http://www.snopes.com/katrina/charity/foodstamps.asp";
		
		saveToFile(model, url);
	}

	public static void saveToFile(SnopesModel snopesModel,String url){
		
//		get file name
		int startIndex = url.indexOf('/', 15) + 1;
		int endIndex = url.indexOf('.', url.length() - 8);
		String path = url.substring(startIndex, endIndex);
		String fName = path.replace('/', '-') + ".txt";
		System.out.println(fName);
		
//	if file doesnt exist, create new file 	
		File file = new File("data/" + fName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		write data to file
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("@@@begin_claim@@@" + snopesModel.getClaim() + "@@@end_claim@@@");
			bw.newLine();
			bw.write("@@@begin_status@@@" + snopesModel.getStatus() + "@@@end_status@@@");
			bw.newLine();
			bw.write("@@@begin_example@@@" + snopesModel.getExample() + "@@@end_example@@@");
			bw.newLine();
			bw.write("@@@begin_origin@@@" + snopesModel.getOrigins() + "@@@end_origin@@@");
			bw.newLine();
			bw.write("@@@begin_sources@@@" + snopesModel.getSource() + "@@@end_sources@@@");
			
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
