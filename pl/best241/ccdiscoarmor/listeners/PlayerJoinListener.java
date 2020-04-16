// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.UUID;
import redis.clients.jedis.Jedis;
import pl.best241.rdbplugin.JedisFactory;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import pl.best241.ccdiscoarmor.ArmorType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener
{
    ArmorPlugin plugin;
    
    public PlayerJoinListener(final ArmorPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final ArmorType type = this.plugin.getBackend().getArmorType(uuid);
        if (type != null) {
            if (type == ArmorType.DISABLED) {
                return;
            }
            if (type == ArmorType.GRAY) {
                this.plugin.colors.put(uuid, Color.fromRGB(255, 0, 0));
            }
            if (type == ArmorType.RANDOM) {
                this.plugin.colors.put(uuid, Color.fromRGB(0, 0, 0));
            }
            if (type == ArmorType.SMOOTH) {
                this.plugin.colors.put(uuid, Color.fromRGB(255, 0, 0));
            }
            if (type == ArmorType.ULTRA) {
                this.plugin.colors.put(uuid, Color.fromRGB(0, 0, 0));
            }
            this.plugin.players.put(uuid, type);
            this.plugin.updatePlayerArmor(player);
            Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, (Runnable)new Runnable() {
                @Override
                public void run() {
                    final Jedis jedis = JedisFactory.getInstance().getJedis();
                    jedis.publish("discoArmor.start", player.getUniqueId().toString() + "|" + type.toString() + "|" + player.getEntityId());
                    JedisFactory.getInstance().returnJedis(jedis);
                }
            }, 20L);
        }
    }
}
