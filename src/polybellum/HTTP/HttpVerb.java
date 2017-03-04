package polybellum.HTTP;

public enum HttpVerb {
	GET("GET"),
	POST("POST"),
	HEAD("HEAD"),
	OPTIONS("OPTIONS"),
	PUT("PUT"),
	DELETE("DELETE"),
	TRACE("TRACE");
	
	private String _verb;
	HttpVerb(String verb){
		_verb = verb;
	}
	
	public String toString(){
		return _verb;
	}
}
