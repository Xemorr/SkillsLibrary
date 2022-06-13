package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public class LoseTargetEffect extends Effect implements EntityEffect {

    public LoseTargetEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;
            mob.setTarget(null);
        }
        return false;
    }
}
