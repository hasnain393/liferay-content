package com.documentrest.application;

public class Document {

	public long   id ;
	public String filename;
	public String file;
	
	public Document() {}
	
	public Document(long id, String filename, String file) {
		this.id = id;
		this.filename = filename;
		this.file = file;
		
	}	
}