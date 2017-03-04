package polybellum.HTTP;

/*/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// IMPORT LIBRARIES //////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////*/

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A class representing a generic Http Request
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class Request {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// MEMBER VARIABLES //////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * The Http verb and error message values
	 */
	private String _verb = "GET", _errorMessage = "";
	
	/**
	 * The URL to connect to
	 */
	private URL _url = null;
	
	/**
	 * Data to send to the URL
	 */
	private byte[] _dataBytes = null;
	
	/**
	 * Whether or not this is a valid request
	 */
	private boolean _valid = true;
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____////////////////////////////PRIVATE CONSTRUCTORS //////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Create a generic Http Request
	 * 
	 * @param valid Whether or not this Request is valid
	 * @param url The URL to connect to
	 * @param verb The Http Verb to use
	 * @param dataBytes Data to send to the URL (can be null)
	 */
	private Request(boolean valid, URL url, String verb, byte[] dataBytes){
		_valid = valid;
		_verb = verb;
		_url = url;
		_dataBytes = dataBytes;
	}
	
	/**
	 * A constructor for when there was an error building a Request
	 * 
	 * @param valid Whether or not this Request is  valid
	 * @param errorMessage An error message describing what went wrong creating this Request
	 */
	private Request(boolean valid, String errorMessage){
		_errorMessage = errorMessage;
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PUBLIC METHODS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Getter for the data bytes this Request intends to write to the URL
	 * 
	 * @return This Request's data output bytes
	 */
	public byte[] getDataBytes(){
		return _dataBytes;
	}
	
	/**
	 * Getter for the error message for this Request if it has one
	 * 
	 * @return This Request's error message String
	 */
	public String getErrorMessage(){
		return _errorMessage;
	}
	
	/**
	 * Getter for the URL this Request intends to connect to
	 * 
	 * @return The URL this Request intends to connect to
	 */
	public URL getURL(){
		return _url;
	}
	
	/**
	 * Getter for the Http Verb this Request intends to use
	 * 
	 * @return The Http Verb set for this Request
	 */
	public String getVerb(){
		return _verb;
	}
	
	/**
	 * Return if this Request is valid
	 * 
	 * @return Whether or not this is a valid Request
	 */
	public boolean isValid(){
		return _valid;
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____//////////////////////////// PUBLIC STATIC METHODS ////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param url The URL to connect to
	 * @param verb The Http Verb to use
	 * @return A built Request object
	 */
	public static Request build(URL url, String verb){
		return new Request(true, url, verb, null);
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param url The URL to connect to
	 * @param verb The Http Verb to use
	 * @return A built Request object
	 */
	public static Request build(String url, String verb){
		try{
			return new Request(true, new URL(url), verb, null);
		}catch(MalformedURLException e){
			return new Request(false, e.getMessage());
		}
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param url The URL to connect to
	 * @param verb The Http Verb to use
	 * @param dataBytes Data to write to the URL
	 * @return A built Request object
	 */
	public static Request build(URL url, HttpVerb verb, byte[] dataBytes){
		return new Request(true, url, verb.toString(), dataBytes);
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param url The URL to connect to
	 * @param verb The Http Verb to use
	 * @param dataBytes Data to write to the URL
	 * @return A built Request object
	 */
	public static Request build(String url, HttpVerb verb, byte[] dataBytes){
		try{
			return new Request(true, new URL(url), verb.toString(), dataBytes);
		}catch(MalformedURLException e){
			return new Request(false, e.getMessage());
		}
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param baseUrl The URL to connect to
	 * @param verb The Http Verb to use
	 * @param nvs A set of name value pairs to write to the URL
	 * @return A built Request object
	 */
	public static Request build(URL baseUrl, HttpVerb verb, NameValueSet nvs){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true,
						HttpUtil.appendQueryStringToUrl(baseUrl, nvs),
						verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, baseUrl, verb.toString(), nvs.toString().getBytes());
			}else{
				return new Request(true, baseUrl, verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param baseUrl The URL to connect to
	 * @param verb The Http Verb to use
	 * @param nvs A set of name value pairs to write to the URL
	 * @return A built Request object
	 */
	public static Request build(String baseUrl, HttpVerb verb, NameValueSet nvs){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true,
						HttpUtil.appendQueryStringToUrl(new URL(baseUrl), nvs),
						verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, new URL(baseUrl), verb.toString(),
						nvs.toString().getBytes());
			}else{
				return new Request(true, new URL(baseUrl), verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param baseUrl The URL to connect to
	 * @param verb The Http Verb to use
	 * @param parameters A set of name value pairs to write to the URL
	 * @return A built Request object
	 */
	public static Request build(URL baseUrl, HttpVerb verb, String... parameters){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true,
						HttpUtil.appendQueryStringToUrl(baseUrl,
								NameValueSet.fromStringArray(parameters)),
						verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, baseUrl, verb.toString(), 
						NameValueSet.fromStringArray(parameters).toString().getBytes());
			}else{
				return new Request(true, baseUrl, verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
	
	/**
	 * Build a Request from Parameters
	 * 
	 * @param baseUrl The URL to connect to
	 * @param verb The Http Verb to use
	 * @param parameters A set of name value pairs to write to the URL
	 * @return A built Request object
	 */
	public static Request build(String baseUrl, HttpVerb verb, String... parameters){
		try {
			if(verb == HttpVerb.HEAD || verb == HttpVerb.GET || verb == HttpVerb.TRACE){
				return new Request(true, HttpUtil.appendQueryStringToUrl(new URL(baseUrl),
						NameValueSet.fromStringArray(parameters)), verb.toString(), null);
			}else if(verb == HttpVerb.POST){
				return new Request(true, new URL(baseUrl), verb.toString(), 
						NameValueSet.fromStringArray(parameters).toString().getBytes());
			}else{
				return new Request(true, new URL(baseUrl), verb.toString(), null);
			}
		} catch (Exception e) {
			return new Request(false, e.getMessage());
		}
	}
}
