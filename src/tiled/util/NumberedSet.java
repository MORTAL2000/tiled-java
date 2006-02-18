/*
 *  Tiled Map Editor, (c) 2005
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Rainer Deyke <rainerd@eldwood.com>
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <b.lindeijer@xs4all.nl>
 */

package tiled.util;

import java.util.*;

/**
 * A NumberedSet is a generic container of Objects where each element is
 * identified by an integer id.  Unlike with a Vector, the mapping between
 * id and element remains unaffected when elements are deleted.  This means
 * that the set of ids for a NumberedSet may not be contiguous. (A sparse
 * array)
 */
public class NumberedSet {
	
	private Hashtable data;

	/**
	 * Constructs a new empty NumberedSet.
	 */
	public NumberedSet() {
		data = new Hashtable();
	}

	/**
	 * Returns the element for a specific element, or null if the id does not
	 * identify any element in this NumberedSet.
	 * 
	 * @param id
	 */
	public Object get(int id) {
		return data.get(""+id);
	}

	/**
	 * This get() is mainly used by NumberedSetIterator
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		return data.get(key);
	}
	
	/**
	 * Returns true if the NumberedSet contains an element for the specified id.
	 * 
	 * @param id
	 */
	public boolean containsId(int id) {
		return get(id) != null;
	}

	/**
	 * Sets the element for the specified id, replacing any previous element that
	 * was associated with that id.  id should be a relatively small positive
	 * integer.
	 * 
	 * @param id
	 * @param o
	 */
	public void put(int id, Object o) {
		if (id < 0) throw new IllegalArgumentException();
		data.put(""+id, o);
	}

	/**
	 * Removes the element associated with the given id from the NumberedSet.
	 */
	public void remove(int id) {
		data.remove(""+id);
	}

	/**
	 * Returns the last id in the NumberedSet that is associated with an element,
	 * or -1 if the NumberedSet is empty.
	 */
	public int getMaxId() {
		int id = -1;
		Iterator itr = data.keySet().iterator();
		while(itr.hasNext()) {
			int i = Integer.parseInt((String)itr.next());
			if(i>id) id = i;
		}
		
		return id+1;
	}
	
	/**
	 * Returns an iterator to iterate over the elements of the NumberedSet.
	 * 
	 * @return
	 */
	public NumberedSetIterator iterator() {
		return new NumberedSetIterator(this);
	}

	/**
	 * Adds a new element to the NumberedSet and returns its id.
	 */
	public int add(Object o) {
	  int id = getMaxId();
	  put(id, o);
	  return id;
	}

	/**
	 * Returns the id of the first element of the NumberedSet that is euqal to
	 * the given object, or -1 otherwise.
	 * 
	 * @param o
	 */
	public int indexOf(Object o) {
		if(contains(o)) {
			Iterator itr = data.keySet().iterator();
			while(itr.hasNext()) {
				String key = (String)itr.next();
				if(o.equals(data.get(key))) {
					return Integer.parseInt(key);
				}
			}
		}
		return -1;
	}

	/**
	 * Returns true if at least one element of the NumberedSet is equal to the
	 * given object.
	 */
	public boolean contains(Object o) {
		return data.containsValue(o);
	}

	/**
	 * If this NumberedSet already contains an element equal to the given object,
	 * return its id.  Otherwise insert the given object into the NumberedSet
	 * and return its id.
	 */
	public int findOrAdd(Object o) {
		int id = indexOf(o);
		if (id != -1) return id;
		return add(o);
	}

	/**
	 * Returns the number of actual elements in the NumberedSet.  This operation
	 * is unfortunately somewhat slow because it requires iterating over the
	 * underlying Vector.
	 */
  	public int size() {
  		return data.size();
  	}
}