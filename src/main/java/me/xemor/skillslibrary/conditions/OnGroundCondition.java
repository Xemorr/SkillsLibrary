package me.xemor.skillslibrary.conditions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class OnGroundCondition extends Condition implements EntityCondition, TargetCondition {

    private boolean grounded;

    public OnGroundCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        grounded = configurationSection.getBoolean("grounded", true);
    }

    @Override
    public boolean isTrue(LivingEntity livingEntity) {
        return isOnGround(livingEntity) == grounded;
    }

    @Override
    public boolean isTrue(LivingEntity livingEntity, Entity entity) {
        return isOnGround(entity) == grounded;
    }

    public boolean isOnGround(Entity entity) {
        Location location = entity.getLocation();
        Block block = location.getBlock();
        return entity instanceof Player ? !block.getRelative(BlockFace.DOWN).isPassable() : entity.isOnGround();
    }
}
