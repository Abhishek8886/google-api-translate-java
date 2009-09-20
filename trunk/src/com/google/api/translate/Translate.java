/**
 * Translate.java
 *
 * Copyright (C) 2007,  Richard Midwinter
 *
 * This file is part of google-api-translate-java.
 *
 * google-api-translate-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * google-api-translate-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with google-api-translate-java.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.google.api.translate;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.GoogleAPI;
import com.tecnick.htmlutils.htmlentities.HTMLEntities;

/**
 * Makes the Google Translate API available to Java applications.
 * 
 * @author Richard Midwinter
 * @author Mike Nereson
 * @author Emeric Vernat
 * @author Juan B Cabral
 */
public final class Translate extends GoogleAPI {
	
	/**
	 * Constants.
	 */
    private static final String
    		LANG_PARAM = "&langpair=",
    		TEXT_PARAM = "&q=",
    		PIPE_PARAM = "%7C",
    		URL = "http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&langpair=#FROM#%7C#TO#&q=";

    /**
     * Translates text from a given language to another given language using Google Translate.
     * 
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String execute(final String text, final Language from, final Language to) throws Exception {
    	validateReferrer();
    	
    	final URL url = new URL(URL.replaceAll("#FROM#", from.toString()).replaceAll("#TO#", to.toString())
    			+URLEncoder.encode(text, ENCODING));
    	
    	final JSONObject json = retrieveJSON(url);
    	
    	return getJSONResponse(json);
    }

    /**
     * Translates an array of text Strings from a given language to another given language using Google Translate.
     * 
     * @param text The array of Strings to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String[] execute(final String[] text, final Language from, final Language to) throws Exception {
    	validateReferrer();
    	
    	final String[] responses = new String[text.length];
    	
    	for (int i = 0; i<text.length; i++) {
	    	responses[i] = execute(text[i], from, to);
    	}
    	
    	return responses;
    }

    /**
     * Translates an array of text Strings from a given language to another given language using Google Translate.
     * 
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The array of Languages to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String[] execute(final String text, final Language from, final Language[] to) throws Exception {
    	validateReferrer();
    	
    	final String[] responses = new String[to.length];
    	
    	for (int i = 0; i<to.length; i++) {
	    	responses[i] = execute(text, from, to[i]);
    	}
    	
    	return responses;
    }

    /**
     * Translates text from a given language to another given language using Google Translate.
     * 
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String[] execute(final String[] text, final Language from[], final Language[] to) throws Exception {
    	validateReferrer();
    	
    	if (text.length != from.length || from.length != to.length) {
    		throw new Exception(
    				"[google-api-translate-java] The same number of texts, from and to languages must be supplied.");
    	}
    	
    	if (text.length == 1) {
    		return new String[] { execute(text[0], from[0], to[0]) };
    	}
    	
    	final String[] responses = new String[text.length];
    	
    	final StringBuilder urlBuilder = new StringBuilder();
    	
    	urlBuilder.append(URL.replaceAll("#FROM#", from[0].toString()).replaceAll("#TO#", to[0].toString()));
    	urlBuilder.append(URLEncoder.encode(text[0], ENCODING));
    	
    	for (int i = 1; i<text.length; i++) {
    		urlBuilder.append(LANG_PARAM);
    		urlBuilder.append(from[i].toString());
    		urlBuilder.append(PIPE_PARAM);
    		urlBuilder.append(to[i].toString());
    		urlBuilder.append(TEXT_PARAM);
    		urlBuilder.append(URLEncoder.encode(text[i].toString(), ENCODING));
    	}
    	
    	final URL url = new URL(urlBuilder.toString());
    	
    	final JSONObject root = retrieveJSON(url);
    	
    	final JSONArray json = root.getJSONArray("responseData");
    	
    	for (int i = 0; i<json.length(); i++) {
    		final JSONObject obj = json.getJSONObject(i);
    	
	    	responses[i] = getJSONResponse(obj);
    	}
    	
    	return responses;
    }
    
    /**
     * Returns the JSON response data as a String. Throws an exception if the status is not a 200 OK.
     * 
     * @param json The JSON object to retrieve the response data from.
     * @return The responseData from the JSONObject.
     * @throws Exception If the responseStatus is not 200 OK.
     */
    private static String getJSONResponse(final JSONObject json) throws Exception {
    	if (json.getString("responseStatus").equals("200")) {
    		final String translatedText = json.getJSONObject("responseData").getString("translatedText");
    		return HTMLEntities.unhtmlentities(translatedText);
    	} else {
    		throw new Exception("Google returned the following error: [" +json.getString("responseStatus") +"] "
    				+json.getString("responseDetails"));
    	}
    }
    
    /**
     * @deprecated Replaced by {@link execute()}.
     */
    @Deprecated
    public static String translate(final String text, final Language from, final Language to) throws Exception {
    	return execute(text, from, to);
    }
}