package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.CompletableFuture;

public class PotionEffectCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private RangeData potency = new RangeData();
    @JsonPropertyWithDefault
    private RangeData duration = new RangeData();
    @CompulsoryJsonProperty
    private PotionEffectType effect = null;

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            for (PotionEffect potionEffect : livingEntity.getActivePotionEffects()) {
                if (potionEffect.getType() == effect || effect == null) {
                    if (potency.isInRange(potionEffect.getAmplifier())) {
                        if (duration.isInRange((double) potionEffect.getDuration() / 20)) {
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
