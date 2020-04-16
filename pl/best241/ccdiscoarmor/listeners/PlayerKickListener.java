// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.listeners;

import org.bukkit.event.EventHandler;
import java.util.UUID;
import org.bukkit.entity.Player;
import pl.best241.ccdiscoarmor.ArmorType;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.Plugin;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import org.bukkit.event.Listener;

public class PlayerKickListener implements Listener
{
    ArmorPlugin plugin;
    
    public PlayerKickListener(final ArmorPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final ArmorType type = this.plugin.players.get(uuid);
        this.plugin.players.remove(uuid);
        this.plugin.colors.remove(uuid);
        if (type != null) {
            this.plugin.getBackend().setArmorType(uuid, type);
        }
    }
}
