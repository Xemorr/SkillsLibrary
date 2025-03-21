package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.Map;

public class IgniteEffect extends Effect implements EntityEffect, TargetEffect {

    private final Expression fireTicks;

    @JsonCreator
    public IgniteEffect(Expression fireTicks) {
        this.fireTicks = fireTicks.apply((it) -> it * 20);
    }

    @Override
    public void useEffect(Execution exe, Entity entity) {
        entity.setFireTicks((int) Math.round(fireTicks.result(exe, entity)));
    }

    @Override
    public void useEffect(Execution exe, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> {
            target.setFireTicks((int) Math.round(fireTicks.result(exe, entity, target)));
        });
    }
}
