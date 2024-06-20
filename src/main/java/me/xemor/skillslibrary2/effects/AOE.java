package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.List;

public class AOE extends WrapperEffect implements EntityEffect, TargetEffect, LocationEffect {

    double radius;

    public AOE(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        radius = configurationSection.getDouble("radius", 5);
        if (!configurationSection.isString("mode")) {
            SkillsLibrary.getInstance().getLogger().warning("AoE skill does not have mode specified! Please specify a mode, otherwise this could have unintended behaviour at " + configurationSection.getCurrentPath() + ".mode");
        }
    }
    @Override
    public boolean useEffect(Entity skillEntity, Location location) {
        boolean shouldCancel = false;
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, radius, radius, radius);
        for (Entity entity : entities) {
            if (entity != skillEntity) {
                shouldCancel |= handleEffects(skillEntity);
                shouldCancel |= handleEffects(skillEntity, entity);
            }
        }
        return shouldCancel;
    }

    @Override
    public boolean useEffect(Entity skillEntity) {
        return useEffect(skillEntity, skillEntity.getLocation());
    }


    @Override
    public boolean useEffect(Entity entity, Entity target) {
        return useEffect(entity, target.getLocation());
    }
}
