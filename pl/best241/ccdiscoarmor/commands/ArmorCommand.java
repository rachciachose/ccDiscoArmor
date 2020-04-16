// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.commands;

import redis.clients.jedis.Jedis;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import pl.best241.rdbplugin.JedisFactory;
import pl.best241.ccdiscoarmor.ArmorType;
import pl.best241.ccdiscoarmor.config.MessagesData;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import pl.best241.ccdiscoarmor.ArmorPlugin;
import org.bukkit.command.CommandExecutor;

public class ArmorCommand implements CommandExecutor
{
    ArmorPlugin plugin;
    
    public ArmorCommand(final ArmorPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("disco").setExecutor((CommandExecutor)this);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesData.commandOnlyForPlayers);
            return true;
        }
        final Player player = (Player)sender;
        if (args.length != 1) {
            sender.sendMessage(MessagesData.discoUsage);
            return false;
        }
        if (!sender.hasPermission("discoarmor.manage")) {
            sender.sendMessage(MessagesData.noPermissions);
            return false;
        }
        final String arg = args[0];
        final UUID uuid = player.getUniqueId();
        if (arg.equalsIgnoreCase("start")) {
            final ArmorType armor = this.plugin.getBackend().getArmorType(uuid);
            if (armor == null || !this.plugin.players.containsKey(uuid) || !this.plugin.colors.containsKey(uuid) || ArmorType.DISABLED == armor) {
                player.sendMessage(MessagesData.youMustSetUpMode);
                return false;
            }
            this.plugin.players.put(uuid, armor);
            this.plugin.updatePlayerArmor(player);
            final Jedis jedis = JedisFactory.getInstance().getJedis();
            jedis.publish("discoArmor.start", player.getUniqueId().toString() + "|" + armor.toString() + "|" + player.getEntityId());
            JedisFactory.getInstance().returnJedis(jedis);
            player.sendMessage(MessagesData.discoArmorWasEnabled);
        }
        else if (arg.equalsIgnoreCase("stop")) {
            final Jedis jedis2 = JedisFactory.getInstance().getJedis();
            jedis2.publish("discoArmor.stop", player.getUniqueId().toString());
            JedisFactory.getInstance().returnJedis(jedis2);
            this.plugin.getBackend().setArmorType(uuid, ArmorType.DISABLED);
            this.plugin.players.remove(uuid);
            this.plugin.colors.remove(uuid);
            Bukkit.getScheduler().runTask((Plugin)this.plugin, (Runnable)new Runnable() {
                @Override
                public void run() {
                    ArmorCommand.this.plugin.updatePlayerArmor(player);
                    player.sendMessage(MessagesData.discoArmorWasDisabled);
                }
            });
        }
        else if (arg.equalsIgnoreCase("random")) {
            final Jedis jedis2 = JedisFactory.getInstance().getJedis();
            jedis2.publish("discoArmor.changeState", player.getUniqueId().toString() + "|" + ArmorType.RANDOM.toString());
            JedisFactory.getInstance().returnJedis(jedis2);
            this.plugin.colors.put(uuid, Color.fromRGB(0, 0, 0));
            this.plugin.players.put(uuid, ArmorType.RANDOM);
            this.plugin.getBackend().setArmorType(uuid, ArmorType.RANDOM);
            player.sendMessage(MessagesData.discoArmorWasCorrectlySetUp);
        }
        else if (arg.equalsIgnoreCase("ultra")) {
            final Jedis jedis2 = JedisFactory.getInstance().getJedis();
            jedis2.publish("discoArmor.changeState", player.getUniqueId().toString() + "|" + ArmorType.ULTRA.toString());
            JedisFactory.getInstance().returnJedis(jedis2);
            this.plugin.colors.put(uuid, Color.fromRGB(0, 0, 0));
            this.plugin.players.put(uuid, ArmorType.ULTRA);
            this.plugin.getBackend().setArmorType(uuid, ArmorType.ULTRA);
            player.sendMessage(MessagesData.discoArmorWasCorrectlySetUp);
        }
        else if (arg.equalsIgnoreCase("smooth")) {
            final Jedis jedis2 = JedisFactory.getInstance().getJedis();
            jedis2.publish("discoArmor.changeState", player.getUniqueId().toString() + "|" + ArmorType.SMOOTH.toString());
            JedisFactory.getInstance().returnJedis(jedis2);
            this.plugin.colors.put(uuid, Color.fromRGB(255, 0, 0));
            this.plugin.players.put(uuid, ArmorType.SMOOTH);
            this.plugin.getBackend().setArmorType(uuid, ArmorType.SMOOTH);
            player.sendMessage(MessagesData.discoArmorWasCorrectlySetUp);
        }
        else if (arg.equalsIgnoreCase("gray")) {
            final Jedis jedis2 = JedisFactory.getInstance().getJedis();
            jedis2.publish("discoArmor.changeState", player.getUniqueId().toString() + "|" + ArmorType.GRAY.toString());
            JedisFactory.getInstance().returnJedis(jedis2);
            this.plugin.colors.put(uuid, Color.fromRGB(255, 0, 0));
            this.plugin.players.put(uuid, ArmorType.GRAY);
            this.plugin.getBackend().setArmorType(uuid, ArmorType.GRAY);
            player.sendMessage(MessagesData.discoArmorWasCorrectlySetUp);
        }
        else {
            if (!arg.equalsIgnoreCase("policaj")) {
                return false;
            }
            final Jedis jedis2 = JedisFactory.getInstance().getJedis();
            jedis2.publish("discoArmor.changeState", player.getUniqueId().toString() + "|" + ArmorType.POLICAJ.toString());
            JedisFactory.getInstance().returnJedis(jedis2);
            this.plugin.colors.put(uuid, Color.fromRGB(0, 0, 0));
            this.plugin.players.put(uuid, ArmorType.POLICAJ);
            this.plugin.getBackend().setArmorType(uuid, ArmorType.POLICAJ);
            player.sendMessage(MessagesData.discoArmorWasCorrectlySetUp);
        }
        return true;
    }
}
