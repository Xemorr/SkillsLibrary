package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class SwitcherooWrapperEffect extends WrapperEffect implements TargetEffect {

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        handleEffects(execution, target, entity);
        handleEffects(execution, target);
    }

}
