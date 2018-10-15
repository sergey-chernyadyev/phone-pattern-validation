package ru.itdt.opora.document;

import java.util.List;

public class Document{
	private List<FieldSet> fieldSets;
	
	public Document(List<FieldSet> fieldSets){
		this.fieldSets = fieldSets;
	}	
	
	public List<FieldSet> getFieldSet(String name){
		return fieldSets;		
	}
}