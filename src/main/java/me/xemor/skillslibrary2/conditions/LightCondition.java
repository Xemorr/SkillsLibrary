package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class LightCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    private final boolean checkNatural;
    private final boolean checkBlocks;
    private final RangeData lightRange;

    public LightCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        lightRange = new RangeData("lightRange", configurationSection);
        checkNatural = configurationSection.getBoolean("checkNaturalLight", true);
        checkBlocks = configurationSection.getBoolean("checkBlockLight", true);
        if (!checkNatural && !checkBlocks) {
            SkillsLibrary.getInstance().getLogger().severe("You have disabled checkNaturalLight and checkBlockLight, this effectively disables the condition.");
            SkillsLibrary.getInstance().getLogger().severe("It is unlikely this is what you want to do. "  + configurationSection.getCurrentPath());
        }
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return isLight(entity.getLocation());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return isTrue(execution, entity, target.getLocation());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> isLight(location));
    }

    public boolean isLight(Location location) {
        Block blockAt = location.getWorld().getBlockAt(location);
        byte light;
        if (checkNatural && checkBlocks) {
            light = blockAt.getLightLevel();
        }
        else if (checkNatural) {
            light = blockAt.getLightFromSky();
        } else if (checkBlocks) {
            light = blockAt.getLightFromBlocks();
        }
        else {
            return true;
        }
        return lightRange.isInRange(light);
    }
}
