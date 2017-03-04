package polybellum.HTTP;

public class HTTPResponse extends Response{
	
	public HTTPResponse(int response, byte[] data){
		setResponse(response);
		setDataByteArray(data);
	}
	
}
