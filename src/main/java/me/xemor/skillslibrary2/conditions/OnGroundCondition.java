package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class OnGroundCondition extends Condition implements EntityCondition, TargetCondition {

    private final boolean grounded;

    public OnGroundCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        grounded = configurationSection.getBoolean("grounded", true);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return isOnGround(entity) == grounded;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity otherEntity) {
        return SkillsLibrary.getFoliaHacks().runASAP(otherEntity, () -> isOnGround(otherEntity) == grounded);
    }

    public boolean isOnGround(Entity entity) {
        Location location = entity.getLocation();
        Block block = location.getBlock();
        return entity instanceof Player ? !block.getRelative(BlockFace.DOWN).isPassable() : entity.isOnGround();
    }
}
