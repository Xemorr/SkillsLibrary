package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public interface LocationCondition {

    CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location);

}
