package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public interface ItemStackCondition {

    CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, ItemStack itemStack);

}
