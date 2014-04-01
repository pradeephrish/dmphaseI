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
 * A leaf node implementing the {@link LearningNode LearningNode}
 * interface.
 **/
public class LearningLeafNode 
    extends LeafNode implements LearningNode {
    
    private ItemSet learningSet = null;

    /**
     * Creates a new learning leaf node.
     **/
    LearningLeafNode() {
	super();
    }
    
    /**
     * Set the current learning set.
     *
     * @param set A (potentially 'null') learning set.
     **/
    public void setLearningSet(ItemSet set) {
	learningSet = set;
    }
    
    public ItemSet getLearningSet() {
	return learningSet;
    }
    
    public void replace(Node node) {
	if (!(node instanceof LearningNode))
	    throw new IllegalArgumentException("A learning node can only " +
					       "be replaced by another "+
					       "learning node");
	super.replace(node);
    }
    
    /**
     * Returns the most likely goal value of this node given its learning set.
     *
     * @return The most likely goal value, or -1  if it cannot be determined
     *         (e.g. because the learning set is not defined, or the node's tree
     *         does not specify the attributes of its items).
     **/
    public int mostLikelyGoalValue() {
	ItemSet itemSet = getLearningSet();
	DecisionTree tree = tree();
	
	if (tree == null || itemSet == null)
	    return -1;
	
	SymbolicAttribute goalAttribute = tree.getGoalAttribute();
	
	if (goalAttribute == null && 
	    !itemSet.attributeSet().contains(goalAttribute))
	    return -1;
	
	/* Find the most frequent goal value in the items of the learning set */
	int[] frequencies = new int[goalAttribute.nbValues];
	
	for (int i = 0; i < itemSet.size(); i++)
	    frequencies[itemSet.item(i).
			symbolicValue(itemSet.
				      attributeSet().
				      indexOf(goalAttribute))]++;
	
	int mostFrequent = -1, mostFrequentFrequency = -1;
	for (int gav = 0; gav < goalAttribute.nbValues; gav++)
	    if (frequencies[gav] > mostFrequentFrequency) {
		mostFrequent = gav;
		mostFrequentFrequency = frequencies[gav];
	    }

	return mostFrequent;
    }
}
