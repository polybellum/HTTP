package polybellum.HTTPUtils;

public class ExceptionResponse extends Response {
	public ExceptionResponse(String message){
		setResponse(-1);
		setDataByteArray(message.getBytes());
	}
}
