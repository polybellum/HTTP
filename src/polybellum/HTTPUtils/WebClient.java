package polybellum.HTTPUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public abstract class WebClient {

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
	public HTTPResponse Get(URL url){
		//MAKE PREPARE METHOD
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();
		
		connection.disconnect();
	}
	public HTTPResponse Get(String url){
		URL urlObject = new URL(url);
		return Get(url);
	}
	public HTTPResponse Post(URL url, String data);
	public HTTPResponse Post(String url, String data){
		URL urlObject = new URL(url);
		return Post(urlObject, data);
	}
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
