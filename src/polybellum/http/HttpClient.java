// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>
	
package polybellum.http;

/*/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// IMPORT LIBRARIES //////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * A class representing an Http Web Client
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class HttpClient {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// MEMBER VARIABLES //////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * A map of connection properties for this Http Client
	 */
	private HashMap<String, String> _connectionProperties;
	
	/**
	 * The connect timeout in ms
	 */
	private int _connectTimeout = 0;
	
	/**
	 * The read timeout in ms
	 */
	private int _readTimeout = 0;

/*__///////////////////////////////////////////////////////////////////////////////////////////////
____///////////////////////////////// CONSTRUCTORS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Initialize this Http Client
	 */
	public HttpClient(){
		_connectionProperties = new HashMap<String, String>();
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PRIVATE METHODS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Perform an Http Request
	 * 
	 * @param url The URL to connect to
	 * @param method The Http Verb to use
	 * @param outputData Data to write to the URL
	 * @return The Http Response from the URL
	 */
	private Response httpRequest(URL url, String method, byte[] outputData){
		
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		
		//Try to open and write/read from the connection
		try {
			//Set the Content-Length property if we are to be writing data
			if(outputData != null){	
				setRequestProperty("Content-Length", String.valueOf(outputData.length));
			}
			//Set the connection properties and begin connecting
			connection = prepareConnection(url, method, outputData != null);
			//Write the data if we are writing data
			if(outputData != null){
				OutputStream wr = connection.getOutputStream();
				wr.write(outputData);
				wr.close();
			}
			//Read the response from the server
			inputStream = connection.getInputStream();
			return new HttpResponse(connection.getResponseCode(), 
					HttpUtil.readInputStreamBytes(inputStream));
		}
		catch (IOException e) { //There was an error reading/writing
			return new ExceptionResponse(e.getMessage()); 
		}
		finally { //Disconnect and close the streams
				if(connection != null) connection.disconnect();
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						return new ExceptionResponse(e.getMessage());
					} 
				}
		}
		
	}
	
	/**
	 * Prepare a connection to be read from / written to
	 * 
	 * @param url The URL to connect to
	 * @param method The Http Verb to use
	 * @param doOutput If we are writing output to the URL
	 * @return An Http connection to the URL with all the parameters set
	 * @throws IOException There may be an exception connecting to the URL
	 */
	private HttpURLConnection prepareConnection(URL url, String method, boolean doOutput) 
			throws IOException{
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		setConnectionProperties(connection);
		connection.setRequestMethod(method);
		connection.setDoOutput(doOutput);
		return connection;
		
	}
	
	/**
	 * Set this Http Client's connection properties for a URL connection
	 * @param connection
	 */
	private void setConnectionProperties(URLConnection connection){
		
		for(String key : _connectionProperties.keySet()){
			connection.setRequestProperty(key, _connectionProperties.get(key));
		}
		connection.setConnectTimeout(_connectTimeout);
		connection.setReadTimeout(_readTimeout);
		
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PUBLIC METHODS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Perform an HTTP Delete request
	 * 
	 * @param url The URL to connect to
	 * @return The Http Response from the Server
	 */
	public Response delete(String url){
		try {
			return delete(new URL(url));
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Delete request
	 * 
	 * @param url The URL to connect to
	 * @return The Http Response from the Server
	 */
	public Response delete(URL url){
		return httpRequest(url, HttpVerb.DELETE.toString(), null);
	}
	
	/**
	 * Execute a generic Request object using this Http Client
	 * 
	 * @param request The generic Request to perform
	 * @return The Http Response from the Server
	 */
	public Response execute(Request request){
		if(!request.isValid()){
			return new ExceptionResponse("The request specified is invalid: "
					+ request.getErrorMessage());
		}
		if(request.getVerb().equals(HttpVerb.GET.toString())){
			return get(request.getURL());
		}else if(request.getVerb().equals(HttpVerb.POST.toString())){
			return post(request.getURL(), request.getDataBytes());
		}else if(request.getVerb().equals(HttpVerb.HEAD.toString())){
			return head(request.getURL());
		}else if(request.getVerb().equals(HttpVerb.PUT.toString())){
			return put(request.getURL(), request.getDataBytes());
		}else if(request.getVerb().equals(HttpVerb.OPTIONS.toString())){
			return options(request.getURL(), request.getDataBytes());
		}else if(request.getVerb().equals(HttpVerb.TRACE.toString())){
			return trace(request.getURL(), request.getDataBytes());
		}else if(request.getVerb().equals(HttpVerb.DELETE.toString())){
			return delete(request.getURL());
		}else{
			return new ExceptionResponse("A valid HTTP verb must be specified.");
		}
	}
	
	/**
	 * Perform an HTTP Get request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param queryParameters Query string parameters
	 * @return The Http Response from the Server
	 */
	public Response get(URL baseUrl, String... queryParameters){
		try {
			URL url = HttpUtil.appendQueryStringToUrl(baseUrl, queryParameters);
			return httpRequest(url, HttpVerb.GET.toString(), null);
		} catch (Exception e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Get request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param nvs Query string parameters in a Name Value Set
	 * @return The Http Response from the Server
	 */
	public Response get(URL baseUrl, NameValueSet nvs){
		try {
			URL url = HttpUtil.appendQueryStringToUrl(baseUrl, nvs);
			return httpRequest(url, HttpVerb.GET.toString(), null);
		} catch (Exception e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Get request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param queryParameters Query string parameters
	 * @return The Http Response from the Server
	 */
	public Response get(String baseUrl, String... parameters){
		try {
			return get(new URL(baseUrl), parameters);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Get request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param nvs Query string parameters in a Name Value Set
	 * @return The Http Response from the Server
	 */
	public Response get(String baseUrl, NameValueSet nvs){
		try {
			return get(new URL(baseUrl), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Head request
	 * 
	 * @param url The URL to connect to
	 * @return The Http Response from the Server
	 */
	public Response head(String url){
		try {
			return head(new URL(url));
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Head request
	 * 
	 * @param url The URL to connect to
	 * @return The Http Response from the Server
	 */
	public Response head(URL url){
		return httpRequest(url, HttpVerb.HEAD.toString(), null);
	}
	
	/**
	 * Perform an HTTP Options request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server
	 * @return The Http Response from the Server
	 */
	public Response options(URL baseUrl, byte[] data){
		return httpRequest(baseUrl, HttpVerb.OPTIONS.toString(), data);
	}
	
	/**
	 * Perform an HTTP Options request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server in Name Value format
	 * @return The Http Response from the Server
	 */
	public Response options(URL baseURL, String... data){
		if(data.length == 0){
			return options(baseURL, new byte[0]);
		}else{
			try{
				return options(baseURL, NameValueSet.fromStringArray(data));
			}catch(Exception e){
				return new ExceptionResponse(e.getMessage());
			}
		}
	}
	
	/**
	 * Perform an HTTP Options request
	 * 
	 * @param url The URL to connect to
	 * @param nvs The data to write to the server in Name Value format
	 * @return The Http Response from the Server
	 */
	public Response options(URL baseURL, NameValueSet nvs){
		return options(baseURL, nvs.toString().getBytes());
	}
	
	/**
	 * Perform an HTTP Options request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server
	 * @return The Http Response from the Server
	 */
	public Response options(String url, byte[] data){
		try {
			return options(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Options request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server in Name Value format
	 * @return The Http Response from the Server
	 */
	public Response options(String url, String... data){
		try {
			return options(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Options request
	 * 
	 * @param url The URL to connect to
	 * @param nvs The data to write to the server in Name Value format
	 * @return The Http Response from the Server
	 */
	public Response options(String url, NameValueSet nvs){
		try {
			return options(new URL(url), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Post request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server
	 * @return The Http Response from the Server
	 */
	public Response post(URL baseUrl, byte[] data){
		return httpRequest(baseUrl, HttpVerb.POST.toString(), data);
	}
	
	/**
	 * Perform an HTTP Post request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response post(URL baseURL, String... data){
		if(data.length == 0){
			return post(baseURL, new byte[0]);
		}else{
			try{
				return post(baseURL, NameValueSet.fromStringArray(data));
			}catch(Exception e){
				return new ExceptionResponse(e.getMessage());
			}
		}
	}
	
	/**
	 * Perform an HTTP Post request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param nvs The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response post(URL baseURL, NameValueSet nvs){
		return post(baseURL, nvs.toString().getBytes());
	}
	
	/**
	 * Perform an HTTP Post request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server
	 * @return The Http Response from the Server
	 */
	public Response post(String url, byte[] data){
		try {
			return post(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Post request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response post(String url, String... data){
		try {
			return post(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Post request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param nvs The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response post(String url, NameValueSet nvs){
		try {
			return post(new URL(url), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(String url, byte[] data){
		try {
			return put(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(URL url, byte[] data){
		return httpRequest(url, HttpVerb.PUT.toString(), data);
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(URL url, String data){
		return put(url, data.getBytes());
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(String url, String data){
		try {
			return put(new URL(url), data.getBytes());
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param The Content-Type Http Property for the output data
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(String url, ContentType contentType, byte[] data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param The Content-Type Http Property for the output data
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(URL url, ContentType contentType, byte[] data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param The Content-Type Http Property for the output data
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(URL url, ContentType contentType, String data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	/**
	 * Perform an HTTP Put request
	 * 
	 * @param url The URL to connect to
	 * @param The Content-Type Http Property for the output data
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response put(String url, ContentType contentType, String data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	/**
	 * Set the connection timeout for this Http Client in milliseconds
	 * @param timeout The connection timeout in milliseconds
	 */
	public void setConnectTimeout(int timeout){
		if(timeout < 0) throw new IllegalArgumentException("Timeout cannot be negative!");
		_connectTimeout = timeout;
	}
	
	/**
	 * Set the Content-type Http property for this Http client
	 * 
	 * @param type The new Content-type
	 */
	public void setContentType(String type){
		this.setRequestProperty("Content-type", type);
	}
	
	/**
	 * Set the read timeout for this Http Client in milliseconds
	 * @param timeout The read timeout in milliseconds
	 */
	public void setReadTimeout(int timeout){
		if(timeout < 0) throw new IllegalArgumentException("Timeout cannot be negative!");
		_readTimeout = timeout;
	}
	
	/**
	 * Set a Http Request Property (Header Property) for this client
	 * 
	 * @param key The key name of the property
	 * @param value The value of the property
	 */
	public void setRequestProperty(String key, String value){
		_connectionProperties.put(key, value);
	}
	
	/**
	 * Perform an HTTP Trace request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server
	 * @return The Http Response from the Server
	 */
	public Response trace(URL baseUrl, byte[] data){
		return httpRequest(baseUrl, HttpVerb.TRACE.toString(), data);
	}
	
	/**
	 * Perform an HTTP Trace request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response trace(URL baseURL, String... data){
		if(data.length == 0){
			return trace(baseURL, new byte[0]);
		}else{
			try{
				return trace(baseURL, NameValueSet.fromStringArray(data));
			}catch(Exception e){
				return new ExceptionResponse(e.getMessage());
			}
		}
	}
	
	/**
	 * Perform an HTTP Trace request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param nvs The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response trace(URL baseURL, NameValueSet nvs){
		return trace(baseURL, nvs.toString().getBytes());
	}
	
	/**
	 * Perform an HTTP Trace request
	 * 
	 * @param url The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response trace(String url, byte[] data){
		try {
			return trace(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Trace request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param data The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response trace(String url, String... data){
		try {
			return trace(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	/**
	 * Perform an HTTP Trace request
	 * 
	 * @param baseUrl The URL to connect to
	 * @param nvs The data to write to the server formatted as Name Value pairs
	 * @return The Http Response from the Server
	 */
	public Response trace(String url, NameValueSet nvs){
		try {
			return trace(new URL(url), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
}
