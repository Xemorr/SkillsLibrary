package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class RemoveEntityEffect extends Effect implements EntityEffect, TargetEffect {

    public RemoveEntityEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        entity.remove();
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        target.remove();
    }
}
