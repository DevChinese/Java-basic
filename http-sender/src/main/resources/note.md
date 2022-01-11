## 一般使用步骤
### HttpGet请求响应的一般步骤：
1). 创建HttpClient对象,可以使用HttpClients.createDefault() 或者 HttpClientBuilder.create().build();；
2). 如果是无参数的GET请求，则直接使用构造方法HttpGet(String url)创建HttpGet对象即可；
如果是带参数GET请求，则可以先使用URIBuilder(String url)创建对象，再调用addParameter(String param, String value)，或setParameter(String param, String value)来设置请求参数，并调用build()方法构建一个URI对象。只有构造方法HttpGet(URI uri)来创建HttpGet对象。
3). 创建HttpResponse，调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。通过调用getStatusLine().getStatusCode()可以获取响应状态码。
4). 释放连接。

### HttpPost请求响应的一般步骤：
1). 创建HttpClient对象,可以使用HttpClients.createDefault()；
2). 如果是无参数的GET请求，则直接使用构造方法HttpPost(String url)创建HttpPost对象即可；
如果是带参数POST请求，先构建HttpEntity对象并设置请求参数，然后调用setEntity(HttpEntity entity)创建HttpPost对象。
3). 创建HttpResponse，调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。通过调用getStatusLine().getStatusCode()可以获取响应状态码。
4). 释放连接

## 参考链接
https://segmentfault.com/a/1190000038939795



