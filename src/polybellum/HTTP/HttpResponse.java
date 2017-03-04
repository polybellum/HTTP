// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>
	
package polybellum.HTTP;

/**
 * A class representing a Http Response
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class HttpResponse extends Response{

/*__///////////////////////////////////////////////////////////////////////////////////////////////
____///////////////////////////////// CONSTRUCTORS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Set the response to the given code and set the data value to the given byte array
	 * @param response The response code for this Response
	 * @param data The data value for this Response
	 */
	public HttpResponse(int response, byte[] data){
		setResponse(response);
		setDataByteArray(data);
	}
	
}
