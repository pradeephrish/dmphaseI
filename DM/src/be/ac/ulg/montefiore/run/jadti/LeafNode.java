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
 * A leaf node.  A leaf node is a node with no sons.
 **/
public class LeafNode 
    extends Node {
    
    private int goalValue;

    /**
     * Creates a new leaf node.
     **/
    public LeafNode() {
	goalValue = -1;
    }
    
    /**
     * Returns the symbolic value associated to this node.  This value is
     * the goal (guessed) symbolic attribute value associated to this leaf.
     *
     * @return The goal attribute value associated to this node, or -1 if
     *         this value has not been fixed.
     **/
    public int getGoalValue() {
	return goalValue;
    }
    
    /**
     * Set the symbolic value associated to this node.  This value is
     * the goal (guessed) symbolic attribute value associated to this leaf.
     *
     * @param goalValue The goal attribute value associated to this node, or -1
     *                  to unset the current value.
     **/
    public void setGoalValue(int goalValue) {
	if (goalValue < -1)
	    throw new IllegalArgumentException("Argument must be greater or " +
					       "equal to -1");
	this.goalValue = goalValue;
    }
    
    public boolean hasOpenNode() {
	return false;
    }

    protected void updateHasOpenNode() {
    }
    
    protected void replaceSon(Node oldSon, Node newSon) {
	throw new CannotCallMethodException("This node has no son");
    }
    
    public Node son(int sonNb) {
	throw new CannotCallMethodException("Node has no son");
    }

    public int nbSons() {
	return 0;
    }
}
