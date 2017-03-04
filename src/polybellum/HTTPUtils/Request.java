package polybellum.HTTPUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {
	private String _verb = "GET";
	private URL _url = null;
	private byte[] _dataBytes = null;
	
	private Request(URL url, String verb, byte[] dataBytes){
		_verb = verb;
		_url = url;
		_dataBytes = dataBytes;
	}
	
	public String getVerb(){
		return _verb;
	}
	
	public URL getURL(){
		return _url;
	}
	
	public byte[] getDataBytes(){
		return _dataBytes;
	}
	
	public static Request build(URL url, String verb, byte[] dataBytes){
		return new Request(url, verb, dataBytes);
	}
	
	public static Request build(String url, String verb, byte[] dataBytes){
		try{
			return new Request(new URL(url), verb, dataBytes);
		}catch(MalformedURLException e){
			return null;
		}
	}
}
