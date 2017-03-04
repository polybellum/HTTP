package polybellum.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NameValueSet {
	
	private ArrayList<NameValuePair> _parameters = new ArrayList<NameValuePair>();
	
	public void put(String name, String value){
		_parameters.add(new NameValuePair(name, value));
	}
	
	public String toQueryString(){
		return toParameterString("?");
	}
	
	@Override
	public String toString(){
		return toParameterString("");
	}
	
	private String toParameterString(String startString){
		String returnValue = startString;
		try{
			for(NameValuePair nvp : _parameters){
				returnValue += String.format("%s=%s&",
						URLEncoder.encode(nvp.getName(), Charsets.UTF_8),
						URLEncoder.encode(nvp.getValue(), Charsets.UTF_8));
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		if(returnValue.length() == 0) return returnValue;
		return returnValue.substring(0, returnValue.length()-1);
	}
	
	public int size(){
		return _parameters.size();
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
	
}
