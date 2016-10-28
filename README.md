# Converter

>converter 是一个数据格式转换器，目前0.1版本只支持csv格式的文件转换为json格式的文件。
首先声明，网上有诸多格式转换工具，其中就包含csv转json，但他们并不提供可编程api，仍旧需要大伙自己实现。所以这是我设计这个工具的目标
同类产品如下网址：[www.csvtojson.com/](http://www.csvtojson.com/)

###快速使用：
converter支持容器方式运行和可编程api使用。

**1.容器方式**

* 首先准备一个csv格式的文件在你的任意目录，假设文件绝对路径是`/opt/flie/data.csv`.
* 用默认配置文件，进入`bin`目录，输入命令`chmod +x startup.sh`，现赋予权限，然后运行`./startup /opt/flie/data.csv`
* 输入命令 `cat /opt/dst/output.json` 即可看到转换完生成的json文件。
* 如果你的csv文件的分隔符不是`,`，则进入`conf`目录，就该配置文件，将`<key>separator</key><value>,</value>`中的`,`改成你自己的分隔符，然后执行上述步骤，即可完成转换。

**2.api调用**

* 新建工程，将converter.jar导入工程。新建一个类，输入如下代码：

```java
public static void main(String[] args) throws Exception {
    Configuration.getCnf().setString("separator",",");
    Configuration.getCnf().setString("mask","`");
    Configuration.getCnf().setString("output","/opt/dst/output.json");
    Processor process = new Processor("/opt/flie/data.csv");
    process.process(true).disk();
    System.out.println(process.string());
}
```

* 控制台会输出json数据。查看生成的文件 `cat /opt/dst/output.json` 也能够看到生成结果

###数据格式说明：
csv数据格式如下
```java
activity_id,client_id,content,type,agreed,cmted,shared,image,false_delete,created_at
25207601,5623365215,2016.9.27 11:14 测试日志文件,self,0,0,0,0,0,"2016-09-27 11:15:59"
72036199,5623365215,测试负载均衡反向代理,self,2,0,0,0,0,"2016-10-03 14:03:24"
65589038,5623365215,2016年10月8号，测试缓存,self,0,0,0,0,0,"2016-10-08 14:08:47"
```
可能会有这样的需求，将这样一段格式化的数据变成json,可能是交给数据平台的同学进行处理，也可能是数据平台的同学除了一个报表给我们，但是我们可能更需要一个json格式为我们使用。
这个需求可能是在程序运行过程中就需要完成的，所以需要我们自己封装，比较辛苦。converter提供了csv-->json的转换，可以作为外部工具用shell运行，或者将其引入工程调用api
**特别注意，csv格式的文件需要包括第一行的meta信息，否则需要进行额外配置**
经转换后得到的json如下：
```json
[
	{
		"activity_id":"25207601",
		"agreed":"0",
		"client_id":"5623365215",
		"cmted":"0",
		"content":"2016.9.27 11:14 测试日志文件",
		"created_at":"\"2016-09-27 11:15:59\"",
		"false_delete":"0",
		"image":"0",
		"shared":"0",
		"type":"self"
	},
	{
		"activity_id":"72036199",
		"agreed":"2",
		"client_id":"5623365215",
		"cmted":"0",
		"content":"测试负载均衡反向代理",
		"created_at":"\"2016-10-03 14:03:24\"",
		"false_delete":"0",
		"image":"0",
		"shared":"0",
		"type":"self"
	},
	{
		"activity_id":"65589038",
		"agreed":"0",
		"client_id":"5623365215",
		"cmted":"0",
		"content":"2016年10月8号，测试缓存",
		"created_at":"\"2016-10-08 14:08:47\"",
		"false_delete":"0",
		"image":"0",
		"shared":"0",
		"type":"self"
	}
]
```

###配置说明
配置文件如下：
```xml
<?xml version="1.0"?>
<converter>
    <property>
        <key>separator</key>
        <value>,</value>
    </property>
    <property>
        <key>mask</key>
        <value></value>
    </property>
    <property>
        <key>output</key>
        <value>/opt/dst/output.json</value>
    </property>
    <property>
        <key>keys</key>
        <props>
        </props>
    </property>
</converter>
```
* `separator`对应的值是csv文件的分隔符
* `mask` 对应的值是数据中你想过滤掉的字符或正则表达式
* `output` 对应的值是结果文件输出的路径