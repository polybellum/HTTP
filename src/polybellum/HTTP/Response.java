package polybellum.HTTP;

/**
 * A class representing a generic Response from a source with a response code integer and
 * a byte array data value
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class Response {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// MEMBER VARIABLES //////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * A number representing a response code for this Response
	 */
	private int _response;
	
	/**
	 * A byte array of data for this Response
	 */
	private byte[] _data = null;

/*__///////////////////////////////////////////////////////////////////////////////////////////////
____////////////////////////////// PROTECTED METHODS //////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Set the data byte array for this Response
	 * 
	 * @param data The byte array to set as data for this Response
	 */
	protected void setDataByteArray(byte[] data){
		_data = data;
	}
	
	/**
	 * Set the integer response code for this Response
	 * @param response The new Response code
	 */
	protected void setResponse(int response){
		_response = response;
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PUBLIC METHODS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Getter for the data byte array
	 * @return The data byte array set for this Response
	 */
	public byte[] getDataByteArray(){
		return _data;
	}
	
	/**
	 * Get the data byte array for this Response as a generic String
	 * @return A generic String made from the data byte array
	 */
	public String getDataString(){
		if(_data == null) return "";
		return new String(getDataByteArray());
	}
	
	/**
	 * Get the String representation of the data byte array using the specified encoding
	 * @param encoding The String encoding to use to encode the data byte array
	 * @return The data byte array encoded using the specified encoding
	 */
	public String getDataString(String encoding){
		try{
			//Get a plain text (ISO) array of bytes from the data
			byte ptext[] = new String(getDataByteArray()).getBytes("ISO_8859_1"); 
			//Encode the bytes with the given encoding
			String value = new String(ptext, encoding); 
			return value;
		}catch(Exception e){
			//Return the original bytes as a generic String on error
			return getDataString();
		}
	}
	
	/**
	 * Get the integer response code from this Response
	 * @return The integer response code from this Response
	 */
	public int getResponse(){
		return _response;
	}
	
	/**
	 * Whether or not the Request that generated this Response had an Exception
	 * @return If this object is an instance of ExceptionResponse
	 */
	public boolean hadException(){
		return this instanceof ExceptionResponse;
	}
	
}
