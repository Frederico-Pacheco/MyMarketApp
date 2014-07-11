package br.com.mymarket.converters;

import org.json.JSONObject;

public class TokenConverter {
	   public String convert(String json) {
	        try {
	        	JSONObject js = new JSONObject(json);
	        	return js.getString("token");
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
