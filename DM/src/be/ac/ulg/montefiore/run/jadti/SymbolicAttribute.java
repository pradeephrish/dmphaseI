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
 * A symbolic attribute.  Symbolic attributes have a finite set of
 * possible values represented by a positive integer.
 **/
public class SymbolicAttribute 
    extends Attribute {
    
    public final int nbValues;
    
    
    /**
     * Builds a new unnamed symbolic attribute.
     * 
     * @param nbValues The number of different values allowed for this 
     *                 attribute.
     **/
    public SymbolicAttribute(int nbValues) {
	super();
	this.nbValues = nbValues;
    }
    
    /**
     * Builds a new named symbolic attribute.
     *
     * @param name The attribute name.
     * @param nbValues The number of different values allowed for this 
     *                 attribute.
     **/
    public SymbolicAttribute(String name, int nbValues) {
	super(name);
	this.nbValues = nbValues;
    }
    
    /**
     * Converts a symbolic value to string.
     *
     * @param index The index of the value to convert.
     * @return The value converted to a String.
     */
    public String valueToString(int index) {
	if (index < 0 || index >= nbValues)
	    throw new IllegalArgumentException("Invalid index");
	
	return "" + index;
    }
}
