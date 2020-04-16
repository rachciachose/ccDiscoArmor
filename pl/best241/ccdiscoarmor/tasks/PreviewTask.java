// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.tasks;

import redis.clients.jedis.Jedis;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import pl.best241.rdbplugin.JedisFactory;
import org.bukkit.Bukkit;
import java.util.UUID;
import java.util.ArrayList;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PreviewTask extends BukkitRunnable
{
    ArmorPlugin plugin;
    protected static ArrayList<UUID> preview;
    
    public PreviewTask(final ArmorPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.isSneaking()) {
                if (!PreviewTask.preview.contains(player.getUniqueId())) {
                    PreviewTask.preview.add(player.getUniqueId());
                    final Jedis jedis = JedisFactory.getInstance().getJedis();
                    jedis.publish("discoArmor.preview", player.getUniqueId().toString() + ":true");
                    JedisFactory.getInstance().returnJedis(jedis);
                }
            }
            else if (!player.isSneaking() && PreviewTask.preview.contains(player.getUniqueId())) {
                final Jedis jedis = JedisFactory.getInstance().getJedis();
                jedis.publish("discoArmor.preview", player.getUniqueId().toString() + ":false");
                JedisFactory.getInstance().returnJedis(jedis);
                Bukkit.getScheduler().runTask((Plugin)this.plugin, (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        PreviewTask.preview.remove(player.getUniqueId());
                        PreviewTask.this.plugin.updatePlayerArmorPlayer(player);
                    }
                });
            }
        }
    }
    
    static {
        PreviewTask.preview = new ArrayList<UUID>();
    }
}
