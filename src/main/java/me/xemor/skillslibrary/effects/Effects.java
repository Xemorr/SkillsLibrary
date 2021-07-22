package me.xemor.skillslibrary.effects;

import com.google.common.collect.HashBiMap;
import org.bukkit.entity.Projectile;

import java.util.HashMap;

public class Effects {

    private static final HashBiMap<String, Integer> nameToEffect = HashBiMap.create();
    private static final HashMap<Integer, Class<? extends Effect>> effectToData = new HashMap<>();
    private static int counter = 0;

    static {
        registerEffect("DAMAGE", DamageEffect.class);
        registerEffect("AOE", AOE.class);
        registerEffect("VELOCITY", VelocityEffect.class);
        registerEffect("CANCEL", CancelEffect.class);
        registerEffect("GLIDING", GlidingEffect.class);
        registerEffect("WAIT", WaitEffect.class);
        registerEffect("POTION", Potion.class);
        registerEffect("WEB", WebEffect.class);
        registerEffect("ARROW", ArrowEffect.class);
        registerEffect("ATTRIBUTE", AttributeEffect.class);
        registerEffect("BREAKBLOCK", BreakBlockEffect.class);
        registerEffect("LAUNCH", LaunchEffect.class);
        registerEffect("MESSAGE", MessageEffect.class);
        registerEffect("PICKUP", Pickup.class);
        registerEffect("REPULSE", Repulse.class);
        registerEffect("RESIZE", ResizeEffect.class);
        registerEffect("SMITE", SmiteEffect.class);
        registerEffect("VELOCITY", VelocityEffect.class);
        registerEffect("FLING", VelocityEffect.class);
        registerEffect("METADATA", MetadataEffect.class);
        registerEffect("GIVEITEM", GiveItemEffect.class);
        registerEffect("SCRAMBLEINVENTORY", ScrambleInventoryEffect.class);
        registerEffect("RANDOMTELEPORT", RandomTeleportEffect.class);
        registerEffect("PROJECTILE", ProjectileEffect.class);
        registerEffect("SOUND", SoundEffect.class);
        registerEffect("COMMAND", CommandEffect.class);
        registerEffect("LUNGE", LungeEffect.class);
        registerEffect("NEAREST", NearestEffect.class);
        registerEffect("SHOOTER", ShooterEffect.class);
        registerEffect("KNOCKBACK", KnockbackEffect.class);
    }

    public static void registerEffect(String name, Class<? extends Effect> effectDataClass) {
        nameToEffect.put(name, counter);
        effectToData.put(counter, effectDataClass);
        counter++;
    }

    public static Class<? extends Effect> getClass(int effect) { return effectToData.getOrDefault(effect, Effect.class); }

    public static int getEffect(String name) {
        return nameToEffect.getOrDefault(name, -1);
    }

    public static String getName(int effect) {
        return nameToEffect.inverse().get(effect);
    }

}
