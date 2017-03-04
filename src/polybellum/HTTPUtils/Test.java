package polybellum.HTTPUtils;

public class Test {
	public static void main(String[] args){
		WebClient c = new SimpleWebClient();
		c.setContentType("text/plain");
		Response hr = c.post("http://posttestserver.com/post.php", "This is a cool test".getBytes());
		//Response hr = c.get("http://httpbin.org/get");
		System.out.println(hr.getResponse());
		System.out.println(hr.getDataString("UTF-8"));
	}
}
