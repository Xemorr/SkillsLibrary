package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.particles.ParticleData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ParticleEffect extends Effect implements EntityEffect, TargetEffect, LocationEffect {

    @CompulsoryJsonProperty
    private ParticleData particleData;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            particleData.spawnParticle(livingEntity);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        particleData.spawnParticle(location);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (target instanceof LivingEntity livingEntity) {
            particleData.spawnParticle(livingEntity);
        }
    }
}