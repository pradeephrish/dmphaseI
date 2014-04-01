package com.dm.generateData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TrainingSetDecisionTree {

    public static void main(String args[]) {
	int[][] dataArray = new int[1843][26365];
	ArrayList<Integer> columnList = new ArrayList<Integer>();
	ArrayList<Integer> classLabels = new ArrayList<Integer>();

	int row = 0;
	int column = 0;
	float value = 0;
	int newValue;
	String[] threeElements;

	try {

	    File file = new File(
		    "C:\\Users\\appatil\\Dropbox\\test\\trainingdata.txt");

	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);

	    FileInputStream fstream1 = new FileInputStream(
		    "C:\\Users\\appatil\\Dropbox\\Data_Mining\\Course_Project\\Data-Task-3\\label_training.txt");

	    DataInputStream in1 = new DataInputStream(fstream1);
	    BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
	    String strLine1;

	    while ((strLine1 = br1.readLine()) != null) {
		classLabels.add(Integer.parseInt(strLine1));
		// System.out.print(strLine1 + " ");
	    }

	    FileInputStream fstream2 = new FileInputStream(
		    "C:\\Users\\appatil\\Dropbox\\Data_Mining\\Course_Project\\Data-Task-3\\training.txt");

	    DataInputStream in2 = new DataInputStream(fstream2);
	    BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
	    String strLine2;

	    while ((strLine2 = br2.readLine()) != null) {

		threeElements = strLine2.split(" ");
		row = Integer.parseInt(threeElements[0]);
		column = Integer.parseInt(threeElements[1]);
		value = Float.parseFloat(threeElements[2]);
		newValue = (int) (value * 100);
		// System.out.println(newValue);
		// columnList.add(column);
		dataArray[row][column] = newValue;

	    }
	    // find maximum element
	    // Collections.sort(columnList);
	    bw.write("trainingdata-database");
	    bw.write("\n");
	    bw.write("object name ");
	    for (int i = 1; i < 26365; i++) {
		bw.write("number" + i + " " + "numerical ");
	    }
	    bw.write("Label symbolic ");
	    bw.write("\n");

	    for (int i = 1; i < 1843; i++) {

		bw.write("obj" + i + " ");
		for (int j = 1; j < 26365; j++) {

		    bw.write("" + dataArray[i][j] + " ");
		}
		if (classLabels.get(i - 1).equals(1)) {
		    bw.write("yes");
		} else {
		    bw.write("no");
		}
		bw.write("\n");
	    }

	    // Close the input stream
	    in1.close();
	    in2.close();
	    bw.close();
	    System.out.println("Done");
	} catch (Exception e) {// Catch exception if any
	    System.err.println("Error: " + e.getMessage());
	}
    }
}
