package me.xemor.skillslibrary2.triggers;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.xemor.skillslibrary2.SkillsLibrary;

import java.util.HashMap;

public class Trigger {

    private static final HashMap<String, Integer> nameToTrigger = new HashMap<>();
    private static final Int2ObjectOpenHashMap<Class<? extends TriggerData>> triggerToData = new Int2ObjectOpenHashMap<>();
    private static int counter = 0;

    static {
        registerTrigger("DAMAGEDENTITY", TriggerData.class);
        registerTrigger("DAMAGEDBYENTITY", TriggerData.class);
        registerTrigger("DAMAGEDBYPROJECTILE", ProjectileData.class);
        registerTrigger("DAMAGEDENTITYWITHPROJECTILE", ProjectileData.class);
        registerTrigger("LAUNCHPROJECTILE", TriggerData.class);
        registerTrigger("PROJECTILEHIT", TriggerData.class);
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
        registerTrigger("ENTERVEHICLE", TriggerData.class);
        registerTrigger("EXITVEHICLE", TriggerData.class);
        registerTrigger("PLAYERJOIN", TriggerData.class);
        registerTrigger("PLAYERQUIT", TriggerData.class);
        registerTrigger("COMBAT", TriggerData.class);
        registerTrigger("PROJECTILECOMBAT", ProjectileData.class);
        registerTrigger("TAME", TriggerData.class);
        registerTrigger("INTERACT", InteractData.class);
        registerTrigger("PROJECTILEHIT", TriggerData.class);
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
