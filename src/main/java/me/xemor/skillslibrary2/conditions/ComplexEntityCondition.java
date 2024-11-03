package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public interface ComplexEntityCondition {

    CompletableFuture<Boolean> isTrue(Execution execution, Entity entity);

}
