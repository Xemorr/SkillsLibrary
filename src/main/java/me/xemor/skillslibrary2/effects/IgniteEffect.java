package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.entity.Entity;

public class IgniteEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private Expression fireTicks;

    @Override
    public void useEffect(Execution exe, Entity entity) {
        entity.setFireTicks((int) Math.round(fireTicks.result(exe, entity) * 20));
    }

    @Override
    public void useEffect(Execution exe, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> {
            target.setFireTicks((int) Math.round(fireTicks.result(exe, entity, target) * 20));
        });
    }
}
