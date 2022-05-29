package de.luca.xpbank.utils;

import de.luca.xpbank.constants.DataConstants;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class XpDatabaseUtil {

    /*
        May build statement in the future to prevent sql injections
        but in this case, its not necessary
     */

    public static void updateLevel(Player p, int newLevel) {
        String uuid = p.getUniqueId().toString();
        SqlUtil.update("UPDATE " + DataConstants.xpTable + " SET level =" + newLevel + " WHERE uuid='" + uuid + "'");
    }

    public static void updateLevelInpayed(Player p, int numb) {
        String uuid = p.getUniqueId().toString();
        SqlUtil.update("UPDATE " + DataConstants.xpTable + " SET levels_inpayed = " + (getLevelsInpayed(p) + numb) + " WHERE uuid='" + uuid + "'");
    }

    public static int getLevelsInpayed(Player p) {
        String value = "";
        try {
            ResultSet rs = SqlUtil.getResult("SELECT levels_inpayed FROM " + DataConstants.xpTable + " WHERE uuid = '" + p.getUniqueId() + "'");
            while (rs.next()) {
                value = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.valueOf(value);
    }

    public static int getLevel(Player p) {
        String value = "";
        try {
            ResultSet rs = SqlUtil.getResult("SELECT level FROM " + DataConstants.xpTable + " WHERE uuid = '" + p.getUniqueId() + "'");
            while (rs.next()) {
                value = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.valueOf(value);
    }

    public static void createPlayer(Player p) {
        String uuid = p.getUniqueId().toString();
        SqlUtil.update("INSERT INTO " + DataConstants.xpTable + " (uuid, level, levels_inpayed) VALUES('" + uuid + "', 0, 0)");
    }

    public static boolean playerExists(Player p) {
        try {
            return SqlUtil.getResult("SELECT * FROM `" + DataConstants.xpTable + "` WHERE uuid = '" + p.getUniqueId() + "';").isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
