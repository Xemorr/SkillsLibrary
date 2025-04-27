package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.conditions.ConditionList;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class PlaceBlockEffect extends Effect implements ComplexLocationEffect {

    @CompulsoryJsonProperty
    @JsonAlias("block")
    private BlockData blockData = null;
    @JsonPropertyWithDefault
    private boolean updatePhysics = true;
    @JsonPropertyWithDefault
    private boolean isPacket = false;
    @JsonPropertyWithDefault
    private ConditionList revertConditions = new ConditionList();
    @JsonPropertyWithDefault
    private Expression revertsAfter = new Expression(-1);

    private int getRevertsAfter(Execution execution, Entity entity) {
        return (int) Math.round(revertsAfter.result(execution, entity) * 20);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            int revertsAfter = getRevertsAfter(execution, entity);
            SkillsLibrary.getFoliaHacks().runASAP(location, () -> {
                if (isPacket) {
                    handlePacketPlaceBlock(execution, entity, location, revertsAfter);
                }
                else {
                    handlePlaceBlock(execution, entity, location, revertsAfter);
                }
            });
        });
    }

    public void handlePacketPlaceBlock(Execution execution, Entity entity, Location location, int revertsAfter) {
        if (revertsAfter <= 0) return;
        if (entity instanceof Player player) {
            BlockData currentData = location.getBlock().getBlockData();
            SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
                player.sendBlockChange(location, blockData);
            });
            SkillsLibrary.getScheduling().regionSpecificScheduler(location).runAtFixedRate((task) -> {
                CompletableFuture<Boolean> conditionsPassed = revertConditions.ANDConditions(execution, entity, false, location);
                conditionsPassed.thenAccept((b) -> {
                    if (b) {
                        task.cancel();
                    }
                    if (location.getBlock().getBlockData().matches(currentData)) {
                        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> player.sendBlockChange(location, location.getBlock().getBlockData()));
                    }
                });
            }, revertsAfter, revertsAfter);
        }
    }

    public void handlePlaceBlock(Execution execution, Entity entity, Location location, int revertsAfter) {
        Block block = location.getBlock();
        BlockData oldData = block.getBlockData();
        block.setBlockData(blockData, updatePhysics);
        if (revertsAfter > 0) {
            SkillsLibrary.getScheduling().regionSpecificScheduler(location).runAtFixedRate((task) -> {
                CompletableFuture<Boolean> conditionsPassed = revertConditions.ANDConditions(execution, entity, false, location);
                conditionsPassed.thenAccept((b) -> {
                    if (b) {
                        if (block.getBlockData().matches(blockData)) {
                            block.setBlockData(oldData, updatePhysics);
                        }
                        task.cancel();
                    }
                });
            }, revertsAfter, revertsAfter);
        }
    }
}
