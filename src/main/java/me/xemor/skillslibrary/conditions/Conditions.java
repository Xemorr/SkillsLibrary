package me.xemor.skillslibrary.conditions;

import java.util.HashMap;

public class Conditions {

    private static final HashMap<String, Integer> nameToCondition = new HashMap<>();
    private static final HashMap<Integer, Class<? extends Condition>> conditionToClass = new HashMap<>();
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
    }

    public static void register(String name, Class<? extends Condition> triggerDataClass) {
        nameToCondition.put(name, counter);
        conditionToClass.put(counter, triggerDataClass);
        counter++;
    }

    public static Class<? extends Condition> getClass(int condition) {
        return conditionToClass.getOrDefault(condition, HealthCondition.class);
    }

    public static int getCondition(String name) {
        return nameToCondition.getOrDefault(name, -1);
    }

}
