package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class PotionEffectCondition extends Condition implements EntityCondition, TargetCondition {

    private final RangeData potencyRange;
    private final RangeData durationRange;
    private PotionEffectType type = null;

    public PotionEffectCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        if (configurationSection.contains("effect")) {
            String typeStr = configurationSection.getString("effect").toLowerCase(Locale.ENGLISH);
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
    public boolean isTrue(Execution execution, Entity entity) {
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
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}
