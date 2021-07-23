package me.xemor.skillslibrary.triggers;

import me.xemor.skillslibrary.SkillsLibrary;

import java.util.HashMap;

public class Trigger {

    private static final HashMap<String, Integer> nameToTrigger = new HashMap<>();
    private static final HashMap<Integer, Class<? extends TriggerData>> triggerToData = new HashMap<>();
    private static int counter = 0;

    static {
        registerTrigger("DAMAGEDENTITY", TriggerData.class);
        registerTrigger("DAMAGEDBYENTITY", TriggerData.class);
        registerTrigger("DAMAGED", DamageData.class);
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
        registerTrigger("COMBAT", TriggerData.class);
        registerTrigger("TAME", TriggerData.class);
        registerTrigger("INTERACT", InteractData.class);
    }

    public static void registerTrigger(String name, Class<? extends TriggerData> effectDataClass) {
        nameToTrigger.put(name, counter);
        triggerToData.put(counter, effectDataClass);
        counter++;
    }

    public static Class<? extends TriggerData> getClass(int trigger) { return triggerToData.getOrDefault(trigger, TriggerData.class); }

    public static int getTrigger(String name) {
        int trigger = nameToTrigger.getOrDefault(name, -1);
        if (trigger == -1) {
            SkillsLibrary.getInstance().getLogger().severe(name + " has not been registered as a trigger!");
        }
        return trigger;
    }

}
