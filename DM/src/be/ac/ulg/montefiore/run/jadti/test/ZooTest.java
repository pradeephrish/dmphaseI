/* jaDTi package - v0.5.0 */

package be.ac.ulg.montefiore.run.jadti.test;

import be.ac.ulg.montefiore.run.jadti.*;
import be.ac.ulg.montefiore.run.jadti.io.*;
import java.io.*;
import java.util.*;


/*
 * A short example program of the jaDTi library.
 */
public class ZooTest {

    final static String dbFileName = "resources/zoo.db";
    final static String jadtiURL = "http://www.run.montefiore.ulg.ac.be/" +
	"~francois/software/jaDTi/";
    
    static public void main(String[] args)
	throws IOException {

	ItemSet learningSet = null;
	try {
	    learningSet = ItemSetReader.read(new FileReader(dbFileName));
	}
	catch(FileNotFoundException e) {
	    System.err.println("File not found : " + dbFileName + ".");
	    System.err.println("This file is included in the source " +
			       "distribution of jaDti.  You can find it at " +
			       jadtiURL);
	    System.exit(-1);
	}
	

	AttributeSet attributeSet = learningSet.attributeSet();

	Vector testAttributesVector = new Vector();
	
	
	
	testAttributesVector.add(attributeSet.findByName("legs"));
	testAttributesVector.add(attributeSet.findByName("tail"));
	testAttributesVector.add(attributeSet.findByName("domestic"));
	testAttributesVector.add(attributeSet.findByName("hair"));
	testAttributesVector.add(attributeSet.findByName("feathers"));

	AttributeSet testAttributes = new AttributeSet(testAttributesVector);
	SymbolicAttribute goalAttribute =
	    (SymbolicAttribute) learningSet.attributeSet().findByName("type");

	DecisionTree tree = 
	    buildTree(learningSet, testAttributes, goalAttribute);
	
	printDot(tree);
	
	printGuess(learningSet.item(0), tree);
    }

    /*
     * Build the decision tree.
     */
    static private DecisionTree buildTree(ItemSet learningSet, 
					  AttributeSet testAttributes, 
					  SymbolicAttribute goalAttribute) {
	DecisionTreeBuilder builder = 
	    new DecisionTreeBuilder(learningSet, testAttributes,
				    goalAttribute);
	
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
	
	int goalAttributeValue = 
	    ((Integer) item.valueOf(itemAttributes, goalAttribute)).intValue();
	int guessedGoalAttributeValue = tree.guessGoalAttribute(item);

	String s = "Item goal attribute value is " +
	    goalAttribute.valueToString(goalAttributeValue) + "\n";
	
	s += "The value guessed by the tree is " + 
	    tree.getGoalAttribute().valueToString(guessedGoalAttributeValue);
	
	System.out.println(s);
    }
}
