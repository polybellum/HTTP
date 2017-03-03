package polybellum.HTTPUtils;

import java.nio.charset.Charset;

public class HTTPResponse {
	private int _response = 0;
	private byte[] _data = null;
	
	public HTTPResponse(int response, byte[] data){
		_response = response;
		_data = data;
	}
	
	public int getResponse(){
		return _response;
	}
	
	public byte[] getDataByteArray(){
		return _data;
	}
	
	public String getDataString(){
		return new String(_data); //MAKE THIS NULL SAFE AND STUFF
	}
	
	public String getDataString(String encoding){
		try{
			byte ptext[] = new String(_data).getBytes("ISO_8859_1"); 
			String value = new String(ptext, encoding); 
			return value;
		}catch(Exception e){
			return getDataString();
		}
	}
}
