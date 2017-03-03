package polybellum.HTTPUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	public HTTPResponse get(URL url){
		//MAKE PREPARE METHOD
		
		HttpURLConnection connection = null;
		InputStream is = null;
		try {
		connection = (HttpURLConnection) url.openConnection();
		setConnectionProperties(connection);
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  is = url.openStream ();
		  byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
		  int n;

		  while ( (n = is.read(byteChunk)) > 0 ) {
		    baos.write(byteChunk, 0, n);
		  }
		  
		  return new HTTPResponse(code, baos.toByteArray());
		}
		catch (IOException e) {
		  System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
		  e.printStackTrace ();
		  // Perform any other exception handling that's appropriate.
		  
		}
		finally {
			if(connection != null) connection.disconnect();
		  if (is != null) { try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  }
		}
		
		return null;
		
	}
	
	public HTTPResponse get(String url){ //TODO: MAKE IT SO THAT WE CAN RETURN A RESPONSE WITH AN ERROR MESSAGE
		try {
			URL urlObject = new URL(url);
			return get(urlObject);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/*public HTTPResponse post(URL url, String data);
	public HTTPResponse post(String url, String data){
		URL urlObject = new URL(url);
		return Post(urlObject, data);
	}*/
	
	public void setReadTimeout(int timeout){
		if(timeout < 0) throw new IllegalArgumentException("Timeout cannot be negative!");
		_readTimeout = timeout;
	}
	
	public void setConnectTimeout(int timeout){
		if(timeout < 0) throw new IllegalArgumentException("Timeout cannot be negative!");
		_connectTimeout = timeout;
	}
	
	//MAKE REST FRAMEWORK
	//MAKE PIPELINE OBJECT FOR WEB REQUESTS
}
