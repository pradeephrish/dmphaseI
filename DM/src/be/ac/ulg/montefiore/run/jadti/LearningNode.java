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
 * A learning node is a node with an associated learning set.  A learning set
 * is a set of items.<p>
 * Each node implementing this interface should ensure that if it is replaced
 * by a node N, N also implements this interface.
 **/
public interface LearningNode {
    /**
     * Sets the current learning set.
     *
     * @param set A (potentially 'null') learning set.
     **/
    public void setLearningSet(ItemSet set);

    /**
     * Gets the current learning set.
     *
     * @return The current (potentially 'null') learning set.
     **/
    public ItemSet getLearningSet();
}
