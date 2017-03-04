package polybellum.HTTP;

public class HttpResponse extends Response{
	
	public HttpResponse(int response, byte[] data){
		setResponse(response);
		setDataByteArray(data);
	}
	
}
