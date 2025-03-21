package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;

public class ConditionList implements Iterable<Condition> {

    @JsonIgnore
    private List<Condition> conditions = new ArrayList<>(1);

    public ConditionList() {}

    @JsonAnySetter
    public void loadCondition(String name, Condition condition) {
        conditions.add(condition);
    }

    public CompletableFuture<Boolean> ANDConditions(Entity entity, boolean exact, Object... objects) {
        return ANDConditions(new Execution(), entity, exact, objects);
    }

    public CompletableFuture<Boolean> ANDConditions(Execution execution, Entity entity, boolean exact, Object... objects) {
        Object otherObject = objects.length == 0 ? null : objects[0];
        CompletableFuture<Boolean> resultFuture = CompletableFuture.completedFuture(true);
        for (Condition condition : conditions) {
            resultFuture = resultFuture.thenCompose(previousResult -> {
                CompletableFuture<Boolean> future = CompletableFuture.completedFuture(previousResult);
                if (!previousResult) return future;
                if (condition instanceof EntityCondition entityCondition && condition.getMode().runs(Mode.SELF) && (!exact || otherObject == null)) {
                    future = future.thenCompose((bool) -> calculateResultAndElseBranch(execution, entityCondition, bool, entity));
                }
                if (condition instanceof TargetCondition targetCondition && otherObject instanceof Entity other && condition.getMode().runs(Mode.OTHER)) {
                    future = future.thenCompose((bool) -> calculateResultAndElseBranch(execution, targetCondition, bool, entity, other));
                }
                if (condition instanceof LocationCondition locationCondition && otherObject instanceof Location location && condition.getMode().runs(Mode.LOCATION)) {
                    future = future.thenCompose((bool) -> calculateResultAndElseBranch(execution, locationCondition, bool, entity, location));
                }
                if (condition instanceof ItemStackCondition itemStackCondition && otherObject instanceof ItemStack item && condition.getMode().runs(Mode.ITEM)) {
                    future = future.thenCompose((bool) -> calculateResultAndElseBranch(execution, itemStackCondition, bool, entity, item));
                }
                return future;
            });
        }
        return resultFuture.exceptionally(ex -> {
            ex.printStackTrace();
            return false;
        });
    }


    public CompletableFuture<Boolean> calculateResultAndElseBranch(Execution execution, EntityCondition entityCondition, boolean currentValue, Entity entity) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = CompletableFuture.completedFuture(entityCondition.isTrue(execution, entity));
            completableB = completableB
                    .thenApply((b) -> b && currentValue)
                    .thenApply(b -> {
                        if (!b) {
                            if (entityCondition instanceof Condition condition) {
                                condition.getOtherwise().handleEffects(execution, entity);
                            }
                        }
                        return b;
                    }
            );
            return completableB;
        });
    }

    public CompletableFuture<Boolean> calculateResultAndElseBranch(Execution execution, TargetCondition targetCondition, boolean currentValue, Entity entity, Entity other) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = targetCondition.isTrue(execution, entity, other);
            completableB
                    .thenApply((b) -> b && currentValue)
                    .thenAccept(b -> {
                if (!b) {
                    if (targetCondition instanceof Condition condition) {
                        condition.getOtherwise().handleEffects(execution, entity, other);
                    }
                }
            });
            return completableB;
        });
    }

    public CompletableFuture<Boolean> calculateResultAndElseBranch(Execution execution, ItemStackCondition itemCondition, boolean currentValue, Entity entity, ItemStack item) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = itemCondition.isTrue(execution, entity, item);
            completableB.thenApply((b) -> b && currentValue).thenAccept(b -> {
                if (!b) {
                    if (itemCondition instanceof Condition condition) {
                        condition.getOtherwise().handleEffects(execution, entity, item);
                    }
                }
            });
            return completableB;
        });
    }

    public CompletableFuture<Boolean> calculateResultAndElseBranch(Execution execution, LocationCondition locationCondition, boolean currentValue, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = locationCondition.isTrue(execution, entity, location);
            completableB.thenApply((b) -> b && currentValue).thenAccept(b -> {
                if (!b) {
                    if (locationCondition instanceof Condition condition) {
                        condition.getOtherwise().handleEffects(execution, entity, location);
                    }
                }
            });
            return completableB;
        });
    }

    public CompletableFuture<Boolean> ORConditions(Execution execution, Entity entity, boolean exact, Object... objects) {
        Object otherObject = objects.length == 0 ? null : objects[0];
        CompletableFuture<Boolean> resultFuture = CompletableFuture.completedFuture(false);
        for (Condition condition : conditions) {
            resultFuture = resultFuture.thenCompose(previousResult -> {
                CompletableFuture<Boolean> future = CompletableFuture.completedFuture(previousResult);
                if (previousResult) return future;
                if (condition instanceof EntityCondition entityCondition && condition.getMode().runs(Mode.SELF) && (!exact || otherObject == null)) {
                    future = future.thenCompose((bool) -> (handleElseBranchForOr(execution, entityCondition, bool, entity)));
                }
                if (condition instanceof TargetCondition targetCondition && otherObject instanceof Entity other && condition.getMode().runs(Mode.OTHER)) {
                    future = future.thenCompose((bool) -> (handleElseBranchForOr(execution, targetCondition, bool, entity, other)));
                }
                if (condition instanceof LocationCondition locationCondition && otherObject instanceof Location location && condition.getMode().runs(Mode.LOCATION)) {
                    future = future.thenCompose((bool) -> (handleElseBranchForOr(execution, locationCondition, bool, entity, location)));
                }
                if (condition instanceof ItemStackCondition itemStackCondition && otherObject instanceof ItemStack item && condition.getMode().runs(Mode.ITEM)) {
                    future = future.thenCompose((bool) -> (handleElseBranchForOr(execution, itemStackCondition, bool, entity, item)));
                }
                return future;
            });
        }
        return resultFuture;
    }

    public CompletableFuture<Boolean> handleElseBranchForOr(Execution execution, EntityCondition entityCondition, boolean currentValue, Entity entity) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = CompletableFuture.completedFuture(entityCondition.isTrue(execution, entity));
            completableB = completableB
                    .thenApply((b) -> b || currentValue)
                    .thenApply(b -> {
                        if (!b) {
                            if (entityCondition instanceof Condition condition) {
                                condition.getOtherwise().handleEffects(execution, entity);
                            }
                        }
                        return b;
                    });
            return completableB;
        });
    }

    public CompletableFuture<Boolean> handleElseBranchForOr(Execution execution, TargetCondition targetCondition, boolean currentValue, Entity entity, Entity other) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = targetCondition.isTrue(execution, entity, other);
            completableB.thenApply((b) -> b || currentValue)
                    .thenAccept(b -> {
                        if (!b) {
                            if (targetCondition instanceof Condition condition) {
                                condition.getOtherwise().handleEffects(execution, entity, other);
                            }
                        }
                    });
            return completableB;
        });
    }

    public CompletableFuture<Boolean> handleElseBranchForOr(Execution execution, ItemStackCondition itemCondition, boolean currentValue, Entity entity, ItemStack item) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = itemCondition.isTrue(execution, entity, item);
            completableB.thenApply((b) -> b || currentValue).thenAccept(b -> {
                if (!b) {
                    if (itemCondition instanceof Condition condition) {
                        condition.getOtherwise().handleEffects(execution, entity, item);
                    }
                }
            });
            return completableB;
        });
    }

    public CompletableFuture<Boolean> handleElseBranchForOr(Execution execution, LocationCondition locationCondition, boolean currentValue, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            CompletableFuture<Boolean> completableB = locationCondition.isTrue(execution, entity, location);
            completableB.thenApply((b) -> b || currentValue).thenAccept(b -> {
                if (!b) {
                    if (locationCondition instanceof Condition condition) {
                        condition.getOtherwise().handleEffects(execution, entity, location);
                    }
                }
            });
            return completableB;
        });
    }

    public void prependCondition(Condition condition) { conditions.add(0, condition); }

    public void appendCondition(Condition condition) { conditions.add(condition); }

    @Deprecated
    public void addCondition(Condition condition) {
        conditions.add(0, condition);
    }

    @NotNull
    @Override
    public Iterator<Condition> iterator() {
        return conditions.iterator();
    }
}
