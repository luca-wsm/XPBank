package de.luca.xpbank.constants;

public class PrefixConstants {

    private static final String PREFIX = "§7[§cXPBank§7] §2"; //Highlight = §6
    private static final String H1 = "§8}§7§m-------------§8( §a";
    private static final String H2 = " §8)§7§m-------------§8{";
    private static final String HEADER = H1 + " XPBank " + H2;

    public static String getPrefix() { return PREFIX; }

    public static String getH1() {
        return H1;
    }

    public static String getH2() {
        return H2;
    }

}
