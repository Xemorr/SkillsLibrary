package me.xemor.skillslibrary2.triggers;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import me.xemor.skillslibrary2.SkillsLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trigger {

    private static final HashMap<String, TriggerId> nameToTrigger = new HashMap<>();
    private static final List<Class<? extends TriggerData>> triggerToData = new ArrayList<>();
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
        registerTrigger("TARGETED", TriggerData.class);
        registerTrigger("VEHICLE", TriggerData.class);
        registerTrigger("ENTERVEHICLE", TriggerData.class);
        registerTrigger("BECOMEVEHICLE", TriggerData.class);
        registerTrigger("EXITVEHICLE", TriggerData.class);
        registerTrigger("PLAYERJOIN", TriggerData.class);
        registerTrigger("PLAYERQUIT", TriggerData.class);
        registerTrigger("COMBAT", TriggerData.class);
        registerTrigger("PROJECTILECOMBAT", ProjectileData.class);
        registerTrigger("TAME", TriggerData.class);
        registerTrigger("INTERACT", InteractData.class);
        registerTrigger("KILL", TriggerData.class);
        registerTrigger("POTIONEFFECT", PotionEffectTriggerData.class);
        registerTrigger("INTERACTENTITY", TriggerData.class);
        registerTrigger("RIPTIDE", TriggerData.class);
        registerTrigger("TOGGLESPRINT", TriggerData.class);
        registerTrigger("CHANGEMAINHAND", TriggerData.class);
        registerTrigger("EQUIPARMOR", TriggerData.class);
        registerTrigger("SPRINT", TriggerData.class);
        registerTrigger("BLOCKBREAK",TriggerData.class);
        registerTrigger("TOTEM", TriggerData.class);
        registerTrigger("CONSUME", TriggerData.class);
        registerTrigger("MOVE", TriggerData.class);
        registerTrigger("SWAPHANDS", TriggerData.class);
    }

    public static void registerTrigger(String name, Class<? extends TriggerData> effectDataClass) {
        nameToTrigger.put(name, new TriggerId(counter));
        triggerToData.add(effectDataClass);
        counter++;
    }

    public static Class<? extends TriggerData> getClass(TriggerId trigger) {
        if (trigger == null) {SkillsLibrary.getInstance().getLogger().severe("There is an unregistered trigger somewhere!"); return TriggerData.class; }
        Class<? extends TriggerData> triggerClass = triggerToData.get(trigger.getId());
        return triggerClass == null ? TriggerData.class : triggerClass;
    }

    public static TriggerId getTrigger(String name) {
        TriggerId trigger = nameToTrigger.get(name);
        if (trigger == null) {
            SkillsLibrary.getInstance().getLogger().severe(name + " has not been registered as a trigger!");
        }
        return trigger;
    }

    public static NamedType[] getNamedSubTypes() {
        return nameToTrigger
                .entrySet()
                .stream()
                .map((entry) -> Map.entry(entry.getKey(), triggerToData.get(entry.getValue().getId())))
                .map((entry) -> new NamedType(entry.getValue(), entry.getKey()))
                .toArray(NamedType[]::new);
    }

}
