package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

//TODO
public class LocationOffsetEffect extends Effect implements LocationEffect{
    public LocationOffsetEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        return false;
    }
}
