package polybellum.HTTPUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class WebClient {

	private HashMap<String, String> _connectionProperties;
	private int _connectTimeout = 0;
	private int _readTimeout = 0;
	
	public WebClient(){
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
	
	public Response get(URL baseUrl, String... parameters){
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		
		try {
		
			//Append query string if values set TODO make function for this in a Util
			//TODO turn Querystring into a generic name value pairs object
			if(parameters.length > 0){
				//Check for even name values
				if(parameters.length % 2 == 1) return new ExceptionResponse("There must be an even number of parameters set for a query string for name and value pairs.");
				
				QueryString queryString = new QueryString();
				String paramName = null;
				for(String param : parameters){
					if(paramName == null){
						paramName = param;
					}else{
						queryString.put(paramName, param);
						paramName = null;
					}
				}
				
				baseUrl = new URL(baseUrl.toString() + queryString.toQueryString());
			}
		
			connection = prepareConnection(baseUrl, "GET"); //TODO make enum
			inputStream = connection.getInputStream();
			return new HTTPResponse(connection.getResponseCode(), readInputStreamBytes(inputStream));
		}
		catch (Exception e) {
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
	
	private byte[] readInputStreamBytes(InputStream inputStream) throws IOException{ //TODO put in helper util class
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  byte[] byteChunk = new byte[4096]; //TODO make constant
		  int n;

		  while ( (n = inputStream.read(byteChunk)) > 0 ) {
		    baos.write(byteChunk, 0, n);
		  }
		  
		  return baos.toByteArray();
	}
	
	public Response get(String baseUrl, String... parameters){
		try {
			return get(new URL(baseUrl), parameters);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	//TODO group these they're so similar (to get)
	//TODO make this accept parameter array data or block bytes of data
	//TODO make overloads to accept a parameter object instead of string array
	public Response post(URL url, String data){
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {
			byte[] dataBytes = data.getBytes();
			
			connection = prepareConnection(url, "POST", dataBytes.length); //TODO make enum
			OutputStream wr = connection.getOutputStream();
			wr.write(dataBytes);
			inputStream = connection.getInputStream();
			return new HTTPResponse(connection.getResponseCode(), readInputStreamBytes(inputStream));
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
	
	public Response post(String url, String data){
		try {
			return post(new URL(url), data);
		} catch (MalformedURLException e) {
			return new ExceptionResponse(e.getMessage());
		}
	}
	
	private HttpURLConnection prepareConnection(URL url, String method) throws IOException{
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		setConnectionProperties(connection);
		connection.setRequestMethod(method);
		connection.connect();
		return connection;
	}
	
	//TODO group these
	private HttpURLConnection prepareConnection(URL url, String method, int contentLength) throws IOException{
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		setRequestProperty("Content-Length", String.valueOf(contentLength));
		setConnectionProperties(connection);
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
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
	
}
