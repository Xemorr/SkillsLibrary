package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;

public class AOE extends WrapperEffect implements EntityEffect {

    double radius;

    public AOE(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        radius = configurationSection.getDouble("radius", 5);
    }

    @Override
    public boolean useEffect(Entity skillEntity) {
        boolean shouldCancel = false;
        List<Entity> entities = skillEntity.getNearbyEntities(radius, radius, radius);
        for (Entity entity : entities) {
            shouldCancel |= handleEffects(skillEntity);
            shouldCancel |= handleEffects(skillEntity, entity);
        }
        return shouldCancel;
    }
}
