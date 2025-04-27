package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
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

    @JsonPropertyWithDefault
    private SetData<PotionEffectType> types = new SetData<>();

    public void removeEffects(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            Collection<PotionEffect> activePotionEffects = livingEntity.getActivePotionEffects();
            for (PotionEffect effect : activePotionEffects) {
                if (types.inSet(effect.getType())) {
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
