package polybellum.HTTPUtils;

public class Response {
	private int _response;
	private byte[] _data = null;
	
	public int getResponse(){
		return _response;
	}
	
	protected void setDataByteArray(byte[] data){
		_data = data;
	}
	
	public byte[] getDataByteArray(){
		return _data;
	}
	
	protected void setResponse(int response){
		_response = response;
	}
	
	public boolean hadException(){
		return this instanceof ExceptionResponse;
	}
	
	public String getDataString(){
		return new String(getDataByteArray()); //TODO MAKE THIS NULL SAFE AND STUFF
	}
	
	public String getDataString(String encoding){
		try{
			byte ptext[] = new String(getDataByteArray()).getBytes("ISO_8859_1"); 
			String value = new String(ptext, encoding); 
			return value;
		}catch(Exception e){
			return getDataString();
		}
	}
}
