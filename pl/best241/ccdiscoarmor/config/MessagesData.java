// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.config;

import org.bukkit.plugin.Plugin;

public class MessagesData
{
    public static String commandOnlyForPlayers;
    public static String discoUsage;
    public static String noPermissions;
    public static String youMustSetUpMode;
    public static String discoArmorWasEnabled;
    public static String discoArmorWasDisabled;
    public static String discoArmorWasCorrectlySetUp;
    private static MessagesConfig config;
    
    public static void loadMessages(final Plugin plugin) {
        (MessagesData.config = new MessagesConfig(plugin, "messages.yml")).saveDefaultConfig();
        MessagesData.config.reloadCustomConfig();
        MessagesData.commandOnlyForPlayers = MessagesData.config.getString("commandOnlyForPlayers");
        MessagesData.discoUsage = MessagesData.config.getString("discoUsage");
        MessagesData.noPermissions = MessagesData.config.getString("noPermissions");
        MessagesData.youMustSetUpMode = MessagesData.config.getString("youMustSetUpMode");
        MessagesData.discoArmorWasEnabled = MessagesData.config.getString("discoArmorWasEnabled");
        MessagesData.discoArmorWasDisabled = MessagesData.config.getString("discoArmorWasDisabled");
        MessagesData.discoArmorWasCorrectlySetUp = MessagesData.config.getString("discoArmorWasCorrectlySetUp");
    }
}
