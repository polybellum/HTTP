// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>
	
package polybellum.HTTP;

/**
 * A Simple Http Client for easy Http requests and responses
 * 
 * @author Nic Wilson (mtear)
 *
 */
public class SimpleHttpClient extends HttpClient {
	
/*__///////////////////////////////////////////////////////////////////////////////////////////////
____///////////////////////////////// CONSTRUCTORS ////////////////////////////////////////////////
____/////////////////////////////////////////////////////////////////////////////////////////////*/

	/**
	 * Initialize the Http Client with some simple default parameters.<br>
	 * A modern browser User Agent String.<br>
	 * A connect timeout of 8 seconds.<br>
	 * A read timeout of 60 seconds.<br>
	 * The Content-type property set to "text/plain"
	 */
	public SimpleHttpClient(){
		setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; "
				+ "en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		this.setConnectTimeout(8000);
		this.setReadTimeout(60000);
		setContentType("text/plain");
	}
	
}
