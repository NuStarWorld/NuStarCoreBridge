package top.nustar.nustarcorebridge.sender;

import org.bukkit.entity.Player;

public class BukkitSender extends AbstractSender<Player> {
    public BukkitSender(Player sender) {
        super(sender);
    }

    @Override
    public void sendMessage(String message) {
        this.sender.sendMessage(message);
    }
}
