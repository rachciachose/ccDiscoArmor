// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.listeners;

import java.util.UUID;
import com.comphenix.protocol.events.PacketContainer;
import pl.best241.ccdiscoarmor.ArmorType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ConnectionSide;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import com.comphenix.protocol.events.PacketAdapter;

public class ServerPacketListener extends PacketAdapter
{
    ArmorPlugin plugin;
    
    public ServerPacketListener(final ArmorPlugin plugin) {
        super((Plugin)plugin, ConnectionSide.SERVER_SIDE, new Integer[] { PacketType.Play.Server.ENTITY_EQUIPMENT.getLegacyId() });
        this.plugin = plugin;
        plugin.protocolManager.addPacketListener((PacketListener)this);
    }
    
    public void onPacketSending(final PacketEvent event) {
        if (!event.isCancelled() && event.getPacketType() == PacketType.Play.Server.ENTITY_EQUIPMENT) {
            final PacketContainer packet = event.getPacket();
            final Entity entity = (Entity)packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
            if (entity instanceof Player) {
                final Player player = (Player)entity;
                final UUID uuid = player.getUniqueId();
                final ArmorType type = this.plugin.players.get(uuid);
                if (type != null) {}
            }
        }
    }
}
