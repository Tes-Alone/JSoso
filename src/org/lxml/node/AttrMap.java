package org.lxml.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AttrMap {

	private Map<String, String> attrMap;
	
	AttrMap() {
		attrMap = new HashMap<>();
	}
	
	public Set<String> getAttrNames() {
		return attrMap.keySet();
	}
	
	public String getAttrValue(String name) {
		if (name == null)
			throw new NullPointerException();
		
		if (name.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		return attrMap.get(name);
	}
	
	public void addAttr(String name, String value) {
		if (name == null || value == null)
			throw new NullPointerException();
		
		//if (name.isEmpty() || value.isEmpty())
			//throw new IllegalArgumentException("参数为空串.");
		
		attrMap.put(name, value);
	}
	
	public void removeAttr(String name) {
		if (name == null)
			throw new NullPointerException();
		
		if (name.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		attrMap.remove(name);
	}
	
	public void removeAll() {
		attrMap.clear();
	}
	
	public int size() {
		return attrMap.size();
	}
}
