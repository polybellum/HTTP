package polybellum.HTTPUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class QueryString {
	
	private ArrayList<NameValuePair> _parameters = new ArrayList<NameValuePair>();
	
	public void put(String name, String value){
		_parameters.add(new NameValuePair(name, value));
	}
	
	public String toQueryString(){
		String returnValue = "?";
		try{
			for(NameValuePair nvp : _parameters){
				returnValue += URLEncoder.encode(nvp.getName(), "UTF-8") //TODO use enum
						+ "=" + URLEncoder.encode(nvp.getValue(), "UTF-8") + "&";
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return returnValue.substring(0, returnValue.length()-1);
	}
	
	private class NameValuePair{
		private String _name, _value;
		public NameValuePair(String name, String value){
			_name = name;
			_value = value;
		}
		public String getName(){
			return _name;
		}
		public String getValue(){
			return _value;
		}
	}
	
}
