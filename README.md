# 核心桥
此插件用于抽象出一套接口可以适配龙核和萌芽的大部分功能，包括但不限于变量设定和发包处理

### 使用方法
继承`PacketProcessor`接口后添加注解
```java
@PacketName("DefaultPacket")
public class DefaultPacketProcessor implements PacketProcessor {

    @PacketHandler("sendMessage")
    public void runPacket(@PacketArgument("sender") Player sender, @PacketArgument("name")String name, @PacketArgument("message")String message) {
        System.out.printf("DefaultPacketProcessor.runPacket(%s,%s, %s)\n",sender, name, message);
    }

}
```
获得`PacketEventBus`实例后将类注册进去即可.`NuStarCoreBridge`会自动监听龙核和萌芽的发包事件进行处理
```java
PacketEventBus.instance().registerPacket(DefaultPacketProcessor.class);
```

### 发包参数格式
|发包名称|方法|参数|说明|
|:----|:----|:----|:----|
|DefaultPacket|sendMessage|name={arg},message={arg}|一个测试发包|    

龙核: 方法.发包('DefaultPacket', 'sendMessage', 'name=测试名字', 'message=测试消息')    
萌芽: DefaultPacket<->sendMessage name=测试名字 message=测试消息    

参数没有固定顺序，不支持重载方法。
