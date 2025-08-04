# NuStar核心桥 —— 各大引擎之间的桥梁

### 插件优势
1. 跨平台发包
2. 跨平台服务调用
3. 人类友好的API，错误提示

### 常规用法
请查阅`NuStarCoreBridgeAPI`类下的接口方法

### 自定义发包处理器
一段简单的示例代码，更多内容请查阅`DefaultPacketProcessor`类下的示例代码
```java
@PacketName("DefaultPacket")
public class DefaultPacketProcessor implements PacketProcessor {

    /**
     * @param packetSender 发送者 这里指代Bukkit的玩家
     * @param name 参数名
     * @param message 参数名
     */
    @PacketHandler(value = "sendMessage", description = "向后台发送一条测试消息")
    public void sendMessage(
            PacketSender<Player> packetSender,
            @PacketArgument(value = "name", description = "测试名字") String name,
            @PacketArgument(value = "message", description = "测试消息") String message
    ) {
        System.out.printf("DefaultPacketProcessor.runPacket(%s,%s, %s)\n", packetSender, name, message);
    }

}
```
注册方法
```java
public class Plugin extends JavaPlugin {
    @Override
    public void onEnable() {
        NuStarCoreBridgeAPI.addPacketProcessors(DefaultPacketProcessor.class);
        // 支持链式调用
        NuStarCoreBridgeAPI
                .addPacketProcessors(Packet1.class)
                .addPacketProcessors(Packet2.class)
                .addPacketProcessors(Packet3.class);
    }
}
```
注册发包后，你不管使用龙核、萌芽、ArcartX还是云拾，你都能按照一定的格式发送数据包。从此告别手动监听发包事件处理数据。

### 发包参数格式
| 发包名称          | 方法          | 参数                       | 说明     |
|:--------------|:------------|:-------------------------|:-------|
| DefaultPacket | sendMessage | name={arg},message={arg} | 一个测试发包 |    

`龙核`: 方法.发包('DefaultPacket', 'sendMessage', 'name=测试名字', 'message=测试消息')    
`萌芽`: DefaultPacket<->sendMessage name=测试名字 message=测试消息  
`ArcartX(未测试)`: Packet.send("DefaultPacket", "sendMessage", "name=测试名字", "message=测试消息")  
`云拾`: Packet.sendData("DefaultPacket", "sendMessage", "name=测试名字", "message=测试消息")

参数没有固定顺序，不支持重载方法。

### 服务调用
```java
@PacketName("DefaultPacket")
public class DefaultPacketProcessor implements PacketProcessor {
    
    @PacketHandler(value = "loadItem", description = "向客户端发送一个假物品")
    public void loadItem(PacketSender<Player> packetSender) {
        Player sender = packetSender.getSender();
        NuStarCoreBridgeAPI.putSlotItem(sender, "测试槽位", new ItemStack(Material.STONE, 1));
    }
}
```
