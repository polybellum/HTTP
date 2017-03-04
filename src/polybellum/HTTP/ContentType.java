package polybellum.HTTP;

public enum ContentType {
	JSON("application/json"),
	TEXT_PLAIN("text/plain");
	
	String _value = "";
	ContentType(String value){
		_value = value;
	}
	
	public String getValue(){
		return _value;
	}
	
}
