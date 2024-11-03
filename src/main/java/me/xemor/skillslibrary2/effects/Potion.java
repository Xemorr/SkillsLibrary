package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.PotionEffectData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potion extends Effect implements EntityEffect, TargetEffect {

    private final PotionEffect potionEffect;

    public Potion(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection potionSection = configurationSection.getConfigurationSection("potion");
        if (potionSection != null) {
            PotionEffectData potionEffectData = new PotionEffectData(potionSection);
            if (potionEffectData.getPotionEffect().isPresent()) {
                potionEffect = potionEffectData.getPotionEffect().get();
            } else {
                SkillsLibrary.getInstance().getLogger().severe("Falling back to default regeneration potion effect as " + configurationSection.getCurrentPath() + ".potion is invalid.");
                potionEffect = PotionEffectType.getByName("REGENERATION").createEffect(10, 1);
            }
        } else {
            SkillsLibrary.getInstance().getLogger().severe("Falling back to default regeneration potion effect as " + configurationSection.getCurrentPath() + ".potion is null.");
            potionEffect = PotionEffectType.getByName("REGENERATION").createEffect(10, 1);
        }
    }

    public void applyPotionEffect(Entity target) {
        if (target instanceof LivingEntity lTarget) {
            lTarget.addPotionEffect(potionEffect);
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