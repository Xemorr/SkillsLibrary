package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class NearestEffect extends WrapperEffect implements EntityEffect, TargetEffect, ComplexLocationEffect {

    private final double radius;

    public NearestEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        radius = configurationSection.getDouble("radius", 5);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        getNearest(execution, entity, location).thenAccept((nearest) -> {
            if (nearest == null) return;
            handleEffects(execution, entity, nearest);
        });
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        getNearest(execution, entity, entity.getLocation()).thenAccept((nearest) -> {
            if (nearest == null) return;
            handleEffects(execution, entity, nearest);
        });
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity, Entity target) {
        getNearest(execution, livingEntity, target.getLocation()).thenAccept((nearest) -> {
            if (nearest == null) return;
            handleEffects(execution, livingEntity, nearest);
        });
    }

    @NotNull
    public CompletableFuture<LivingEntity> getNearest(Execution execution, Entity livingEntity, Location location) {
        CompletableFuture<LivingEntity> completableFuture = new CompletableFuture<>();
        SkillsLibrary.getFoliaHacks().runASAP(location, () -> {
            World world = location.getWorld();
            List<Entity> entities = new ArrayList<>(world.getNearbyEntities(location, radius, radius, radius));
            entities.removeIf((entity -> !(entity instanceof LivingEntity)));
            if (entities.isEmpty()) {
                return;
            }
            SkillsLibrary.getFoliaHacks().runASAP(livingEntity, () -> {
                CompletableFuture[] futures = entities
                        .stream()
                        .map((entity) -> getConditions().ANDConditions(execution, livingEntity, false, entity))
                        .toArray(CompletableFuture[]::new);
                CompletableFuture.allOf(
                        futures
                ).thenAccept((result) -> {
                    List<Entity> filteredEntities = new ArrayList<>();
                    for (int i = 0; i < futures.length; i++) {
                        Entity entity = entities.get(i);
                        // This is always true, we've just lost the generic information due to the stream
                        CompletableFuture<Boolean> future = (CompletableFuture<Boolean>) futures[i];
                        try {
                            if (future.get()) {
                                filteredEntities.add(entity);
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            SkillsLibrary.getInstance().getLogger().severe("This should never happen. Contact the developer of SkillsLibrary if you see this error.");
                        }
                    }
                    completableFuture.complete((LivingEntity) Collections.min(filteredEntities, Comparator.comparingDouble(entity -> entity.getLocation().distanceSquared(location))));
                });
            });
        });
        return completableFuture;
    }

}
