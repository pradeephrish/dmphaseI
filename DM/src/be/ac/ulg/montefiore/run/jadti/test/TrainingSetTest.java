package be.ac.ulg.montefiore.run.jadti.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import be.ac.ulg.montefiore.run.jadti.AttributeSet;
import be.ac.ulg.montefiore.run.jadti.DecisionTree;
import be.ac.ulg.montefiore.run.jadti.DecisionTreeBuilder;
import be.ac.ulg.montefiore.run.jadti.Item;
import be.ac.ulg.montefiore.run.jadti.ItemSet;
import be.ac.ulg.montefiore.run.jadti.SymbolicAttribute;
import be.ac.ulg.montefiore.run.jadti.io.DecisionTreeToDot;
import be.ac.ulg.montefiore.run.jadti.io.ItemSetReader;

public class TrainingSetTest {

    /*
     * Download trainingdata.db and testingdata.db from the link
     * https://www.dropbox.com/sh/6im3czoyxnxfutp/HorAY5EDOE and put both the
     * files in the resources folder.
     */
    final static String dbFileName = "resources/trainingdata.db";
    final static String jadtiURL = "http://www.run.montefiore.ulg.ac.be/"
	    + "~francois/software/jaDTi/";
    final static String testingDbFileName = "resources/testingdata.db";

    static public void main(String[] args) throws IOException {

	ItemSet learningSet = null;
	try {
	    learningSet = ItemSetReader.read(new FileReader(dbFileName));
	} catch (FileNotFoundException e) {
	    System.err.println("File not found : " + dbFileName + ".");
	    System.err.println("This file is included in the source "
		    + "distribution of jaDti.  You can find it at " + jadtiURL);
	    System.exit(-1);
	}

	ItemSet testingSet = null;
	try {
	    testingSet = ItemSetReader.read(new FileReader(testingDbFileName));
	} catch (FileNotFoundException e) {
	    System.err.println("File not found : " + testingDbFileName + ".");
	    System.err.println("This file is included in the source "
		    + "distribution of jaDti.  You can find it at " + jadtiURL);
	    System.exit(-1);
	}

	AttributeSet attributeSet = learningSet.attributeSet();

	Vector testAttributesVector = new Vector();
	for (int i = 1; i < 26365; i++) {
	    testAttributesVector.add(attributeSet.findByName("number" + i));
	}

	AttributeSet testAttributes = new AttributeSet(testAttributesVector);
	SymbolicAttribute goalAttribute = (SymbolicAttribute) learningSet
		.attributeSet().findByName("Label");

	DecisionTree tree = buildTree(learningSet, testAttributes,
		goalAttribute);

	printDot(tree);
	File file = new File("resources/prediction.txt");

	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);

	for (int i = 0; i < 952; i++) {
	    System.out
		    .println(i + "=" + predictLabel(testingSet.item(i), tree));
	    bw.write(predictLabel(testingSet.item(i), tree) + "");
	    bw.write("\n");
	}
	bw.close();

	System.out
		.println("  952 predictions are generated in the file resources/prediction.txt ");
    }

    /*
     * Build the decision tree.
     */
    static private DecisionTree buildTree(ItemSet learningSet,
	    AttributeSet testAttributes, SymbolicAttribute goalAttribute) {
	DecisionTreeBuilder builder = new DecisionTreeBuilder(learningSet,
		testAttributes, goalAttribute);

	return builder.build();
    }

    /*
     * Prints a dot file content depicting a tree.
     */
    static private void printDot(DecisionTree tree) {
	System.out.println((new DecisionTreeToDot(tree)).produce());
    }

    /*
     * Prints an item's guessed goal attribute value.
     */
    static private void printGuess(Item item, DecisionTree tree) {
	AttributeSet itemAttributes = tree.getAttributeSet();
	SymbolicAttribute goalAttribute = tree.getGoalAttribute();

	int goalAttributeValue = ((Integer) item.valueOf(itemAttributes,
		goalAttribute)).intValue();
	int guessedGoalAttributeValue = tree.guessGoalAttribute(item);

	String s = "Item goal attribute value is "
		+ goalAttribute.valueToString(goalAttributeValue) + "\n";

	s += "The value guessed by the tree is "
		+ tree.getGoalAttribute().valueToString(
			guessedGoalAttributeValue);

	System.out.println(s);
    }

    static private int predictLabel(Item item, DecisionTree tree) {

	int guessedGoalAttributeValue = tree.guessGoalAttribute(item);
	if (tree.getGoalAttribute().valueToString(guessedGoalAttributeValue)
		.equals("yes"))

	    return 1;
	else
	    return -1;
    }
}
