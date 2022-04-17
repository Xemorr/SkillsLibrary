package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import me.xemor.configurationdata.VectorData;

public class LocationOffsetEffect extends WrapperEffect implements EntityEffect, LocationEffect, TargetEffect {

    private final Vector offset;

    public LocationOffsetEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection offsetSection = configurationSection.getConfigurationSection("offset");
        if (offsetSection == null) {
            offset = new Vector(0, 0, 0);
        } else {
            offset = new VectorData(offsetSection).getVector();
        }
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        return handleEffects(entity, location.add(offset));
    }

    @Override
    public boolean useEffect(Entity entity) {
        handleEffects(entity, entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        useEffect(entity, target.getLocation());
        return false;
    }

}
