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
 * An abstract representation of an attribute.
 **/
abstract public class Attribute {
 
    private String name;

    /**
     * Creates an unnamed attribute.
     **/
    public Attribute() {
	this.name = null;
    }

    /**
     * Creates a named attribute.
     *
     * @param name The attribute name.
     **/
    public Attribute(String name) {
	this.name = name;
    }

    /**
     * Returns the attribute's name.
     *
     * @return The attribute's name, or 'null' if no name has been assigned.
     **/
    public String name() {
	return name;
    }

    public String toString() {
	return (name == null) ? "No name defined" : name;
    }
}
