package polybellum.HTTPUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HTTPUtilities {
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
}
