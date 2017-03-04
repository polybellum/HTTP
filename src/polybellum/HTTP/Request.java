package polybellum.HTTP;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {
	private String _verb = "GET", _errorMessage = "";
	private URL _url = null;
	private byte[] _dataBytes = null;
	private boolean _valid = true;
	
	private Request(boolean valid, URL url, String verb, byte[] dataBytes){
		_valid = valid;
		_verb = verb;
		_url = url;
		_dataBytes = dataBytes;
	}
	
	private Request(boolean valid, String errorMessage){
		_errorMessage = errorMessage;
	}
	
	public String getVerb(){
		return _verb;
	}
	
	public String getErrorMessage(){
		return _errorMessage;
	}
	
	public URL getURL(){
		return _url;
	}
	
	public byte[] getDataBytes(){
		return _dataBytes;
	}
	
	public boolean isValid(){
		return _valid;
	}
	
	public static Request build(URL url, String verb){
		return new Request(true, url, verb, null);
	}
	
	public static Request build(String url, String verb){
		try{
			return new Request(true, new URL(url), verb, null);
		}catch(MalformedURLException e){
			return new Request(false, null, null, null);
		}
	}
	
	public static Request build(URL url, HTTPVerb verb, byte[] dataBytes){
		return new Request(true, url, verb.toString(), dataBytes);
	}
	
	public static Request build(String url, HTTPVerb verb, byte[] dataBytes){
		try{
			return new Request(true, new URL(url), verb.toString(), dataBytes);
		}catch(MalformedURLException e){
			return new Request(false, null, null, null);
		}
	}
	
	public static Request build(URL baseUrl, HTTPVerb verb, NameValueSet nvs){
		try {
			return new Request(true, HTTPUtil.appendQueryStringToUrl(baseUrl, nvs), verb.toString(), null);
		} catch (Exception e) {
			return new Request(false, null, null, null);
		}
	}
	
	public static Request build(String baseUrl, HTTPVerb verb, NameValueSet nvs){
		try {
			return new Request(true, HTTPUtil.appendQueryStringToUrl(new URL(baseUrl), nvs), verb.toString(), null);
		} catch (Exception e) {
			return new Request(false, null, null, null);
		}
	}
}
