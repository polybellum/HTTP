package polybellum.HTTPUtils;

public class Test {
	public static void main(String[] args){
		WebClient c = new SimpleWebClient();
		//FIX USER AGENT TO NOT HAVE JAVA
		HTTPResponse hr = c.get("http://www.google.com");
		System.out.println(hr.getResponse());
		System.out.println(hr.getDataString("UTF-8"));
	}
}
