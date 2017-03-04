package polybellum.HTTP;

public enum HTTPVerbs {
	GET("GET"),
	POST("POST"),
	HEAD("HEAD"),
	OPTIONS("OPTIONS"),
	PUT("PUT"),
	DELETE("DELETE"),
	TRACE("TRACE");
	
	private String _verb;
	HTTPVerbs(String verb){
		_verb = verb;
	}
	
	public String toString(){
		return _verb;
	}
}
