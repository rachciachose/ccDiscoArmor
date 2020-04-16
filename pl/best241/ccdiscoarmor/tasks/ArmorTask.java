// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.Iterator;
import redis.clients.jedis.Jedis;
import org.bukkit.Bukkit;
import java.util.UUID;
import pl.best241.rdbplugin.JedisFactory;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ArmorTask extends BukkitRunnable
{
    ArmorPlugin plugin;
    private static int distance;
    private static int distanaceSquared;
    
    public ArmorTask(final ArmorPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void run() {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        for (final UUID uuid : this.plugin.players.keySet()) {
            final Player discoPlayer = Bukkit.getPlayer(uuid);
            if (discoPlayer != null) {
                if (!discoPlayer.isOnline()) {
                    continue;
                }
                String dataToSend = discoPlayer.getUniqueId().toString() + ":";
                final Location discoPlayerLoc = discoPlayer.getLocation();
                int playerCount = 0;
                for (final Player online : Bukkit.getOnlinePlayers()) {
                    if (!uuid.equals(online.getUniqueId())) {
                        if (discoPlayerLoc.distanceSquared(online.getLocation()) <= ArmorTask.distanaceSquared) {
                            dataToSend = dataToSend + online.getUniqueId().toString() + "|";
                            ++playerCount;
                        }
                    }
                }
                if (playerCount > 0) {
                    dataToSend = dataToSend.substring(0, dataToSend.length() - 1);
                }
                jedis.publish("discoArmor.bcastTo", dataToSend);
            }
        }
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    static {
        ArmorTask.distance = 10;
        ArmorTask.distanaceSquared = ArmorTask.distance * ArmorTask.distance;
    }
}
