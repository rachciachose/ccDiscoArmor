// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.backend;

import redis.clients.jedis.Jedis;
import pl.best241.rdbplugin.JedisFactory;
import pl.best241.ccdiscoarmor.ArmorType;
import java.util.UUID;

public class RedisBackend implements Backend
{
    @Override
    public ArmorType getArmorType(final UUID uuid) {
        final Jedis resource = JedisFactory.getInstance().getJedis();
        final String value = resource.hget("ccDiscoArmor.playerArmor", uuid.toString());
        JedisFactory.getInstance().returnJedis(resource);
        if (value == null) {
            return null;
        }
        return ArmorType.valueOf(value);
    }
    
    @Override
    public void setArmorType(final UUID uuid, final ArmorType type) {
        final Jedis resource = JedisFactory.getInstance().getJedis();
        resource.hset("ccDiscoArmor.playerArmor", uuid.toString(), type.toString());
        JedisFactory.getInstance().returnJedis(resource);
    }
}
