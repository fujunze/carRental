##API

###租车

#####调用路径

```
/car/booking
```

#####调用方式

```
POST/GET
```

#####参数

参数 |说明
------------- | -------------
mobile|手机号
type|车型号。1：Toyota Camry；2：BMW 650
day|租期（天）

#####响应

```
正常
{
	"CODE":"00",
	"MSG":"租车成功"
}

异常
{
	"CODE":"01",
	"MSG":"无效用户"
}

{
	"CODE":"01",
	"MSG":"此车型已租完了，请选择其他车型"
}

{
	"CODE":"01",
	"MSG":"余额不足"
}

{
	"CODE":"01",
	"MSG":"租车失败"
}
```

###还车

#####调用路径

```
/car/recycle
```

#####调用方式

```
POST/GET
```

#####参数

参数 |说明
------------- | -------------
mobile|手机号
type|车型号。1：Toyota Camry；2：BMW 650
id|租车日志id。可通过接口/car/query查询

#####响应

```
正常
{
	"CODE":"00",
	"MSG":"还车成功"
}

异常
{
	"CODE":"01",
	"MSG":"没有可还的车"
}
```

###查询租车

#####调用路径

```
/car/query
```

#####调用方式

```
POST/GET
```

#####参数

参数 |说明
------------- | -------------
mobile|手机号

#####响应

```
正常
{
	"DATA":[
		{
			"car":"Toyota Camry","id":2
		},{
			"car":"Toyota Camry","id":3
		},{
			"car":"BMW 650","id":4
		}],
	"CODE":"00"
}

{
	"DATA":[],
	"CODE":"00"
}
```

##DB设计

###用户表

字段|类型|可空|描述
---- | ---- | --- | -----
id|bigint|否|主键
user_name|varchar(100)|否|姓名
mobile|varchar(20)|否|手机号
account_balance|decimal|否|账户余额

###汽车库存表

字段|类型|可空|描述
---- | ---- | --- | -----
id|bigint|否|主键
car_model|varchar(1)|否|车型编码
car\_model\_name|varchar(20)|否|车型描述
in_stock|int|否|库存
one\_day\_cost|decimal|否|租一天的价格
update_date|timestamp|否|更新时间

###租车记录表

字段|类型|可空|描述
---- | ---- | --- | -----
id|bigint|否|主键
mobile|varchar(20)|否|手机号
car_model|varchar(1)|否|车型编码
create_date|timestamp|否|创建时间
return_date| timestamp|是|归还时间
duration|int|否|租车天数


##TEST CASES

###租车接口

输入参数|输出|结果
---- | ---- | ----
mobile=13800138444&type=1&day=1|无效用户|pass
mobile=13800138000&type=1&day=1|此车型已租完了，请选择其他车型| pass
mobile=13800138111&type=1&day=1|余额不足| pass
mobile=13800138000&type=2&day=1|租车成功| pass

###还车接口

输入参数|输出|结果
---- | ---- | ----
mobile=13800138444&type=1&id=1|没有可还的车|pass
mobile=13800138000&type=1&id=2|还车成功|pass

###查询租车接口

输入参数|输出|结果
---- | ---- | ----
mobile= 13800138000|{"DATA":[{"car":"Toyota Camry","id":2},{"car":"Toyota Camry","id":3},{"car":"BMW 650","id":4}],"CODE":"00"}|pass
mobile=13800138222|空|pass
