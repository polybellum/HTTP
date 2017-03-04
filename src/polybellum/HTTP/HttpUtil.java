// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>
	
package polybellum.HTTP;

/*/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// IMPORT LIBRARIES //////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////*/


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A Http utility class with static helper methods
 * 
 * @author Nic Wilson (mtear)
 *
 */
public abstract class HttpUtil {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____////////////////////////// PRIVATE STATIC VARIABLES ///////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	private final static int BYTE_CHUNK = 4096;
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____//////////////////////////// PUBLIC STATIC METHODS/////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Add a query string generated from a String array of names and values to the end of a URL
	 * 
	 * @param baseUrl The base URL to be appended
	 * @param parameters An array of name value pairs to create a query string from
	 * @return The new appended URL
	 * @throws Exception There may be an Exception formatting the name value pairs or making a 
	 * malformed URL.
	 */
	public static URL appendQueryStringToUrl(URL baseUrl, String... parameters) throws Exception{
		if(parameters.length == 0){
			return baseUrl;
		}
		
		NameValueSet nvs = NameValueSet.fromStringArray(parameters);
		
		return appendQueryStringToUrl(baseUrl, nvs);
	}
	
	/**
	 * Add a query string generated from a NameValueSet to the end of a URL
	 * 
	 * @param baseUrl The base URL to be appended
	 * @param nvs The name value set to add the query string formatting from
	 * @return The new appended URL
	 * @throws Exception There may be an Exception formatting the NameValueSet or making a 
	 * malformed URL.
	 */
	public static URL appendQueryStringToUrl(URL baseUrl, NameValueSet nvs) throws Exception{
		//Return the original URL if there are no parameters to append
		if(nvs.size() == 0){
			return baseUrl;
		}
		
		return new URL(baseUrl.toExternalForm() + nvs.toQueryString());
	}
	
	/**
	 * Read an InputStream as an array of bytes
	 * 
	 * @param inputStream The input stream to read from
	 * @return An array of bytes that the input stream returned
	 * @throws IOException There may be an error reading from the input stream
	 */
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
