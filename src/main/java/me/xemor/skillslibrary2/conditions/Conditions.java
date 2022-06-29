package me.xemor.skillslibrary2.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Conditions {

    private static final HashMap<String, Integer> nameToCondition = new HashMap<>();
    private static final List<Class<? extends Condition>> conditionToClass = new ArrayList<>();
    private static int counter;

    static {
        register("HEALTH", HealthCondition.class);
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
        register("FLYING", FlyingCondition.class);
        register("HEIGHT", HeightCondition.class);
        register("BLOCK", BlockCondition.class);
        register("ITEM", ItemComparisonCondition.class);
        register("OR", ORCondition.class);
        register("VISIBILITY", VisibilityCondition.class);
        register("LIGHT", LightCondition.class);
        register("TEMPERATURE", TemperatureCondition.class);
        register("SHIELDED", ShieldedCondition.class);
    }

    public static void register(String name, Class<? extends Condition> triggerDataClass) {
        nameToCondition.put(name, counter);
        conditionToClass.add(triggerDataClass);
        counter++;
    }

    public static Class<? extends Condition> getClass(int condition) {
        Class<? extends Condition> effectClass = conditionToClass.get(condition);
        return effectClass == null ? Condition.class : effectClass;
    }

    public static int getCondition(String name) {
        return nameToCondition.getOrDefault(name, -1);
    }

}
