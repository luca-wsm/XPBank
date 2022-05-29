package de.luca.xpbank.commands;

import de.luca.xpbank.constants.DataConstants;
import de.luca.xpbank.utils.GuiUtil;
import de.luca.xpbank.utils.PlayerUtil;
import de.luca.xpbank.utils.XPUtil;
import de.luca.xpbank.utils.XpDatabaseUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class XpBankCommand implements CommandExecutor {

    private static PlayerUtil _util;
    private final ArrayList<Integer> validNumbers = new ArrayList<>();

    /*
        Clear-Up invalid if nest
     */
    public static void payIn(Player p, int levels) {

        int newLevel = levels - (int) Math.round(levels * 0.20);
        int maxUpgrade = XPUtil.maxLevelUpgrade(newLevel, XpDatabaseUtil.getLevel(p));
        int levelsInpayed = XpDatabaseUtil.getLevelsInpayed(p);

            if(XpDatabaseUtil.getLevel(p) <= 175) { // Check if the general limit is reached ( we have to show another message to the player )

                if (p.getLevel() >= levels) { // Check if the player has enough level.

                    if (XpDatabaseUtil.getLevelsInpayed(p) < 25) { // Check if the daily limit is reached

                        if ((levelsInpayed + levels) <= 25) { // Second check if the daily limit is reached

                            if (levels <= 25) { // Check if a "all" inpay goes over the daily limit

                                if (XpDatabaseUtil.getLevel(p) + newLevel <= 175) { // Check if the inpay goes over the general inpay limit

                                    if (XPUtil.canPlayerUpgrade(levels, XpDatabaseUtil.getLevel(p))) { // Check if the EXP are enough for a level up
                                        if (XpDatabaseUtil.getLevel(p) != 0) { //
                                            _util.sendMessage(p, "Level Betrag abzüglich 20% Steuern: §6" + newLevel + " §2Level");
                                            _util.sendMessage(p, "Es werden auf Grund des EXP-Algorithmus §6" + maxUpgrade + " §2Level Upgrades durchgeführt!");
                                            XpDatabaseUtil.updateLevel(p, XpDatabaseUtil.getLevel(p) + maxUpgrade);
                                            XpDatabaseUtil.updateLevelInpayed(p, levels);
                                            _util.sendMessage(p, "Neuer Kontostand: " + XpDatabaseUtil.getLevel(p) + " Level!");
                                            _util.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                                            p.setLevel(p.getLevel() - levels);
                                        } else {
                                            _util.sendMessage(p, "Level Betrag abzüglich 20% Steuern: §6" + newLevel + " §2Level");
                                            XpDatabaseUtil.updateLevel(p, XpDatabaseUtil.getLevel(p) + maxUpgrade);
                                            XpDatabaseUtil.updateLevelInpayed(p, levels);
                                            _util.sendMessage(p, "Neuer Kontostand: §6" + XpDatabaseUtil.getLevel(p) + " §2Level!");
                                            _util.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                                            p.setLevel(p.getLevel() - levels);
                                        }
                                    } else {
                                        _util.sendMessage(p, "Deine Level reichen nicht für ein LevelUp.");
                                    }
                                } else {
                                    _util.sendMessage(p, "Mit dieser Einzahlung würdest du dein generelles Limit überschreiten. ( 175 Level )");
                                }
                            } else {
                                _util.sendMessage(p, "Mit dieser Einzahlung würdest du über dein Limit kommen. (25 Level)");
                            }
                        } else {
                            _util.sendMessage(p, "Mit dieser Einzahlung würdest du über dein Limit kommen. (25 Level)");
                        }
                    } else {
                        _util.sendMessage(p, "Du hast dein tägliches Einzahlungslimit ausgeschöpft. (25 Level) ");
                    }
                } else {
                    _util.sendMessage(p, "So viele Level hast du gar nicht!");
                }
            } else {
                _util.sendMessage(p, "Du hast bereits dein Limit erreicht! (175 Level)");
            }
    }

    public static void payOut(Player p, int count) {
        if (XpDatabaseUtil.getLevel(p) >= count) {
            int level = XpDatabaseUtil.getLevel(p) - count;
            XpDatabaseUtil.updateLevel(p, level);
            p.setLevel(p.getLevel() + count);
            _util.sendMessage(p, "Du hast §6" + count + " §2Level abgehoben!");
            _util.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        } else {
            _util.sendMessage(p, "Dein Kontostand ist zu §4niedrig!");
        }
    }

    public static void openGui(Player p) {
        GuiUtil.openOverview(p);
    }

    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
        _util = new PlayerUtil();

        if (cs instanceof Player) {
            Player p = (Player) cs;

            if(!XpDatabaseUtil.playerExists(p)) { XpDatabaseUtil.createPlayer(p); }

            if (args.length == 0) {
                _util.sendCommandOverview(p, """
                        /xpbank einzahlen <Betrag> | Zahle etwas ein ( 5, 10, 15, 20, 25, alle )
                        /xpbank auszahlen <Betrag> | Zahle etwas aus ( 5, 10, 15, 20, 25, alle )
                        /xpbank list | Zeigt dir Informationen rund um dein Konto an
                        /xpbank gui | Öffne die GUI
                                                
                        Beim Einzahlen zahlst du 20% Steuern, bei ungeraden Level-Zahlen wird gerundet.
                        Du kannst außerdem am Tag nur 25 Level einzahlen und auf der Bank können maximal 175 Level liegen.
                        """, "XPBank");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("einzahlen")) {
                if (args[1].equalsIgnoreCase("alle")) {
                    payIn(p, p.getLevel());
                } else {
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        _util.sendMessage(p, "Bitte gebe eine valide Nummer ein!");
                    }

                    if (DataConstants.validNumbers.contains(quantity)) {
                        payIn(p, quantity);
                    } else {
                        _util.sendMessage(p, "Du kannst nur folgende Beträge einzahlen: 5, 10, 15, 20, 25, alle");
                    }
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("auszahlen")) {
                int level = XpDatabaseUtil.getLevel(p);
                if (args[1].equalsIgnoreCase("alle")) {
                    payOut(p, level);
                } else {
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        _util.sendMessage(p, "Bitte gebe eine valide Nummer ein!");
                    }

                    if (DataConstants.validNumbers.contains(quantity)) {
                        if (level >= quantity) {
                            payOut(p, quantity);
                        } else {
                            _util.sendMessage(p, "Du hast nur " + level + " Level!");
                        }
                    } else {
                        _util.sendMessage(p, "Du kannst nur folgende Beträge auszahlen: 5, 10, 15, 20, 25, alle");
                    }
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                _util.sendMessage(p, "Kontostand: " + XpDatabaseUtil.getLevel(p) + " Level \nEinzahlungen heute: " + XpDatabaseUtil.getLevelsInpayed(p));
            } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
                openGui(p);
            }
        } else {
            _util.noPlayer(cs);
        }
        return false;
    }
}
