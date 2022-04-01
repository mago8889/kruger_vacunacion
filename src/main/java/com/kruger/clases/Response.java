package com.kruger.clases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {

	private int id;
	private String Description;
	private Object objectResponse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Object getObjectResponse() {
		return objectResponse;
	}
	public void setObjectResponse(Object objectResponse) {
		
		ObjectMapper mapper = new ObjectMapper();
		String val = null;
		try {
			val = mapper.writeValueAsString(objectResponse);
			this.objectResponse = val;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
