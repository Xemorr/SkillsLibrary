package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class FlyEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private boolean fly = true;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof Player player) {
            player.setAllowFlight(fly);
            player.setFlying(fly);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
    }
}
