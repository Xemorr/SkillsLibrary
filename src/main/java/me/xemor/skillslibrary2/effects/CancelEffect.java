package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class CancelEffect extends Effect implements EntityEffect {
    @Override
    public void useEffect(Execution execution, Entity entity) {
        execution.setCancelled(true);
    }
}
