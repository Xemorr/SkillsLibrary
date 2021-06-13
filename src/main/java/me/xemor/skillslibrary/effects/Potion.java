package me.xemor.skillslibrary.effects;

import me.xemor.configurationdata.ConfigurationData;
import me.xemor.configurationdata.PotionEffectData;
import me.xemor.skillslibrary.SkillsLibrary;
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
            PotionEffectData potionEffectData = new PotionEffectData(potionSection, PotionEffectType.REGENERATION, 10, 1);
            potionEffect = potionEffectData.getPotionEffect();
        } else {
            SkillsLibrary.getInstance().getLogger().severe("Falling back to default regeneration potion effect as " + configurationSection.getCurrentPath() + ".potion is null.");
            potionEffect = PotionEffectType.getByName("REGENERATION").createEffect(10, 1);
        }
    }

    public void applyPotionEffect(Entity target) {
        if (target instanceof LivingEntity) {
            LivingEntity lTarget = (LivingEntity) target;
            lTarget.addPotionEffect(potionEffect);
        }
    }

    @Override
    public boolean useEffect(LivingEntity boss, Entity target) {
        applyPotionEffect(target);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity boss) {
        applyPotionEffect(boss);
        return false;
    }
}