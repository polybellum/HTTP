package polybellum.HTTPUtils;

public class SimpleWebClient extends WebClient {
	public SimpleWebClient(){
		setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		this.setConnectTimeout(8000);
		this.setReadTimeout(60000);
		setContentType("text/plain");
	}
}
