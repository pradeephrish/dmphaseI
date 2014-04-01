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
 * An instance of this class holds the values of the attributes of an
 * element of the learning/testing set.<p>
 * Each value is represented by a number. 
 * If the value is that of a discrete variable (i.e. the value of a
 * {@link SymbolicAttribute symbolic attribute}), then this number is a
 * positive integer.
 * If the value is that of a numerical variable, its value is encoded as a
 * double.
 **/
public class Item {

    private double[] values;
    
    
    /**
     * Builds a new item.
     *
     * @param attributeSet The set of attributes matching this item.
     * @param values An (non empty) array of attribute values.  If the attribute
     *               is symbolic, its integer value must be converted to a
     *               double.
     **/
    public Item(AttributeSet attributeSet, double[] values) {
	if (values == null || attributeSet == null ||
	    values.length != attributeSet.size())
	    throw new IllegalArgumentException("Invalid argument");
	
	this.values = (double[]) values.clone();
	
	for (int i = 0; i < values.length; i++) {
	    Attribute attribute = attributeSet.attribute(i);
	    
	    if (attribute instanceof SymbolicAttribute) {   /* Symbolic value */
		double value = (int) values[i];
		
		if (value < 0 || 
		    value > ((SymbolicAttribute) attribute).nbValues)
		    throw new IllegalArgumentException("Invalid symbolic " +
						       "value");
	    }
	}
    }
    
    /**
     * Returns the value of an attribute.
     *
     * @param attributes The attribute set matching this item.
     * @param attribute The attribute whose value is to be retreived.
     * @return The attribute value.
     **/
    public Object valueOf(AttributeSet attributes, Attribute attribute) {
	return valueOf(attributes, attributes.indexOf(attribute));
    }
    
    /**
     * Returns the value of an attribute.
     *
     * @param attributes The attribute set matching this item.
     * @param index The attribute index 
     *              (0 <= <code>index</code> < <code>nbAttributes()</code>).
     * @return The attribute value.
     **/
    public Object valueOf(AttributeSet attributes, int index) {
	if (index < 0 && index >= values.length || attributes == null ||
	    attributes.size() != values.length)
	    throw new IllegalArgumentException("Invalid argument");
	
	if (attributes.attribute(index) instanceof SymbolicAttribute)
	    return new Integer((int) values[index]);
	else 
	    return new Double(values[index]);
    }
    
    /* Specialized version of value(AttributeSet, int) */
    int symbolicValue(int index) {
	return (int) values[index];
    }
    
    /* Specialized version of value(AttributeSet, int) */
    double numericalValue(int index) {
	return values[index];
    }

    /**
     * Returns this item's number of attributes.
     *
     * @return A strictly positive number of attributes.
     **/
    int nbAttributes() {
	return values.length;
    }
}
