package com.dm.classifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DataFormatter {
	public static void main(String[] args) throws IOException {
		Double data[][] = new Double[1842][26364];
		List<String> training = FileUtils.readLines(new File("resources/training.txt"));
		List<String> labels = FileUtils.readLines(new File("resources/label_training.txt"));
		
		for (int i = 0; i < training.size(); i++) {
			String line = training.get(i);
			String[] row = line.split(" ");
			Integer rowindex = Integer.parseInt(row[0]);
			Integer featureIndex = Integer.parseInt(row[1]);
			Double featureValue = Double.parseDouble(row[2]);
			data[rowindex-1][featureIndex-1]=featureValue;
		}
		//create desired output data format
		List<String> lines = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			String dataString = "";
			for (int j = 0; j < data[i].length; j++) {
				if(data[i][j]==null)
					data[i][j]=0.0;
				
				dataString+=data[i][j]+" ";
			}
			System.out.println("Wrote Line "+i);
			dataString+=labels.get(i);
			lines.add(dataString);
		}
		FileUtils.writeLines(new File("resources/trainingdata.db"), lines);
	}
}
