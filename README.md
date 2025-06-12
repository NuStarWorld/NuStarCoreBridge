# <img src="logo.png" height="40px" alt="Logo" style="vertical-align: middle;">核心桥

<img src="logo.svg" height="140px" alt="logo" style="float: left;"/>
本插件受益于`Next`框架的依赖反转(Ioc)特性。   

可以帮助你快速创建包括但不限于DragonCore和GermPlugin的发包。   

并且该插件还包装了各种服务，方便你进行多平台开发。   

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
继承`PacketProcessor`接口后添加注解
```java
@Component
@PacketName("DefaultPacket")
public class DefaultPacketProcessor implements PacketProcessor {

    /**
     * 所有使用@PacketHandler的方法，都需要遵守一个唯一约定——第一个参数是PacketSender
     * @param packetSender 发送者 这里指代Bukkit的玩家
     * @param name 参数名
     * @param message 参数名
     */
    @PacketHandler("sendMessage")
    public void sendMessage(
            PacketSender<Player> packetSender,
            @PacketArgument("name") String name,
            @PacketArgument("message") String message
    ) {
        System.out.printf("DefaultPacketProcessor.runPacket(%s,%s, %s)\n", packetSender, name, message);
    }

}
```
做完以上步骤之后，`NuStarCoreBridge`会自动接管龙核，萌芽的发包事件并提交给不同的处理器处理

### 发包参数格式
| 发包名称          | 方法          | 参数                       | 说明     |
|:--------------|:------------|:-------------------------|:-------|
| DefaultPacket | sendMessage | name={arg},message={arg} | 一个测试发包 |    

龙核: 方法.发包('DefaultPacket', 'sendMessage', 'name=测试名字', 'message=测试消息')    
萌芽: DefaultPacket<->sendMessage name=测试名字 message=测试消息

参数没有固定顺序，不支持重载方法。

### 服务调用
```java
@Component
@PacketName("DefaultPacket")
public class DefaultPacketProcessor implements PacketProcessor {
    private volatile SlotService slotService;

    /**
     * 使用@Autowired注解能让你无需显式的使用new关键字或者类如getInstance()来获取服务
     * Ioc容器会自动将实现了该接口的类注入到字段
     * @param slotService 槽位服务
     */
    @Autowired
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }
    
    @PacketHandler("loadItem")
    public void loadItem(PacketSender<Player> packetSender) {
        Player sender = packetSender.getSender();
        slotService.putSlotItem(sender, "测试槽位", new ItemStack(Material.STONE, 1));
    }
}
```
除了自定义发包外，插件还包装了龙核，萌芽的一系列api，这里称它为`服务`