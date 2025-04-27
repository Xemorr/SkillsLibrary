package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class RepulseEffect extends Effect implements TargetEffect {

    @JsonPropertyWithDefault
    private double velocity = 1.0;
    @JsonPropertyWithDefault
    private boolean add = false;

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        repulse(entity, target);
    }

    public void repulse(Entity boss, Entity target) {
        Vector repulseVelocity = target.getLocation().subtract(boss.getLocation()).toVector().normalize().multiply(velocity);
        if (Double.isNaN(repulseVelocity.lengthSquared())) {
            return;
        }
        if (add) {
            target.setVelocity(target.getVelocity().add(repulseVelocity));
        }
        else {
            target.setVelocity(repulseVelocity);
        }
    }
}
