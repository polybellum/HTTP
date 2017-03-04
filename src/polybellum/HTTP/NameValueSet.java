// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>
	
package polybellum.HTTP;

/*/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// IMPORT LIBRARIES //////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////*/

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A class representing a Set of Name/Value Pairs
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class NameValueSet {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// MEMBER VARIABLES //////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * A list of Name/Value Pairs
	 */
	private ArrayList<NameValuePair> _parameters = new ArrayList<NameValuePair>();
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PRIVATE METHODS ///////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Format this set of Name/Value pairs into a name=value& format with the specified starting
	 * string
	 * 
	 * @param startString The string to start with
	 * @return This set of pairs formatted as a String
	 */
	private String toParameterString(String startString){
		String returnValue = startString;
		try{
			for(NameValuePair nvp : _parameters){
				returnValue += String.format("%s=%s&",
						URLEncoder.encode(nvp.getName(), Charset.UTF_8.toString()),
						URLEncoder.encode(nvp.getValue(), Charset.UTF_8.toString()));
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		if(returnValue.length() == 0) return returnValue;
		return returnValue.substring(0, returnValue.length()-1);
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PUBLIC METHODS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Put a value into the Name Value Set
	 * 
	 * @param name A Name
	 * @param value A Value
	 */
	public void put(String name, String value){
		_parameters.add(new NameValuePair(name, value));
	}
	
	/**
	 * Get the size of this Name Value Set
	 * 
	 * @return How many parameters are set in the Name Value Set
	 */
	public int size(){
		return _parameters.size();
	}
	
	/**
	 * Return the Name Value pairs formatted as a Query String
	 * 
	 * @return The Name Value pairs formatted as a Query String
	 */
	public String toQueryString(){
		return toParameterString("?");
	}
	
	@Override
	public String toString(){
		return toParameterString("");
	}
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____//////////////////////////// PUBLIC STATIC METHODS ////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * Create a Name Value Set from a String array
	 * 
	 * @param parameters The names and values to put into the set
	 * @return A built Name Value Set from the parameters
	 * @throws Exception If there is an odd number of parameters an exception will be throwns
	 */
	public static NameValueSet fromStringArray(String... parameters) throws Exception{
		if(parameters.length % 2 == 1){
			throw new Exception("Must have an even number of parameters.");
		}
		
		NameValueSet nvs = new NameValueSet();
		String paramName = null;
		for(String s : parameters){
			if(paramName == null){
				paramName = s;
			}else{
				nvs.put(paramName, s);
				paramName = null;
			}
		}
		return nvs;
	}

/*__///////////////////////////////////////////////////////////////////////////////////////////////
____/////////////////////////////// PRIVATE CLASSES ///////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * A basic Name/Value Pair class
	 * 
	 * @author Nic Wilson (mtear)
	 *
	 */
	private class NameValuePair{
		
	/*__///////////////////////////////////////////////////////////////////////////////////////////
	____/////////////////////////// MEMBER VARIABLES //////////////////////////////////////////////
	____/////////////////////////////////////////////////////////////////////////////////////////*/

		/**
		 * The name and value parameters for this Name/Value pair
		 */
		private String _name, _value;
		
	/*__///////////////////////////////////////////////////////////////////////////////////////////
	____///////////////////////////// CONSTRUCTORS ////////////////////////////////////////////////
	____/////////////////////////////////////////////////////////////////////////////////////////*/
		
		/**
		 * Initialize the values for this Name/Value pair
		 * 
		 * @param name The Name value
		 * @param value The Value value
		 */
		public NameValuePair(String name, String value){
			_name = name;
			_value = value;
		}
		
	/*__///////////////////////////////////////////////////////////////////////////////////////////
	____/////////////////////////// PUBLIC METHODS ////////////////////////////////////////////////
	____/////////////////////////////////////////////////////////////////////////////////////////*/

		/**
		 * Getter for the Name value
		 * 
		 * @return The name
		 */
		public String getName(){
			return _name;
		}
		
		/**
		 * Getter for the Value value
		 * 
		 * @return The value
		 */
		public String getValue(){
			return _value;
		}
		
	}
	
}
