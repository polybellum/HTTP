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
			return new Request(false, e.getMessage());
		}
	}
	
	public static Request build(URL url, HttpVerb verb, byte[] dataBytes){
		return new Request(true, url, verb.toString(), dataBytes);
	}
	
	public static Request build(String url, HttpVerb verb, byte[] dataBytes){
		try{
			return new Request(true, new URL(url), verb.toString(), dataBytes);
		}catch(MalformedURLException e){
			return new Request(false, e.getMessage());
		}
	}
	
	public static Request build(URL baseUrl, HttpVerb verb, NameValueSet nvs){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true, HttpUtil.appendQueryStringToUrl(baseUrl, nvs), verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, baseUrl, verb.toString(), nvs.toString().getBytes());
			}else{
				return new Request(true, baseUrl, verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
	
	public static Request build(String baseUrl, HttpVerb verb, NameValueSet nvs){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true, HttpUtil.appendQueryStringToUrl(new URL(baseUrl), nvs), verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, new URL(baseUrl), verb.toString(), nvs.toString().getBytes());
			}else{
				return new Request(true, new URL(baseUrl), verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
	
	public static Request build(URL baseUrl, HttpVerb verb, String... parameters){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true, HttpUtil.appendQueryStringToUrl(baseUrl, NameValueSet.fromStringArray(parameters)), verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, baseUrl, verb.toString(), NameValueSet.fromStringArray(parameters).toString().getBytes());
			}else{
				return new Request(true, baseUrl, verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
	
	public static Request build(String baseUrl, HttpVerb verb, String... parameters){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true, HttpUtil.appendQueryStringToUrl(new URL(baseUrl), NameValueSet.fromStringArray(parameters)), verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, new URL(baseUrl), verb.toString(), NameValueSet.fromStringArray(parameters).toString().getBytes());
			}else{
				return new Request(true, new URL(baseUrl), verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
}
