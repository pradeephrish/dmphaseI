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

import java.io.*;
import java.util.*;

/**
 * This class reads a database file. <p>
 * The database format is very simple. The first line is the name of the 
 * database. The second line contains the attribute names immediately followed
 * by their types : numerical or symbolic. A numerical attribute can be real or
 * integer (anyway it will be converted to a double value). A symbolic
 * attribute denotes any discrete attribute. Its value can be symbols, numbers
 * or both of them.<p>
 * Each line corresponds to one example and contains the values of the
 * attributes separated by white spaces. <P>
 * If the first attribute name is object and its type is name, then the first 
 * column is the object name (otherwise the name will be compute on the fly
 * based on the line number).<p>
 * Comment lines start with a ';'. <p>
 */

public class ItemSetReader {

    /* Holds a temporary symbolic attribute */
    static private class MutableAttribute {
	final String name;
	final Vector values;
	
	MutableAttribute(String name) {
	    this.name = name;
	    this.values = new Vector();
	}
	
	void add(String value) {
	    values.add(value);
	}
	
	/* Returns -1 if an object named 'name' is not found */
	int find(String name) {
	    return (values.indexOf(name));
	}

	int nbValues() {
	    return values.size();
	}

	SymbolicAttribute toSymbolicAttribute() {
	    return new IdSymbolicAttribute(name, values);
	}
    }
    
    
    /**
     * Reads a database file.
     * 
     * @param reader Holds a reader of the observation sequence file.
     */
    static public ItemSet read(Reader reader) 
	throws IOException, FileFormatException {
	
	StreamTokenizer st = new StreamTokenizer(reader);
	int lineNumber = 1;
	boolean named = false;           /* Is first column the item's name ? */
	Vector attributes = null;
	Vector items = new Vector();

	initSyntaxTable(st);
	
	
	for (st.nextToken(); st.ttype != StreamTokenizer.TT_EOF; 
	     st.nextToken()) {
	    
	    if (st.ttype == StreamTokenizer.TT_EOL)
		continue;

	    st.pushBack();

	    if (lineNumber == 1)
		readFirstLine(st);          /* The database name is forgotten */
	    else if (lineNumber == 2)
		attributes = readAttributesLine(st);
	    else
		items.add(readLine(st, named, attributes, lineNumber));
	    
	    lineNumber++;
	}
	
	return buildItemSet(attributes, items);
    }

    /* Initialize the syntax table of a stream tokenizer */
    static private void initSyntaxTable(StreamTokenizer st) {
	st.resetSyntax();
	st.parseNumbers();
	st.whitespaceChars((int) ' ', (int) ' ');
	st.whitespaceChars((int) '\t', (int) '\t');
	st.wordChars('a', 'z');
	st.wordChars('A', 'Z');
	st.eolIsSignificant(true);
	st.commentChar((int) ';');
    }
    
    static private String readFirstLine(StreamTokenizer st) 
	throws FileFormatException, IOException {
	String name;
		
	if (st.nextToken() != StreamTokenizer.TT_WORD)
	    throw new FileFormatException("Invalid database name" + st.ttype);
	
	name = st.sval;
	
	if (st.nextToken() != StreamTokenizer.TT_EOL)
	    throw new FileFormatException("First line must only hold one word" +
					  " (the database name)");
	
	return name;
    }
    
    static private Vector readAttributesLine(StreamTokenizer st) 
	throws FileFormatException, IOException {

	Vector attributes = new Vector();
	String name;
	
	for (st.nextToken(); st.ttype != StreamTokenizer.TT_EOL; 
	     st.nextToken()) {
	    st.pushBack();
	    
	    if (st.nextToken() != StreamTokenizer.TT_WORD)
		throw new FileFormatException("Invalid attribute name");
	    
	    name = st.sval;
	    
	    if (st.nextToken() != StreamTokenizer.TT_WORD)
		throw new FileFormatException("Attribute name expected");
	    
	    if (st.sval.equals("symbolic"))
		attributes.add(new MutableAttribute(name));
	    else if (st.sval.equals("numerical"))
		attributes.add(new NumericalAttribute(name));
	    else if (name.equals("object") && st.sval.equals("name") && 
		       attributes.size() == 0)
		attributes.add(new MutableAttribute("name"));
	    else
		throw new FileFormatException("Attributes must be followed by" +
					      " their type ('symbolic' or " +
					      "'numerical')");
	}
	
	if (attributes.size() == 0)
	    throw new FileFormatException("No attribute defined");
	
	return attributes;
    }
    
    static private double[] readLine(StreamTokenizer st, boolean named,
				 Vector attributes, int lineNumber)
	throws FileFormatException, IOException {
	
	double[] values = new double[attributes.size()];
	int attributeNb = 0;
	
	
	for (st.nextToken(); st.ttype != StreamTokenizer.TT_EOL && 
		 attributeNb < attributes.size(); 
	     st.nextToken(), attributeNb++) {
	    
	    switch(st.ttype) {
	    case StreamTokenizer.TT_WORD:
		if (!(attributes.elementAt(attributeNb) instanceof
		      MutableAttribute))
		    throw new FileFormatException("Symbolic value expected");
		
		MutableAttribute attribute =
		    (MutableAttribute) attributes.elementAt(attributeNb);
		
		if ((values[attributeNb] = 
		     (double) attribute.find(st.sval)) == -1.) {
		    values[attributeNb] = (double) attribute.nbValues();
		    attribute.add(st.sval);
		}
		break;
		
	    case StreamTokenizer.TT_NUMBER:
		if (!(attributes.elementAt(attributeNb) instanceof
		      NumericalAttribute))
		    throw new FileFormatException("Numeric value expected");
		values[attributeNb] = (double) st.nval;
		break;
		
	    default:
		throw new FileFormatException("Word or number expected");
	    }
	}
	
	if (attributeNb != attributes.size() || 
	    st.ttype != StreamTokenizer.TT_EOL)
	    throw new FileFormatException("Bad number of attributes");
	
	return values;
    }

    static private ItemSet buildItemSet(Vector attributes, Vector items) {
	/* Build attribute set */
	for (int i = 0; i < attributes.size(); i++) {
	    Object attribute = attributes.elementAt(i);
	    
	    if (attribute instanceof MutableAttribute)
		attributes.set(i, ((MutableAttribute) attribute).
			       toSymbolicAttribute());
	}
	AttributeSet attributeSet = new AttributeSet(attributes);

	/* Build items */
	ItemSet itemSet = new ItemSet(new AttributeSet(attributes));
	for (int i = 0; i < items.size(); i++) 
	    itemSet.add(new Item(attributeSet, (double[]) items.elementAt(i)));
	
	return itemSet;
    }
}
