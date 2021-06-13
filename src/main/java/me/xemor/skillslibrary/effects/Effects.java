package me.xemor.skillslibrary.effects;

import com.google.common.collect.HashBiMap;

import java.util.HashMap;

public class Effects {

    private static final HashBiMap<String, Integer> nameToEffect = HashBiMap.create();
    private static final HashMap<Integer, Class<? extends me.xemor.skillslibrary.effects.Effect>> effectToData = new HashMap<>();
    private static int counter = 0;

    static {
        registerEffect("DAMAGE", me.xemor.skillslibrary.effects.DamageEffect.class);
        registerEffect("AOE", me.xemor.skillslibrary.effects.AOE.class);
        registerEffect("VELOCITY", VelocityEffect.class);
        registerEffect("CANCEL", me.xemor.skillslibrary.effects.CancelEffect.class);
        registerEffect("GLIDING", me.xemor.skillslibrary.effects.GlidingEffect.class);
        registerEffect("WAIT", me.xemor.skillslibrary.effects.WaitEffect.class);
        registerEffect("POTION", me.xemor.skillslibrary.effects.Potion.class);
        registerEffect("WEB", me.xemor.skillslibrary.effects.WebEffect.class);
        registerEffect("ARROW", me.xemor.skillslibrary.effects.ArrowEffect.class);
        registerEffect("ATTRIBUTE", AttributeEffect.class);
        registerEffect("BREAKBLOCK", me.xemor.skillslibrary.effects.BreakBlockEffect.class);
        registerEffect("LAUNCH", me.xemor.skillslibrary.effects.LaunchEffect.class);
        registerEffect("MESSAGE", me.xemor.skillslibrary.effects.MessageEffect.class);
        registerEffect("PICKUP", me.xemor.skillslibrary.effects.Pickup.class);
        registerEffect("REPULSE", me.xemor.skillslibrary.effects.Repulse.class);
        registerEffect("RESIZE", me.xemor.skillslibrary.effects.ResizeEffect.class);
        registerEffect("SMITE", me.xemor.skillslibrary.effects.SmiteEffect.class);
        registerEffect("VELOCITY", VelocityEffect.class);
        registerEffect("FLING", VelocityEffect.class);
    }

    public static void registerEffect(String name, Class<? extends me.xemor.skillslibrary.effects.Effect> effectDataClass) {
        nameToEffect.put(name, counter);
        effectToData.put(counter, effectDataClass);
        counter++;
    }

    public static Class<? extends me.xemor.skillslibrary.effects.Effect> getClass(int effect) { return effectToData.getOrDefault(effect, me.xemor.skillslibrary.effects.Effect.class); }

    public static int getEffect(String name) {
        return nameToEffect.getOrDefault(name, -1);
    }

    public static String getName(int effect) {
        return nameToEffect.inverse().get(effect);
    }

}
