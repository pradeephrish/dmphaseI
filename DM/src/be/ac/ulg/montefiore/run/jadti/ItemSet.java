/* jaDTi package - v0.5.0 */

/*
 *  Copyright (c) 2004, Jean-Marc Francois.
 *
 *  This file is part of jaDTi.
 *  jaDTi is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  jaDTi is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jahmm; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package be.ac.ulg.montefiore.run.jadti;

import java.util.*;


/**
 * This class holds ordered sets of items.  An item is defined as a 
 * learning/test set element.
 **/
public class ItemSet {

    private AttributeSet attributeSet;   /* The item's attributes */
    private Vector items;
    private double entropy = -1;        /* Set entropy (-1 if unknown) */
    private Attribute entropyAttribute; /* The attribute used to compute the
					   entropy */
    
    
    /* Used to sort numerical test values while remembering the corresponding
       goal value. */
    private class TestGoalValues 
	implements Comparable {
	
	final double testValue;
	final int goalValue;
	
	TestGoalValues(double testValue, int goalValue) {
	    this.testValue = testValue;
	    this.goalValue = goalValue;
	}

	public int compareTo(Object o) {
	    TestGoalValues tgv = (TestGoalValues) o;
	    
	    if (testValue < tgv.testValue)
		return -1;
	    else if (testValue == tgv.testValue)
		return 0;
	    else
		return 1;
	}
    }
    
    
    /**
     * Builds a new empty item set.
     *
     * @param attributeSet The set of attributes describing the items of the
     *                     set.
     **/
    public ItemSet(AttributeSet attributeSet) {
	if (attributeSet == null)
	    throw new IllegalArgumentException("Invalid attribute set");

	this.attributeSet = attributeSet;
	items = new Vector();
    }
    
    /**
     * Adds all the items of a vector.  All the elements of the vector must
     * be {@link Item items} compatible with this set's attribute set.
     *
     * @param items A Vector of {@link Item Items}.
     **/
    public void add(Vector items) {
	for (int i = 0; i < items.size(); i++)
	    add((Item) items.elementAt(i));
    }
    
    /**
     * Adds an item.  This item must be compatible with this set's attribute
     * set.
     *
     * @param item An item.
     **/
    public void add(Item item) {
	if (item == null || item.nbAttributes() != attributeSet.size())
	    throw new IllegalArgumentException("Invalid item");
	
	items.add(item);
	entropy = -1.;
    }
    
    /**
     * Returns an item's attribute value.
     *
     * @param index The item index. <code>index</code> is such that
     *              0 <= <code>index</code> < <code>size()</code>.
     * @param attribute The attribute whose value is to be retreived.
     * @return The attribute value.
     **/
    public Object value(int index, Attribute attribute) {
	return item(index).valueOf(attributeSet, attribute);
    }

    /**
     * Returns an item of the array given its index.  The index is such that
     * 0 <= <code>index</code> < <code>size()</code>.
     *
     * @param index The item index.
     * @return The <code>index</code>-th item.
     **/
    public Item item(int index) {
	if (index < 0 || index >= size())
	    throw new IllegalArgumentException("Invalid index");
	
	return (Item) items.elementAt(index);
    }
    
    /**
     * Returns this set's number of items.
     *
     * @return A strictly positive number of items.
     **/
    int size() {
	return items.size();
    }
    
    /**
     * Finds the test on one attribute performing the best split (bringing the
     * most information) for finding the value of a <i>goal</i> attribute.
     *
     * @param candidateAttributes The set of attributes defining which
     *                            attributes can be tested.
     * @param goalAttribute The attribute to guess using the test.
     * @return The best test for finding <code>goal</code>.
     **/
    TestScore bestSplitTest(AttributeSet candidateAttributes, 
			    SymbolicAttribute goalAttribute) {
	if (candidateAttributes == null || goalAttribute == null ||
	    candidateAttributes.size() == 0)
	    throw new IllegalArgumentException("Invalid attributes");
	
	Iterator iterator = candidateAttributes.attributes().iterator();
	TestScore bestTestScore = bestSplitTest((Attribute) iterator.next(), 
						goalAttribute);

	while (iterator.hasNext()) {
	    TestScore testScore = bestSplitTest((Attribute) iterator.next(), 
						goalAttribute);

	    if (bestTestScore.compareTo(testScore) < 0)
		bestTestScore = testScore;
	}
	
	return bestTestScore;
    }

    private TestScore bestSplitTest(Attribute testAttribute,
					  SymbolicAttribute goalAttribute) {
	if (testAttribute instanceof SymbolicAttribute)
	    return bestSplitTest((SymbolicAttribute) testAttribute,
				 goalAttribute);
	else if (testAttribute instanceof NumericalAttribute)
	    return bestSplitTest((NumericalAttribute) testAttribute,
				 goalAttribute);
	else
	    throw new IllegalArgumentException("Unknown attribute type");
    }

    /**
     * Finds the best split test.  This test involves one attribute only,
     * and aims at increasing the information on a so-called 'goal' attribute
     * as much as possible.
     *
     * @param testAttribute The attribute on which the test is based.
     * @param goalAttribute The attribute to model.
     **/
    private TestScore bestSplitTest(SymbolicAttribute testAttribute, 
				    SymbolicAttribute goalAttribute) {
	int testNbValues = testAttribute.nbValues;
	int testIndex = attributeSet.indexOf(testAttribute);
	int goalNbValues = goalAttribute.nbValues;
	int goalIndex = attributeSet.indexOf(goalAttribute);
	
	/* frequencyMatch[tvi][gvi] is the number of items that has a value
	   equal to tvi for their 'test' attribute and value equal to 'gvi' for
	   their 'goal' attribute. frequencyMatchSum[tvi] is the sum of
	   the frequencyMatch[tvi][gvi] elements (for all gvi). */
	double[][] frequencyMatch = new double[testNbValues][goalNbValues];
	double[] frequencyMatchSum = new double[testNbValues];
	
	/* Identically for the items that do not have tvi as a test attribute
	   value. */
	double[][] frequencyNoMatch = new double[testNbValues][goalNbValues];
	double[] frequencyNoMatchSum = new double[testNbValues];

	for (int i = 0; i < items.size(); i++) {
	    int testValue = item(i).symbolicValue(testIndex);
	    int goalValue = item(i).symbolicValue(goalIndex);

	    for (int tvi = 0; tvi < testNbValues; tvi++)
		if (testValue == tvi) {
		    frequencyMatch[tvi][goalValue]++;
		    frequencyMatchSum[tvi]++;
		} else {
		    frequencyNoMatch[tvi][goalValue]++;
		    frequencyNoMatchSum[tvi]++;
		}
	}
	
	/* The score is the information brought by the test */
	double bestScore = -1.;
	int bestValue = -1;
	
	for (int tvi = 0; tvi < testNbValues; tvi++) {
	    double score = entropy(goalAttribute) -
		((frequencyMatchSum[tvi] / items.size()) * 
		 Entropy.entropy(frequencyMatch[tvi])) -
		((frequencyNoMatchSum[tvi] / items.size()) * 
		 Entropy.entropy(frequencyNoMatch[tvi]));

	    if (score > bestScore) {
		bestScore = score;
		bestValue = tvi;
	    }
	}

	/* Group the attribute values one by one (best to worse) */
	Vector remainingTestValueIndexes = new Vector();
	for (int tvi = 0; tvi < testNbValues; tvi++)
	    remainingTestValueIndexes.add(new Integer(tvi));
	
	double[] remainingFrequencyMatch = new double[goalNbValues];
	double[] remainingFrequencyNoMatch = new double[goalNbValues];
	
	for (int gvi = 0; gvi < goalNbValues; gvi++)
	    remainingFrequencyNoMatch[gvi] =
		frequencyMatch[0][gvi] + frequencyNoMatch[0][gvi];

	double remainingFrequencyMatchSum = 0.;
	double remainingFrequencyNoMatchSum = items.size();
	
	/* Retain the score of each attribute value */
	Vector orderedValueIndexes = new Vector(testNbValues - 1);
	Vector orderedScores = new Vector(testNbValues - 1);
	
	orderedValueIndexes.add(new Integer(bestValue));
	orderedScores.add(new Double(bestScore));
	
 	/* Remove values until only one is left */
	while (remainingTestValueIndexes.size() >= 2) {
	    /* Update remainingFrequency... arrays according to the last
	       test attribute value removed */
	    remainingTestValueIndexes.remove(new Integer(bestValue));
	    for (int gvi = 0; gvi < goalNbValues; gvi++) {
		remainingFrequencyMatch[gvi] += 
		    frequencyMatch[bestValue][gvi];
		remainingFrequencyNoMatch[gvi] -= 
		    frequencyMatch[bestValue][gvi];
	    }
	    remainingFrequencyMatchSum += frequencyMatchSum[bestValue];
	    remainingFrequencyNoMatchSum -= 
		frequencyMatchSum[bestValue];
	    
	    bestScore = -1.;
	    /* Find the next best test attribute value */
	    for (int i = 0; i < remainingTestValueIndexes.size(); i++) {
		int tvi = ((Integer) remainingTestValueIndexes.elementAt(i)).
		    intValue();
		
		double[] thisFrequencyMatch = new double[goalNbValues];
		double[] thisFrequencyNoMatch = new double[goalNbValues];
		double thisFrequencyMatchSum = 0., thisFrequencyNoMatchSum = 0.;
		
		for (int gvi = 0; gvi < goalNbValues; gvi++) {
		    thisFrequencyMatch[gvi] = frequencyMatch[tvi][gvi] +
			remainingFrequencyMatch[gvi];
		    thisFrequencyNoMatch[gvi] = remainingFrequencyNoMatch[gvi] -
			frequencyMatch[tvi][gvi];
		}
		thisFrequencyMatchSum = 
		    frequencyMatchSum[tvi] + remainingFrequencyMatchSum;
		thisFrequencyNoMatchSum = remainingFrequencyNoMatchSum - 
		    frequencyMatchSum[tvi];
		
		double score = entropy(goalAttribute) -
		    ((thisFrequencyMatchSum / items.size()) *
		     Entropy.entropy(thisFrequencyMatch)) -
		    ((thisFrequencyNoMatchSum / items.size()) *
		     Entropy.entropy(thisFrequencyNoMatch));
		
		if (score > bestScore) {
		    bestScore = score;
		    bestValue = tvi;
		}
	    }
	    
	    orderedScores.add(new Double(bestScore));
	    orderedValueIndexes.add(new Integer(bestValue));
	}
	
	bestScore = -1;
	int bestIndex = 0;
	for (int i = 0; i < orderedScores.size(); i++) {
	    double score = ((Double) orderedScores.elementAt(i)).doubleValue();
	    
	    if (score > bestScore) {
		bestScore = score;
		bestIndex = i;
	    }
	}
	
	int[] testValueIndexes = new int[bestIndex + 1];
	for (int i = 0; i <= bestIndex; i++)
	    testValueIndexes[i] = 
		((Integer) orderedValueIndexes.elementAt(i)).intValue();

	return new TestScore(new SymbolicTest(testAttribute, 
					      testValueIndexes), 
			     bestScore);
    }
    
    /* Finds the best splitting test involving a numerical attribute */
    private TestScore bestSplitTest(NumericalAttribute testAttribute, 
				    SymbolicAttribute goalAttribute) {
	int testIndex = attributeSet.indexOf(testAttribute);
	int goalNbValues = goalAttribute.nbValues;
	int goalIndex = attributeSet.indexOf(goalAttribute);
	
	/* frequencyLower (frequencyHigher) counts the number of items lower 
	   (higher) than the threshold for each goal value.  In the beginning,
	   frequencyLower is zeroed because the threshold is chosen small.  */
	double[] frequencyLower = new double[goalNbValues];
	double[] frequencyHigher = new double[goalNbValues];
	for (int gvi = 0; gvi < goalNbValues; gvi++) {
	    SymbolicTest valueTest = 
		new SymbolicTest(goalAttribute, new int[] { gvi });
	    
	    frequencyHigher[gvi] = split(valueTest)[1].size();
	}
	
	/* Those two variables hold sum of the elements of the corresponding
	   array */
	double frequencyLowerSum = 0;
	double frequencyHigherSum = items.size();
	
	/* This array is used to sort the test values while remembering the
	   corresponding goal value. */
	TestGoalValues[] tgv = new TestGoalValues[items.size()];
	for (int i = 0; i < items.size(); i++) {
	    Item item = (Item) items.elementAt(i);
	    
	    tgv[i] =
		new TestGoalValues(item.numericalValue(testIndex),
				   item.symbolicValue(goalIndex));
	}
	Arrays.sort(tgv); 
	
	int goalValue, goalValueNew = tgv[0].goalValue;
	double testValue, testValueNew = tgv[0].testValue;
	
	double bestScore = 0;
	double bestThreshold = testValueNew;
	
	for (int i = 1; i < items.size(); i++) {
	    testValue = testValueNew;
	    goalValue = goalValueNew;
	    goalValueNew = tgv[i].goalValue;
	    testValueNew = tgv[i].testValue;

	    frequencyLower[goalValue]++;
	    frequencyLowerSum++;
	    frequencyHigher[goalValue]--;
	    frequencyHigherSum--;

	    if (testValue != testValueNew) {
		/* Compute information as if the threshold was epsilon higher
		   than testValue */
		double score = entropy(goalAttribute) -
		    (frequencyLowerSum / items.size()) * 
		    Entropy.entropy(frequencyLower) -
		    (frequencyHigherSum / items.size()) * 
		    Entropy.entropy(frequencyHigher);
		
		if (score > bestScore) {
		    bestScore = score;
		    bestThreshold = (testValue + testValueNew) / 2.;
		}
	    }
	}
	
	return new TestScore(new NumericalTest(testAttribute, bestThreshold),
			     bestScore);
    }
    
    ItemSet[] split(Test test) {
	ItemSet[] sets = new ItemSet[test.nbIssues()];
	for(int i = 0; i < sets.length; i++)
	    sets[i] = new ItemSet(attributeSet);

	for (int i = 0; i < items.size(); i++) {
	    Item item = (Item) items.elementAt(i);
	    sets[test.perform(item.valueOf(attributeSet, test.attribute))]. 
		add(item);
	}
    
	return sets;
    }
    
    /**
     * Computes the entropy of the set regarding a given attribute.
     *
     * @param attribute The attribute agains which to compute the entropy.
     * @return The computed entropy.
     **/
    public double entropy(SymbolicAttribute attribute) {
	if (!attributeSet.contains(attribute))
	    throw new IllegalArgumentException("Unknown attribute");

	if (entropy < 0. || !entropyAttribute.equals(attribute)) {
	    double[] frequencies = new double[attributeSet.size()];
	    
	    for (int i = 0; i < size(); i++) 
		frequencies[item(i).symbolicValue(attributeSet.
						  indexOf(attribute))]++;
	    
	    entropy = Entropy.entropy(frequencies);
	    entropyAttribute = attribute;
	}
	
	return entropy;
    }  

    /**
     * Returns the set of attributes matching the items of this set.
     *
     * @return The set of attributes.
     **/
    public AttributeSet attributeSet() {
	return attributeSet;
    }
}

