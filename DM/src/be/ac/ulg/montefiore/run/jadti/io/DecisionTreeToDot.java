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

package be.ac.ulg.montefiore.run.jadti.io;

import be.ac.ulg.montefiore.run.jadti.*;
import java.util.*;


/**
 * Writes a .dot file graph representing a given decision tree.
 * A tool for converting .dot files to other file formats is available at<br>
 * <url>http://www.research.att.com/sw/tools/graphviz/</url>.
 **/
public class DecisionTreeToDot {

    static final String header = "digraph decision_tree {\n";
    static final String tailer = "}\n";
    private DecisionTree tree;
    
    
    /**
     * Build a new decision tree drawer.
     *
     * @param tree The tree to draw.
     **/
    public DecisionTreeToDot(DecisionTree tree) {
	if (tree == null)
	    throw new IllegalArgumentException("Invalid 'null' argument");
	this.tree = tree;
    }

    /**
     * Produce the .dot file content encoded in a String.
     *
     * @return The .dot file content.
     **/
    public String produce() {
	String s = header;

	Iterator nodesIterator = tree.breadthFirstIterator();
	while (nodesIterator.hasNext()) {
	    Node node = (Node) nodesIterator.next();

	    s += produceLabel(node);
	    
	    if (node instanceof TestNode)
		s += produceTransitions((TestNode) node);
	}
	    
	return s + tailer;
    }
    
    private String produceTransitions(TestNode node) {
	String s = "";
	
	for (int i = 0; i < node.nbSons(); i++)
	    s += id(node) + " -> " + id(node.son(i)) +
		" [label = \"" + node.test().issueToString(i) + "\"];\n";

	return s;
    }
    
    private String produceLabel(Node node) {
	if (node instanceof TestNode)
	    return id(node) + " [label=\"" + ((TestNode) node).test() + 
		"\"];\n";
	else /* Leaf */ {
	    LeafNode leafNode = (LeafNode) node;
	    String goalValueString = "";
	    
	    if (leafNode.getGoalValue() >= 0)
		if (tree.getGoalAttribute() != null)
		    goalValueString = " - " + tree.getGoalAttribute().
			valueToString(leafNode.getGoalValue());
		else
		    goalValueString = " - " + leafNode.getGoalValue();
		
	    return id(node) + " [label=\"Leaf" + goalValueString + "\"];\n";
	}
    }

    private String id(Node node) {
	return "\"" + node + "\"";
    }
}
