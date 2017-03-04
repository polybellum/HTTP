package polybellum.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HTTPUtil {
	private final static int BYTE_CHUNK = 4096;
	
	public static byte[] readInputStreamBytes(InputStream inputStream) throws IOException{
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  byte[] byteChunk = new byte[BYTE_CHUNK];
		  int n;

		  while ( (n = inputStream.read(byteChunk)) > 0 ) {
		    baos.write(byteChunk, 0, n);
		  }
		  
		  return baos.toByteArray();
	}
	
	public static URL appendQueryStringToUrl(URL baseUrl, String... parameters) throws Exception{
		if(parameters.length == 0){
			return baseUrl;
		}
		
		NameValueSet nvs = NameValueSet.fromStringArray(parameters);
		
		return appendQueryStringToUrl(baseUrl, nvs);
	}
	
	public static URL appendQueryStringToUrl(URL baseUrl, NameValueSet nvs) throws Exception{
		if(nvs.size() == 0){
			return baseUrl;
		}
		
		return new URL(baseUrl.toExternalForm() + nvs.toQueryString());
	}
}
