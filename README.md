# NuStar核心桥 —— 各大引擎之间的桥梁
**使用本插件开发附属默认你已经会使用`Next`框架，这里不做过多赘述。**

### 在开发前必须要做的第一步
在插件主类添加`@EnableNuStarCoreBridge`注解 否则获取不到服务，也注入不了附属的任何组件
```java
@EnableNuStarCoreBridge
public class NuStarParty extends SpigotPlugin {
    // .....省略一堆代码
}
```

### 自定义发包处理器
一段简单的示例代码
```java
@Component
@PacketName("DefaultPacket")
@DependsOn(classes = "top.nustar.nustarcorebridge.NuStarCoreBridge")
public class DefaultPacketProcessor implements PacketProcessor {

    /**
     * 所有使用@PacketHandler的方法，都需要遵守一个唯一约定——第一个参数是PacketSender
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
像这样就创建一个发包处理器完毕了，在这个类下写的方法都归属于`DefaultPacket`这个名字的发包。你无需显式的向某个地方注册这个类，`Next`框架会自己扫描注册。

### 发包参数格式
| 发包名称          | 方法          | 参数                       | 说明     |
|:--------------|:------------|:-------------------------|:-------|
| DefaultPacket | sendMessage | name={arg},message={arg} | 一个测试发包 |    

龙核: 方法.发包('DefaultPacket', 'sendMessage', 'name=测试名字', 'message=测试消息')    
萌芽: DefaultPacket<->sendMessage name=测试名字 message=测试消息  
ArcartX(未测试): Packet.send("DefaultPacket", "sendMessage", "name=测试名字", "message=测试消息")  
云拾: `由于作者没有购买云拾也没使用过，因此不知晓云拾的发包格式`

参数没有固定顺序，不支持重载方法。

### 服务调用
```java
@Component
@PacketName("DefaultPacket")
@DependsOn(classes = "top.nustar.nustarcorebridge.NuStarCoreBridge")
public class DefaultPacketProcessor implements PacketProcessor {
    private volatile SlotService slotService;

    /**
     * 使用@Autowired注解能让你无需显式的使用new关键字或者类如getInstance()来获取服务
     * Next框架会自动将实现了该接口的类注入到字段，如果同时安装了多个引擎，那就看谁抢的快了 :(
     * @param slotService 槽位服务
     */
    @Autowired
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }
    
    @PacketHandler(value = "loadItem", description = "向客户端发送一个假物品")
    public void loadItem(PacketSender<Player> packetSender) {
        Player sender = packetSender.getSender();
        slotService.putSlotItem(sender, "测试槽位", new ItemStack(Material.STONE, 1));
    }
}
```
