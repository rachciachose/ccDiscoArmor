// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor;

import com.comphenix.protocol.events.PacketContainer;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.comphenix.protocol.PacketType;
import java.util.Random;
import org.bukkit.entity.Player;
import pl.best241.ccdiscoarmor.config.MessagesData;
import pl.best241.ccdiscoarmor.backend.RedisBackend;
import pl.best241.ccdiscoarmor.tasks.PreviewTask;
import pl.best241.ccdiscoarmor.tasks.ArmorTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.ccdiscoarmor.listeners.PubSubRecieveMessageListener;
import pl.best241.ccdiscoarmor.listeners.ServerPacketListener;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import java.util.UUID;
import java.util.HashMap;
import pl.best241.ccdiscoarmor.backend.Backend;
import org.bukkit.scheduler.BukkitTask;
import pl.best241.ccdiscoarmor.commands.ArmorCommand;
import pl.best241.ccdiscoarmor.listeners.PlayerKickListener;
import pl.best241.ccdiscoarmor.listeners.PlayerQuitListener;
import pl.best241.ccdiscoarmor.listeners.PlayerJoinListener;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmorPlugin extends JavaPlugin
{
    public ProtocolManager protocolManager;
    PacketListener packetListener;
    PlayerJoinListener playerJoinListener;
    PlayerQuitListener playerQuitListener;
    PlayerKickListener playerKickListener;
    ArmorCommand armorCommand;
    BukkitTask armorTask;
    BukkitTask PreviewTask;
    Backend backend;
    private static ArmorPlugin armorPlugin;
    public HashMap<UUID, ArmorType> players;
    public HashMap<UUID, Color> colors;
    
    public ArmorPlugin() {
        this.players = new HashMap<UUID, ArmorType>();
        this.colors = new HashMap<UUID, Color>();
    }
    
    public void onEnable() {
        if (!this.getServer().getPluginManager().isPluginEnabled("rdbPlugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "rdbPlugin not enabled! ccDiscoArmor not started!");
            this.setEnabled(false);
        }
        ArmorPlugin.armorPlugin = this;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.packetListener = (PacketListener)new ServerPacketListener(this);
        this.playerJoinListener = new PlayerJoinListener(this);
        this.playerQuitListener = new PlayerQuitListener(this);
        this.playerKickListener = new PlayerKickListener(this);
        this.getServer().getPluginManager().registerEvents((Listener)new PubSubRecieveMessageListener(), (Plugin)this);
        this.armorCommand = new ArmorCommand(this);
        this.armorTask = new ArmorTask(this).runTaskTimerAsynchronously((Plugin)this, 60L, 60L);
        this.PreviewTask = new PreviewTask(this).runTaskTimer((Plugin)this, 15L, 15L);
        this.backend = new RedisBackend();
        MessagesData.loadMessages((Plugin)this);
    }
    
    public void onDisable() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.updatePlayerArmor(player);
        }
    }
    
    public void updatePlayerArmor(final Player player) {
        for (int i = 1; i < 5; ++i) {
            final Random random = new Random();
            final PacketContainer packet = this.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, (Object)player.getEntityId()).write(1, (Object)i);
            if (player.getInventory().getArmorContents().length > i - 1) {
                if (player.getInventory().getArmorContents()[i - 1] != null) {
                    packet.getItemModifier().write(0, (Object)player.getInventory().getArmorContents()[i - 1]);
                    for (final Player online : Bukkit.getOnlinePlayers()) {
                        if (!online.getName().equals(player.getName())) {
                            try {
                                this.protocolManager.sendServerPacket(online, packet);
                            }
                            catch (InvocationTargetException ex) {
                                Logger.getLogger(ArmorPlugin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void updatePlayerArmorPlayer(final Player player) {
        for (int i = 1; i < 5; ++i) {
            final Random random = new Random();
            final PacketContainer packet = this.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, (Object)player.getEntityId()).write(1, (Object)(i - 1));
            if (player.getInventory().getArmorContents().length > i - 1) {
                if (player.getInventory().getArmorContents()[i - 1] != null) {
                    packet.getItemModifier().write(0, (Object)player.getInventory().getArmorContents()[i - 1]);
                    try {
                        this.protocolManager.sendServerPacket(player, packet);
                    }
                    catch (InvocationTargetException ex) {
                        Logger.getLogger(ArmorPlugin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    public Backend getBackend() {
        return this.backend;
    }
    
    public static ArmorPlugin getPlugin() {
        return ArmorPlugin.armorPlugin;
    }
}
