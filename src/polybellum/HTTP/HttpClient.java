package polybellum.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class HttpClient {

	private HashMap<String, String> _connectionProperties;
	private int _connectTimeout = 0;
	private int _readTimeout = 0;
	
	public HttpClient(){
		_connectionProperties = new HashMap<String, String>();
	}
	
	public void setRequestProperty(String key, String value){
		_connectionProperties.put(key, value);
	}
	
	private void setConnectionProperties(URLConnection connection){
		for(String key : _connectionProperties.keySet()){
			connection.setRequestProperty(key, _connectionProperties.get(key));
		}
		connection.setConnectTimeout(_connectTimeout);
		connection.setReadTimeout(_readTimeout);
	}
	
	public Response get(URL baseUrl, String... queryParameters){
		try {
			URL url = HttpUtil.appendQueryStringToUrl(baseUrl, queryParameters);
			return httpRequest(url, HttpVerb.GET.toString(), null);
		} catch (Exception e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response get(URL baseUrl, NameValueSet nvs){
		try {
			URL url = HttpUtil.appendQueryStringToUrl(baseUrl, nvs);
			return httpRequest(url, HttpVerb.GET.toString(), null);
		} catch (Exception e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response get(String baseUrl, String... parameters){
		try {
			return get(new URL(baseUrl), parameters);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response get(String baseUrl, NameValueSet nvs){
		try {
			return get(new URL(baseUrl), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response post(URL baseUrl, byte[] data){
		return httpRequest(baseUrl, HttpVerb.POST.toString(), data);
	}
	
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
	
	public Response post(URL baseURL, NameValueSet nvs){
		return post(baseURL, nvs.toString().getBytes());
	}
	
	public Response post(String url, byte[] data){
		try {
			return post(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response post(String url, String... data){
		try {
			return post(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response post(String url, NameValueSet nvs){
		try {
			return post(new URL(url), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response put(String url, byte[] data){
		try {
			return put(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response put(URL url, byte[] data){
		return httpRequest(url, HttpVerb.PUT.toString(), data);
	}
	
	public Response put(URL url, String data){
		return put(url, data.getBytes());
	}
	
	public Response put(String url, String data){
		try {
			return put(new URL(url), data.getBytes());
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response put(String url, ContentType contentType, byte[] data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	public Response put(URL url, ContentType contentType, byte[] data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	public Response put(URL url, ContentType contentType, String data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	public Response put(String url, ContentType contentType, String data){
		setContentType(contentType.getValue());
		return put(url, data);
	}
	
	public Response delete(String url){
		try {
			return delete(new URL(url));
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response delete(URL url){
		return httpRequest(url, HttpVerb.DELETE.toString(), null);
	}
	
	public Response head(String url){
		try {
			return head(new URL(url));
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response head(URL url){
		return httpRequest(url, HttpVerb.HEAD.toString(), null);
	}
	
	public Response options(URL baseUrl, byte[] data){
		return httpRequest(baseUrl, HttpVerb.OPTIONS.toString(), data);
	}
	
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
	
	public Response options(URL baseURL, NameValueSet nvs){
		return options(baseURL, nvs.toString().getBytes());
	}
	
	public Response options(String url, byte[] data){
		try {
			return options(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response options(String url, String... data){
		try {
			return options(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response options(String url, NameValueSet nvs){
		try {
			return options(new URL(url), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response trace(URL baseUrl, byte[] data){
		return httpRequest(baseUrl, HttpVerb.TRACE.toString(), data);
	}
	
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
	
	public Response trace(URL baseURL, NameValueSet nvs){
		return trace(baseURL, nvs.toString().getBytes());
	}
	
	public Response trace(String url, byte[] data){
		try {
			return trace(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response trace(String url, String... data){
		try {
			return trace(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	public Response trace(String url, NameValueSet nvs){
		try {
			return trace(new URL(url), nvs);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	private Response httpRequest(URL url, String method, byte[] outputData){
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {
			if(outputData != null){	
				setRequestProperty("Content-Length", String.valueOf(outputData.length));
			}
			connection = prepareConnection(url, method, outputData != null);
			if(outputData != null){
				OutputStream wr = connection.getOutputStream();
				wr.write(outputData);
				wr.close();
			}
			inputStream = connection.getInputStream();
			return new HttpResponse(connection.getResponseCode(), HttpUtil.readInputStreamBytes(inputStream));
		}
		catch (IOException e) {
			return new ExceptionResponse(e.getMessage()); 
		}
		finally {
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
	
	private HttpURLConnection prepareConnection(URL url, String method, boolean doOutput) throws IOException{
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		setConnectionProperties(connection);
		connection.setRequestMethod(method);
		connection.setDoOutput(doOutput);
		return connection;
	}
	
	public void setReadTimeout(int timeout){
		if(timeout < 0) throw new IllegalArgumentException("Timeout cannot be negative!");
		_readTimeout = timeout;
	}
	
	public void setConnectTimeout(int timeout){
		if(timeout < 0) throw new IllegalArgumentException("Timeout cannot be negative!");
		_connectTimeout = timeout;
	}
	
	public void setContentType(String type){
		this.setRequestProperty("Content-type", type);
	}
	
	public Response execute(Request request){
		if(!request.isValid()){
			return new ExceptionResponse("The request specified is invalid: " + request.getErrorMessage());
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
	
}
