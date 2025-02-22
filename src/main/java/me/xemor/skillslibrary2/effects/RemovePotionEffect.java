package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class RemovePotionEffect extends Effect implements EntityEffect, TargetEffect {

    private Set<PotionEffectType> types;

    public RemovePotionEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        types = configurationSection.getStringList("types").stream().map(String::toUpperCase).map(PotionEffectType::getByName).collect(Collectors.toSet());
    }

    public boolean inSet(PotionEffectType type) {
        return types.isEmpty() || types.contains(type);
    }

    public void removeEffects(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            Collection<PotionEffect> activePotionEffects = livingEntity.getActivePotionEffects();
            for (PotionEffect effect : activePotionEffects) {
                if (inSet(effect.getType())) {
                    livingEntity.removePotionEffect(effect.getType());
                }
            }

        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        removeEffects(entity);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        removeEffects(target);
    }

}
