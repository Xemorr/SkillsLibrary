package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class KnockbackEffect extends Effect implements TargetEffect {

    @JsonPropertyWithDefault
    private Expression multiplier = new Expression(1.0);
    @JsonPropertyWithDefault
    private boolean overwriteCurrentVelocity = true;

    @Override
    public void useEffect(Execution exe, Entity livingEntity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(livingEntity, () -> {
            Location location = livingEntity.getLocation();
            if (livingEntity instanceof Player player) {
                location = player.getEyeLocation();
            }
            Vector knockback = location.getDirection().clone().multiply(multiplier.result(exe, livingEntity, target));
            SkillsLibrary.getFoliaHacks().runASAP(target, () -> {
                if (overwriteCurrentVelocity) target.setVelocity(knockback);
                else target.setVelocity(target.getVelocity().add(knockback));
            });
        });
    }
}
