package polybellum.HTTP;

public class Test {
	public static void main(String[] args){
		HttpClient c = new SimpleHttpClient();
		//Response hr = c.post("http://posttestserver.com/post.php", "This is a cool test".getBytes());
		Response hr = c.get("https://www.wired.com/wp-content/uploads/2015/11/zelda.jpg");
		System.out.println(hr.getResponse());
		System.out.println(hr.getDataString("UTF-8"));
	}
}
