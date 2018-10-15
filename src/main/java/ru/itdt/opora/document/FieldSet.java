package ru.itdt.opora.document;

import java.util.HashMap;
import java.util.Map;

public class FieldSet {
	private Map<String,Object> fields;
	
	public FieldSet(){
		this.fields = new HashMap<String, Object>();
	}
	
	public FieldSet(Map<String,Object> fields){
		this.fields = fields;
	}
	
	public Map<String,Object> getFields(){
		return fields;
	}
	
	public Object getField(String name){
		return fields.get(name);
	}
	
}