JAVA 爬虫框架组成
---
```
爬虫框架是由以下三个第三方jar包封装而成的
1.OkHttp3   负责网络请求
2.jsoup     负责解析HTML
3.fastjson  负责解析json
```

概念介绍
---
#Agent
```
HTTP的 User-Agent，通常用于伪装浏览器信息
框架实现DefaultUserAgent类，通过随机方式
选取配置类的User-Agent。提供UserAgent接口
实现向下扩展能力。详情参考包com.crawler.framework.agent
```

#RequestUrl
```
RequestUrl为HTTP url对象，详情方法请参考基类RequestUrl。若需要扩展可实现
RequestUrl接口或继承AbstractGetRequestUrl，AbstractPostRequestUrl抽象类。
当前框架实现的RequestUrl对象有

GetRequestUrl
用法：
String url = https://[site]/gp/product-reviews/[asin]/ref=cm_cr_arp_d_viewopt_sr?ie=UTF8&showViewpoints=1&filterByStar=all_stars&pageSize=[size]&reviewerType=all_reviews&sortBy=recent
GetRequestUrl getRequestUrl = GetRequestUrl.build(url, "pageNumber")
                    .addParam("pageNumber", 1)
                    .replaceUrlPrefix("[site]", "www.amazon.com")
                    .replaceUrlPrefix("[asin]", "JHH11107");
其中url为爬虫请求地址，pageNumber为请求页数参数，1为页数。构造函数提供URL和页数形参。 
addParam方法为键值对，添加URL参数，添加后会自动将参数名和参数值拼接到url上
replaceUrlPrefix方法为替换url参数上的占位符。


PostRequestUrl
用法：url
String url = "https://[site]/sp/ajax/feedback";
PostRequestUrl postRequestUrl = PostRequestUrl.build(url, "pageNumber", 5)
                    .replaceUrlPrefix("[site]", "www.amazon.com")
                    .addParam("seller", amazonAuth.getSellerId())
                    .addParam("marketplaceID", "XJHH516516");
                    
其中， GetRequestUrl，PostRequestUrl都有多个重载的build方法                 
```

#Request
```
HTTP的请求对象，位于com.crawler.framework.request
包下。提供 RequestManager抽象类（请求管理器），请求
管理器主要责任是创建OkHttp3的Request对象。它包含了
RequestUrl对象。功能有根据RequestUrl对象设定的URL
和其请求参数构建当前请求URL,以及构建下一页请求URL的
Request对象。

已实现的请求管理器有
GetRequestManager       用于get请求
JsonPostRequestManager  用于post请求
FormPostRequestManager  用于post的表单请求

用法：
RequestManager requestManager = new GetRequestManager(GetRequestUrl, UserAgent);
RequestManager requestManager = new FormPostRequestManager(PostRequestUrl, UserAgent);   
RequestManager requestManager = new JsonPostRequestManager(PostRequestUrl, UserAgent);     
``` 

#Proxy

IP代理管理器
---
```
ProxyManager是代理管理器的基础接口。IP
代理的获取必须实现此接口。此框架默认实现
两种IP代理的获取
FreeProxy 免费代理（快代理）
FastIpProxyManager 快代理（收费版）

使用方法：
FreeProxy freeProxy = new FreeProxy();
Set<Proxy> proxyList = freeProxy.getProxy();

FastIpProxyManager fastIpProxyManager = new FastIpProxyManager(orderId);
Set<Proxy> proxyList = freeProxy.getProxy();
其中orderId为收费版快代理给的token
```

IP代理选择器
---
```
BasisProxySelector是IP代理选择器的基类，OkHttp使用它的子类作为
IP代理路由。其中

List<Proxy> select(URI uri)返回一个最适合访问该URI的代理服务器列表，
其中会首选列表的第一个代理，如果不行则用第二个

连接代理服务器失败时的回调，当回调此方法说明当前爬虫已停止
connectFailed(URI uri, SocketAddress sa, IOException ioe)
详情请参考BasisProxySelector

此框架实现了两种代理选择器：
BlockingQueueProxySelector  基于阻塞队列的选择器
RedisProxySelector          基于Redis的选择器

用法：
FreeProxy freeProxy = new FreeProxy();
Set<Proxy> proxySet = freeProxy.getProxy();
BasisProxySelector proxySelector = new BlockingQueueProxySelector(proxySet);


Redis代理选择器使用比较繁琐.
1.需要在Application类上加上注解
@EnableRedis启用此框架默认的redis配置，参考RedisConf类。

2.yml文件添加Redis配置
spring:
  redis:
      database: 0   # Redis数据库索引（默认为0）
      host: localhost  # Redis服务器地址
      port: 6379  # Redis服务器连接端口
      password:    # Redis服务器连接密码（默认为空）
      timeout: 5000  # 连接超时时间（毫秒）
      
3.
@Autowired
private RedisTemplate<String, Object> redisTemplate;
...
FreeProxy freeProxy = new FreeProxy();
Set<Proxy> proxySet = freeProxy.getProxy();
BasisProxySelector selector = new RedisProxySelector(redisTemplate, proxySet,null);

需要更新IP代理选择器里的IP代理池可调用BasisProxySelector的updateProxies方法

建议代理选择器实例交由spring管理
```

#Spider（核心）
Spider
---
```
Spider是爬虫的基类，想要编写爬虫必须继承Spider
Spider属性介绍

allowedDomains：允许爬虫爬取的域名
dict：字典，用于自定义数据在爬虫组件中传递
itemPiplines：爬虫数据处理组件
requestManager：请求管理器
client：OkHttp请求客户端，强烈建议同一爬虫共享同一客户端，交由Spring管理

主要方法介绍
SpiderParse<T> parse(Response response, Call call, SpiderInfo<T> spiderInfo, SpiderEngine<T> spiderEngine)
HTTP请求成功回调方法。此方法用于解析HTTP响应结果
返回 SpiderParse 对象，如果SpiderParse对象里的 Request为空则说明爬虫爬取完毕，
不再继续往下爬取但会执行ItemPipeLine，如果返回null说明本次爬虫直接停止，不执行ItemPipeLine

reTry(Call call, IOException e)
爬虫失败重试回掉
```

SpiderEngine
---
```
爬虫的执行引擎，此类定义了爬虫的执行行为。
将构建好的爬虫交由爬虫引擎管理。
第一个爬虫的请求实际上是爬虫引擎的 submitRequest方法发起的。
在爬虫执行完解析方法parse后，爬虫引擎会执行一系列的ItemPipeline
来处理和持久化数据
详情参考SpiderEngine类
```

#ItemPipeline
```
爬虫处理数据组件,任何爬虫数据处理组件必须继承ItemPipline类。
爬虫执行多个ItemPipeline的顺序是根据加入List顺序执行的
主要方法：

前置通知，调用 processItem方法前调用
void beFore(SpiderInfo<T> spiderInfo)

爬虫数据处理方法
List<T> processItem(SpiderInfo<T> spiderInfo, List<T> itemList)

后置通知，调用processItem方法后调用
alter(SpiderInfo<T> spiderInfo)
```

client
---
```
OKHttp3的一个请求客户端。
强烈建议OKHttp3实例交由spring实例管理，同一爬虫（多个爬虫实例）共用同一客户端
```
#Demo 
```
参考本项目的test包下的crawler.spider.demo.baidu
京东搜索爬虫

参考项目（亚马逊爬取）
crawler-review
```
