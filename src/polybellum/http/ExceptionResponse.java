// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>
	
package polybellum.http;

/**
 * A class representing a Response that had an error
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class ExceptionResponse extends Response {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____///////////////////////////////// CONSTRUCTORS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Constructor: Set the response to -1 and set the message to the given message
	 * @param message The message to show for this Response, typically an error message
	 */
	public ExceptionResponse(String message){
		setResponse(-1);
		setDataByteArray(message.getBytes());
	}
	
}
