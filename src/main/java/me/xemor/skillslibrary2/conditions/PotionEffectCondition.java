package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;

public class PotionEffectCondition extends Condition implements EntityCondition, TargetCondition {

    private RangeData potencyRange;
    private RangeData durationRange;
    private PotionEffectType type = null;

    public PotionEffectCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        if (configurationSection.contains("type")) {
            String typeStr = configurationSection.getString("type").toLowerCase(Locale.ENGLISH);
            NamespacedKey key = NamespacedKey.fromString(typeStr);
            if (key == null) {
                SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid potion effect type at " + configurationSection.getCurrentPath());
            }
            type = Registry.EFFECT.get(key);
        }
        potencyRange = new RangeData("potency", configurationSection);
        durationRange = new RangeData("duration", configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            for (PotionEffect potionEffect : livingEntity.getActivePotionEffects()) {
                if (potionEffect.getType() == type || type == null) {
                    if (potencyRange.isInRange(potionEffect.getAmplifier())) {
                        if (durationRange.isInRange((double) potionEffect.getDuration() / 20)) {
                            return true;
                        }
                    }
                }
            };
        }
        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isTrue(target);
    }
}
