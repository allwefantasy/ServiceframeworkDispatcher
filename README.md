## ServiceframeworkDispatcher

[ServiceFramework](https://github.com/allwefantasy/ServiceFramework) 框架的一个子项目。
实现Web的模块化开发，是一个强大的模块的引擎。


使用范例：

首先确保你的config目录或者classpath下(比如resource目录)有`strategy.v2.json`文件。你也可以通过
`application.strategy.config.file` 配置不同的文件名称


使用代码如下：

```
def dispatcher = findService(classOf[StrategyDispatcher[SearchResult]])
@At(
    path = Array("/_search"),
    types = Array(RestRequest.Method.POST, RestRequest.Method.GET)
  )
  def search = {
    val searchRList = dispatcher.dispatch(params())
    render(searchRList)
  }            
```      

其中 dispatcher 是 `serviceframework.dispatcher.StrategyDispatcher` 的实例，全局一个就行。
stragetyParams 是 一个 Map[String,String],里面有个必填的key,叫做\_client\_,该\_client\_代表 
调用哪个策略。

strategy.v2.json 配置文件如下：

```
{
    "query": {
        "desc": "",
        "strategy": "xxx.xxx.xxx.LinearStrategy",
        "processor": [
            {
                "name": "xxx.xxx.xxx.DataProcessor"
            }
        ],
        "ref": [],
        "compositor": [
            {
                "name": "xxx.xxx.xxx.compositor1"
            },
            {
                "name": "xxx.xxx.xxx.compositor2"
            }
        ],
        "configParams":{"key1":"",key2:""}
    }
}
```

## ServiceframeworkDispatcher 核心概念
 
* Strategy  策略，对应一个完整的请求。该策略决定 Compositor, Processor ,ref 是如何被组合调用的
* Compositor  组合器，一个数据处理链路
* Processor  协同器，是数据来源数据源
* ref  对其他的策略的引用


所有的模块都需要实现  Strategy /  Compositor / Processor 三个中的某个接口



