package de.luca.xpbank;

import de.luca.xpbank.commands.XpBankCommand;
import de.luca.xpbank.commands.XpInfoCommand;
import de.luca.xpbank.constants.DataConstants;
import de.luca.xpbank.events.GuiEvents;
import de.luca.xpbank.events.JoinEvent;
import de.luca.xpbank.utils.SqlInitializer;
import de.luca.xpbank.utils.SqlUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class Main extends JavaPlugin {

    private final HashMap<String, CommandExecutor> commandMap = new HashMap<>();
    private static final File databaseFile = new File("plugins\\XPBank", "sql.yml");
    private static final FileConfiguration databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);

    @Override
    public void onEnable() {
        try {
            initSqlDataFile();
            SqlUtil.connect();
            SqlInitializer.initTables();
            loadCommands();
            loadEvents();
            showEnableMessage();
            runResetTimer();
        } catch (Exception ex) {
            this.setEnabled(false);
            System.out.println("[XPBank] Die MySQL Daten sind nicht korrekt!");
        }
    }

    @Override
    public void onDisable() {
        SqlUtil.disconnect();
    }

    private void loadCommands() {
        commandMap.put("xpbank", new XpBankCommand());
        commandMap.put("xpinfo", new XpInfoCommand());

        for (Map.Entry<String, CommandExecutor> entry : commandMap.entrySet()) {
            Objects.requireNonNull(this.getCommand(entry.getKey())).setExecutor(entry.getValue());
        }
    }

    private void loadEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new GuiEvents(), this);
    }

    private void showEnableMessage() {
        System.out.println("« —————————————————————— »");
        System.out.println(" ");
        System.out.println("» XPBank wurde geladen!");
        System.out.println("» Entwickler: Luca Wesemann");
        System.out.println("» All rights reserved I guess");
        System.out.println(" ");
        System.out.println("« —————————————————————— »");
    }

    private void runResetTimer() {
        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        SqlUtil.update("UPDATE " + DataConstants.xpTable + " SET `levels_inpayed`='0' WHERE levels_inpayed != 0");
                    }
                },
                date.getTime(),
                1000 * 60 * 60 * 24
        );
    }

    private void initSqlDataFile() {
        if(!databaseFile.exists()) {
            saveResource("sql.yml", false);
        }
    }

    public static FileConfiguration getDatabaseConfig() {
        return databaseConfig;
    }
}
