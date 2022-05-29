package de.luca.xpbank.utils;

import de.luca.xpbank.constants.DataConstants;

public class SqlInitializer {

    public static void initTables() {
        try {
            SqlUtil.update("CREATE TABLE IF NOT EXISTS " + DataConstants.xpTable + " (uuid TEXT, level INTEGER, levels_inpayed INTEGER)");
        } catch (Exception e) {
            System.out.println("SQL Init Fehler: " + e.getMessage());
        }
    }
}
