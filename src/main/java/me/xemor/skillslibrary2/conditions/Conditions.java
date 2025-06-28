package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conditions {

    private static final HashMap<String, ConditionId> nameToCondition = new HashMap<>();
    private static final List<Class<? extends Condition>> conditionToClass = new ArrayList<>();
    private static int counter;

    static {
        register("HEALTH", HealthCondition.class);
        register("HOTBARSLOT", HotbarSlotCondition.class);
        register("CHANCE", ChanceCondition.class);
        register("COOLDOWN", CooldownCondition.class);
        register("ENTITY", EntityWhitelistCondition.class);
        register("SIZE", SizeCondition.class);
        register("NOT", NOTCondition.class);
        register("SNEAK", SneakCondition.class);
        register("TIME", TimeCondition.class);
        register("ONGROUND", OnGroundCondition.class);
        register("GLIDING", GlidingCondition.class);
        register("BIOME", BiomeCondition.class);
        register("METADATA", MetadataCondition.class);
        register("NPC", NPCCondition.class);
        register("WEATHER", WeatherCondition.class);
        register("INBLOCK", InBlockCondition.class);
        register("WORLD", WorldCondition.class);
        register("TAMED", TamedCondition.class);
        register("FLY", FlyingCondition.class);
        register("FLYING", FlyingCondition.class);
        register("HEIGHT", HeightCondition.class);
        register("BLOCK", BlockCondition.class);
        register("ITEM", ItemCondition.class);
        register("OR", ORCondition.class);
        register("VISIBILITY", VisibilityCondition.class);
        register("LIGHT", LightCondition.class);
        register("TEMPERATURE", TemperatureCondition.class);
        register("SHIELDED", ShieldedCondition.class);
        register("ITEMWRAPPER", ItemWrapperCondition.class);
        register("DISTANCE", DistanceCondition.class);
        register("SWIMMING", SwimmingCondition.class);
        register("SPEED", SpeedCondition.class);
        register("SHOOTER", ShooterCondition.class);
        register("SPRINTING", SprintingCondition.class);
        register("POTIONEFFECT", PotionEffectCondition.class);
        register("PERMISSION", PermissionCondition.class);
    }

    public static void register(String name, Class<? extends Condition> triggerDataClass) {
        nameToCondition.put(name, new ConditionId(counter));
        conditionToClass.add(triggerDataClass);
        counter++;
    }

    public static Class<? extends Condition> getClass(int condition) {
        Class<? extends Condition> effectClass = conditionToClass.get(condition);
        return effectClass == null ? Condition.class : effectClass;
    }

    public static ConditionId getCondition(String name) {
        return nameToCondition.getOrDefault(name, null);
    }

    public static NamedType[] getNamedSubTypes() {
        return nameToCondition
                .entrySet()
                .stream()
                .map((entry) -> Map.entry(entry.getKey(), conditionToClass.get(entry.getValue().getId())))
                .map((entry) -> new NamedType(entry.getValue(), entry.getKey()))
                .toArray(NamedType[]::new);
    }

}
