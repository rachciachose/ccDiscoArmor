// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.listeners;

import org.bukkit.event.EventHandler;
import java.util.UUID;
import org.bukkit.entity.Player;
import pl.best241.ccdiscoarmor.ArmorType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import org.bukkit.event.Listener;

public class PlayerQuitListener implements Listener
{
    ArmorPlugin plugin;
    
    public PlayerQuitListener(final ArmorPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final ArmorType type = this.plugin.players.get(uuid);
        if (type != null) {
            this.plugin.getBackend().setArmorType(uuid, type);
        }
        this.plugin.players.remove(uuid);
        this.plugin.colors.remove(uuid);
    }
}
