package polybellum.HTTPUtils;

public class Test {
	public static void main(String[] args){
		WebClient c = new SimpleWebClient();
		c.setContentType("text/plain");
		//TODO FIX USER AGENT TO NOT HAVE JAVA
		Response hr = c.post("http://posttestserver.com/post.php", "This is a cool test".getBytes()); //TODO use https
		//Response hr = c.get("http://httpbin.org/get"); //TODO use https
		System.out.println(hr.getResponse());
		System.out.println(hr.getDataString("UTF-8"));
	}
}
