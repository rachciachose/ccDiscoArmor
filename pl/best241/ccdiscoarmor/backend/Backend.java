// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdiscoarmor.backend;

import pl.best241.ccdiscoarmor.ArmorType;
import java.util.UUID;

public interface Backend
{
    ArmorType getArmorType(final UUID p0);
    
    void setArmorType(final UUID p0, final ArmorType p1);
}
