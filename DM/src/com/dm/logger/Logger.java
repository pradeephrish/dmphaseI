package com.dm.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class Logger {
	
	public static void main(String[] args) {
		
		log("http://facebook.com",true);
	}
	
	public static void log( String str, boolean isNew){
		
//		if file doesnt exist, create new file 
		File file = new File("log/error");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			
//			write error to file
		try {
				
			FileWriter fw;	
			if(isNew)
				fw = new FileWriter(file,false);	//create new file by deleting previous records
			else
				fw = new FileWriter(file,true);		// append
				
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(new Timestamp(new Date().getTime())+" :    " + str);
				bw.newLine();
				
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
