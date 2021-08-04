package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LungeEffect extends Effect implements EntityEffect, TargetEffect {

    private final double horizontalVelocity;
    private final double verticalVelocity;
    private final boolean overwrite;

    public LungeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        horizontalVelocity = configurationSection.getDouble("horizontalVelocity");
        verticalVelocity = configurationSection.getDouble("verticalVelocity");
        overwrite = configurationSection.getBoolean("overwrite", true);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            setVelocity((LivingEntity) entity);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        if (target instanceof LivingEntity) {
            setVelocity((LivingEntity) target);
        }
        return false;
    }

    public void setVelocity(LivingEntity entity) {
        Vector direction = entity.getEyeLocation().getDirection();
        double x = direction.getX() * horizontalVelocity;
        double z = direction.getZ() * horizontalVelocity;
        double y = direction.getY() * verticalVelocity;
        if (overwrite) entity.setVelocity(new Vector(x, y, z));
        else entity.setVelocity(entity.getVelocity().add(new Vector(x, y, z)));
    }
}
