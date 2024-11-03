package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class SwitcherooWrapperEffect extends WrapperEffect implements TargetEffect {

    public SwitcherooWrapperEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        handleEffects(target, entity);
        handleEffects(target);
    }

}
