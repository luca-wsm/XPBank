package de.luca.xpbank.utils;

import de.luca.xpbank.constants.PrefixConstants;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerUtil {

    public static String permissionPrefix = "xpbank."; // We have to keep the '.' because this is the permission suffix

    public void sendMessage(Player p, String msg) {
        String m = msg.replace("\n", "\n" + PrefixConstants.getPrefix());
        p.sendMessage(PrefixConstants.getPrefix() + m);
    }

    public void playSound(Player p, Sound s) {
        p.playSound(p.getLocation(), s, 1F, 1F);
    }

    public void sendCommandOverview(Player p, String message, String header) {
        p.sendMessage("");
        p.sendMessage(PrefixConstants.getH1() + " " + header + " " + PrefixConstants.getH2());
        p.sendMessage("");
        p.sendMessage("§8» §a" + message.replace("\n", "\n§8» §a"));
        p.sendMessage("");
        p.sendMessage(PrefixConstants.getH1() + " " + header + " " + PrefixConstants.getH2());
        p.sendMessage("");
    }

    public void noPlayer(CommandSender cs) { //Maybe move this method in the future
        cs.sendMessage(PrefixConstants.getPrefix() + "Du musst ein Spieler sein um diesen Command auszuführen!");
    }
}
