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


/**
 * A builder of decision trees from a database.<p>  The decision tree aims to
 * guess a (so called) 'goal' attribute thanks to 'test attributes' values.
 **/
public class DecisionTreeBuilder {
    
    private double entropyThreshold = 0.;
    private double scoreThreshold = 0.;

    private LearningDecisionTree tree;
    private ItemSet learningSet;
    SymbolicAttribute goalAttribute;  /* The attribute to guess */
    AttributeSet testAttributes;      /* The attributes on which the tests are
				         based. */
    
    
    /**
     * Builds a decision tree.  The learning is based on a learning set and
     * aims at guessing a 'goal' attribute value.  The entropy and score
     * thresholds are set to 0.
     *
     * @param learningSet The database holding the learning set.
     * @param testAttributes The attributes on which the tests are based.
     * @param goalAttribute The attribute to guess.
     **/
    public DecisionTreeBuilder(ItemSet learningSet, 
			       AttributeSet testAttributes,
			       SymbolicAttribute goalAttribute) {
	if (learningSet == null || learningSet.size() == 0)
	    throw new IllegalArgumentException("Invalid argument");
	
	this.learningSet = learningSet;
	this.testAttributes = testAttributes;
	this.goalAttribute = goalAttribute;
	
	LearningDecisionTree tree = 
	    new LearningDecisionTree(learningSet.attributeSet(),
				     goalAttribute);
	((LearningNode) tree.getRoot()).setLearningSet(learningSet);
	
	this.tree = tree;
    }
    
    /**
     * Build the entire decision tree according to the database.
     *
     * @return A decision tree modeling the database.
     **/
    public DecisionTree build() {
	while (tree.hasOpenNode())
	    expand();

	return tree.decisionTree();
    }
    
    /**
     * Expand an open node.
     **/
    public void expand() {
	LearningOpenNode node = (LearningOpenNode) tree.openNode();
	if (node == null)
	    throw new CannotCallMethodException("No open node left");
		
	ItemSet set = node.getLearningSet();

	if (set.entropy(goalAttribute) <= entropyThreshold)
	    makeLeafNode(node);
	else {
	    TestScore testScore = set.bestSplitTest(testAttributes, 
						    goalAttribute);
	    
	    if (testScore.score * set.size() <= scoreThreshold)
		makeLeafNode(node);  /* Forward pruning : test does not
					provide enough information */
	    else 
		makeTestNode(node, testScore.test);
	}
    }

    /**
     * Sets the maximal entropy of leaf node.  If a leaf node has an entropy
     * higher then the fixed entropy threshold, it is replaced by a test
     * that divides it in multiple subsets of lower entropy (if such a test
     * exists).
     *
     * @param entropy The entropy threshold.
     **/
    public void setEntropyThreshold(double entropy) {
	if (entropy < 0.)
	    throw new IllegalArgumentException("Argument must be positive");
	
	entropyThreshold = entropy;
    }
    
    /**
     * Returns the current entropy threshold.
     *
     * @return The current entropy threshold.
     **/
    public double getEntropyThreshold() {
	return entropyThreshold;
    }
    
    /**
     * Sets the minimal score of a test.  A new test node is created only if
     * its score is higher than the defined threshold.  The score of a test is
     * computed by multiplying the learning set (S) cardinality (N) by the
     * following information value:<br>
     * H(S) - Sum<sub>i = 1...T</sub> N<sub>i</sub> H(S<sub>i</sub>) / N<br>
     * where S<sub>i</sub> is the subset of S matching the i-th of the T
     * test's issues.  Each entropy is computed against the 'goal' attribute.
     *
     * @param entropy The score threshold.
     **/
    public void setTestScoreThreshold(double entropy) {
	if (entropy < 0.)
	    throw new IllegalArgumentException("Argument must be positive");
	
	entropyThreshold = entropy;
    }
    
    /**
     * Returns the current minimal score threshold.
     *
     * @return The current score threshold.
     **/
    public double getTestScoreThreshold() {
	return scoreThreshold;
    }
    
    private void makeLeafNode(LearningOpenNode openNode) {
	LearningLeafNode leafNode = new LearningLeafNode();
	
	leafNode.setLearningSet(openNode.getLearningSet());
	openNode.replace(leafNode);
    }

    private void makeTestNode(LearningOpenNode openNode, Test test) {
	LearningTestNode testNode = new LearningTestNode(test);

	testNode.setLearningSet(openNode.getLearningSet());
	openNode.replace(testNode);
	
	ItemSet[] subSets = openNode.getLearningSet().split(test);
	
	for (int i = 0; i < test.nbIssues(); i++) {
	    LearningOpenNode node = new LearningOpenNode();
	    
	    node.setLearningSet(subSets[i]);
	    testNode.son(i).replace(node);
	}
    }
}
