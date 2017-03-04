# polybellum HTTP Library  
  
Written by Nic Wilson (github.com/mtear)  

A simple Http Client for working with Requests, Responses, and REST APIs using Http Verbs.  
  
Licensed with the Apache License-- Feel free to use and modify!  
  
## Jitpack  
Use this library with jitpack! Add jitpack support to your Gradle file.  

```
repositories {
        jcenter()
        maven { url "https://jitpack.io" }
   }
   dependencies {
         compile 'com.github.polybellum:HTTP:-SNAPSHOT'
   }
```  
  
  
## Example Usage:  

```
HttpClient client = new SimpleHttpClient();
Response r = client.get("https://www.google.com");
System.out.println(r.getResponse());
System.out.println(r.getDataString());
```  
  
You can use the Http verbs GET, POST, HEAD, PUT, OPTIONS, DELETE, and TRACE all with their own functions with various overloads.  
If there is an error the methods will instead return an ```ExceptionResponse``` with response code -1 so be sure to check the validity of your responses.  