package polybellum.HTTP;

public enum Charset {
	UTF_8("UTF-8");
	
	private String _value;
	Charset(String value){
		_value = value;
	}
	
	public String toString(){
		return _value;
	}
}
