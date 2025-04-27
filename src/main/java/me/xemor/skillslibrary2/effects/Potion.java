package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.PotionEffectData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potion extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    @JsonAlias("potion")
    private PotionEffectData potionEffect;

    public void applyPotionEffect(Entity target) {
        if (target instanceof LivingEntity lTarget) {
            potionEffect.createPotion().ifPresent(lTarget::addPotionEffect);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        applyPotionEffect(target);
    }

    @Override
    public void useEffect(Execution execution, Entity boss) {
        applyPotionEffect(boss);
    }
}