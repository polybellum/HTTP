package polybellum.HTTPUtils;

import java.net.URL;

public class Request {
	private String _verb = "GET";
	private URL _url;
	
	private Request(URL url, String verb){
		_verb = verb;
		_url = url;
	}
	
	public String getVerb(){
		return _verb;
	}
	
	public URL getURL(){
		return _url;
	}
	
	public static Request build(URL url, String verb){
		return new Request(url, verb);
	}
}
