package me.xemor.skillslibrary.triggers;

import java.util.HashMap;

public class Trigger {

    private static final HashMap<String, Integer> nameToTrigger = new HashMap<>();
    private static final HashMap<Integer, Class<? extends TriggerData>> triggerToData = new HashMap<>();
    private static int counter = 0;

    static {
        registerTrigger("DAMAGEDENTITY", TriggerData.class);
        registerTrigger("DAMAGEDBYENTITY", TriggerData.class);
        registerTrigger("SNEAK", TriggerData.class);
        registerTrigger("TOGGLEGLIDE", TriggerData.class);
        registerTrigger("PLAYERJUMP", TriggerData.class);
        registerTrigger("LOOP", LoopData.class);
        registerTrigger("COMBAT", TriggerData.class);
        registerTrigger("DEATH", TriggerData.class);
        registerTrigger("SPAWN", TriggerData.class);
        registerTrigger("TARGET", TriggerData.class);
        registerTrigger("VEHICLE", TriggerData.class);
        registerTrigger("PLAYERJOIN", TriggerData.class);
        registerTrigger("PLAYERQUIT", TriggerData.class);
    }

    public static void registerTrigger(String name, Class<? extends TriggerData> effectDataClass) {
        nameToTrigger.put(name, counter);
        triggerToData.put(counter, effectDataClass);
        counter++;
    }

    public static Class<? extends TriggerData> getClass(int trigger) { return triggerToData.getOrDefault(trigger, TriggerData.class); }

    public static int getTrigger(String name) {
        return nameToTrigger.getOrDefault(name, -1);
    }

}
