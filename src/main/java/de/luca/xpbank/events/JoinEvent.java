package de.luca.xpbank.events;

import de.luca.xpbank.utils.XpDatabaseUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!XpDatabaseUtil.playerExists(p)) {
            XpDatabaseUtil.createPlayer(p);
        }
    }
}
