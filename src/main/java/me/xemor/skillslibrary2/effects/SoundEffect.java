package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.SoundData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class SoundEffect extends Effect implements EntityEffect, TargetEffect, ComplexLocationEffect {

    @JsonPropertyWithDefault
    private SoundData sound;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        playSound(entity.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity, Entity target) {
        playSound(target.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        playSound(location);
    }

    private void playSound(Location location) {
        location.getWorld().playSound(location, sound.sound(), sound.volume(), sound.pitch());
    }
}
