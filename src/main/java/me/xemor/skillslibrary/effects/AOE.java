package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class AOE extends WrapperEffect implements EntityEffect {

    double radius;

    public AOE(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        radius = configurationSection.getDouble("radius", 5);
    }

    @Override
    public boolean useEffect(LivingEntity skillEntity) {
        boolean shouldCancel = false;
        List<Entity> entities = skillEntity.getNearbyEntities(radius, radius, radius);
        for (Entity entity : entities) {
            shouldCancel |= handleEffects(skillEntity, entity);
        }
        return shouldCancel;
    }
}
