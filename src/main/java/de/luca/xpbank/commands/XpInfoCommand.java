package de.luca.xpbank.commands;

import de.luca.xpbank.utils.PlayerUtil;
import de.luca.xpbank.utils.XPUtil;
import de.luca.xpbank.utils.XpDatabaseUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpInfoCommand implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
        PlayerUtil _util = new PlayerUtil();

        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (args.length == 0) {

                //Player data
                int currentLevel = p.getLevel();
                int currentEXP = XPUtil.levelToExp(currentLevel);
                int expForLevelUp = XPUtil.calculateEXPForLevelUp(currentLevel);

                //Database data
                int levelInDatabase = XpDatabaseUtil.getLevel(p);
                int expInDatabase = XPUtil.levelToExp(XpDatabaseUtil.getLevel(p));
                int expForLevelUpDatabase = XPUtil.calculateEXPForLevelUp(XpDatabaseUtil.getLevel(p));

                //Debug messages
                _util.sendMessage(p, "Aktuelles Level: §e" + currentLevel + "\nLevel in EXP: §e" + currentEXP + "\nEXP für ein LevelUp: §e" + expForLevelUp);
                p.sendMessage("");
                _util.sendMessage(p, "Stored Level: §e" + levelInDatabase + "\nStored Level in EXP: §e" + expInDatabase + "\nEXP für ein LevelUp (stored): §e" + expForLevelUpDatabase);

                if (XPUtil.canPlayerUpgrade(p.getLevel(), levelInDatabase)) {
                    _util.sendMessage(p, "Du kannst aktuell " + XPUtil.maxLevelUpgrade(p.getLevel(), levelInDatabase) + " Level hoch!");
                } else {
                    _util.sendMessage(p, "Du kannst nicht upgraden");
                }
            }
        } else {
            _util.noPlayer(cs);
        }
        return false;
    }
}
