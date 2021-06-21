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
        for (Effect effect : getNextEffects()) {
            if (effect instanceof TargetEffect) {
                TargetEffect nextEffect = (TargetEffect) effect;
                List<Entity> entities = skillEntity.getNearbyEntities(radius, radius, radius);
                for (Entity entity : entities) {
                    if (areConditionsTrue(skillEntity, entity)) {
                        shouldCancel |= nextEffect.useEffect(skillEntity, entity);
                    }
                }
            }
        }
        return shouldCancel;
    }
}
