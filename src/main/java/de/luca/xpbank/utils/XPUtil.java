package de.luca.xpbank.utils;

public class XPUtil {

    // Calculates Level to EXP
    public static int levelToExp(int level) {
        if (level == 0 || level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level >= 17 && level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        }
    }

    // Calculates EXP to Level
    public static int expToLevel(int level) {
        int val = levelToExp(level);
        if (level == 0 || level <= 16) {
            return (int) Math.sqrt(val + 9) - 3;
        } else if (level >= 17 && level <= 31) {
            return (int) (8.1 + Math.sqrt(0.4 * (levelToExp(level) - 195.975)));
        } else {
            return (int) (18.056 + Math.sqrt(0.222 * (val - 752.986)));
        }
    }

    // Calculates how much EXP needed for an level up
    public static int calculateEXPForLevelUp(int level) {
        if (level == 0 || level <= 15) {
            return 2 * level + 7;
        } else if (level >= 16 && level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    // Check if a player can upgrade his databaseLevel with his currentEXP/Level
    public static boolean canPlayerUpgrade(int playerLevel, int databaseLevel) {
        int currentEXP = levelToExp(playerLevel);
        int expForLevelUp = calculateEXPForLevelUp(databaseLevel);

        return currentEXP >= expForLevelUp;
    }

    // Calculate how much level a player can upgrade
    public static int maxLevelUpgrade(int playerLevel, int databaseLevel) {

        boolean done = false;
        int currentEXP = levelToExp(playerLevel);
        int databaseLevelTemp = databaseLevel;
        int possibleUpgrades = 0;
        int neededForUpgrade = XPUtil.calculateEXPForLevelUp(playerLevel);

        while (!done) {
            if (currentEXP >= neededForUpgrade) {
                neededForUpgrade = XPUtil.calculateEXPForLevelUp(databaseLevelTemp);
                currentEXP = currentEXP - neededForUpgrade;
                databaseLevelTemp++;
                possibleUpgrades++;
            } else {
                done = true;
            }
        }

        return possibleUpgrades;
    }
}
