package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Effects {

    private static final HashBiMap<String, EffectId> nameToEffect = HashBiMap.create();
    private static final List<Class<? extends Effect>> effectToData = new ArrayList<>();
    private static int counter = 0;

    static {
        registerEffect("DAMAGE", DamageEffect.class);
        registerEffect("AOE", AOE.class);
        registerEffect("VELOCITY", VelocityEffect.class);
        registerEffect("CANCEL", CancelEffect.class);
        registerEffect("GLIDING", GlidingEffect.class);
        registerEffect("WAIT", WaitEffect.class);
        registerEffect("POTION", Potion.class);
        registerEffect("WEB", BlockEntityEffect.class);
        registerEffect("BLOCKENTITY", BlockEntityEffect.class);
        registerEffect("ARROW", ArrowEffect.class);
        registerEffect("ATTRIBUTE", AttributeEffect.class);
        registerEffect("LAUNCH", LaunchEffect.class);
        registerEffect("MESSAGE", MessageEffect.class);
        registerEffect("PICKUP", Pickup.class);
        registerEffect("REPULSE", RepulseEffect.class);
        registerEffect("RESIZE", ResizeEffect.class);
        registerEffect("SMITE", LightningEffect.class);
        registerEffect("LIGHTNING", LightningEffect.class);
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
        registerEffect("IGNITE", IgniteEffect.class);
        registerEffect("FIRE", IgniteEffect.class);
        registerEffect("LOSETARGET", LoseTargetEffect.class);
        registerEffect("TARGET", TargetEntityEffect.class);
        registerEffect("SWAP", SwapEffect.class);
        registerEffect("LOCATIONCUBE", LocationCubeEffect.class);
        registerEffect("PLACEBLOCK", PlaceBlockEffect.class);
        registerEffect("SPAWNENTITY", SpawnEffect.class);
        registerEffect("REMOVEPOTION", RemovePotionEffect.class);
        registerEffect("TELEPORT", TeleportEffect.class);
        registerEffect("RAYTRACE", RaytraceEffect.class);
        registerEffect("TIMER", TimerEffect.class);
        registerEffect("REMOVEENTITY", RemoveEntityEffect.class);
        registerEffect("ITEMSTACK", ItemStackWrapperEffect.class);
        registerEffect("ITEMAMOUNT", ItemAmountEffect.class);
        registerEffect("ITEMMATERIAL", ItemMaterialEffect.class);
        registerEffect("SWITCHEROO", SwitcherooWrapperEffect.class);
        registerEffect("LOCATIONOFFSET", LocationOffsetEffect.class);
        registerEffect("BONEMEAL", BonemealEffect.class);
        registerEffect("HUNGER", HungerEffect.class);
        registerEffect("VEINMINER",VeinMinerEffect.class);
        registerEffect("ACTIONBAR", ActionBarEffect.class);
        registerEffect("FREEZE", FreezeEffect.class);
        registerEffect("HEALTH", HealthEffect.class);
        registerEffect("FLY", FlyEffect.class);
        registerEffect("FLYING", FlyEffect.class);
        registerEffect("FURNACEBURNTIME", FurnaceBurnTimeEffect.class);
        registerEffect("PARTICLE", ParticleEffect.class);
    }

    public static void registerEffect(String name, Class<? extends Effect> effectDataClass) {
        nameToEffect.put(name, new EffectId(counter));
        effectToData.add(effectDataClass);
        counter++;
    }

    public static Class<? extends Effect> getClass(EffectId effectId) {
        Class<? extends Effect> effectClass = effectToData.get(effectId.getId());
        return effectClass == null ? Effect.class : effectClass;
    }

    public static EffectId getEffect(String name) {
        return nameToEffect.getOrDefault(name, null);
    }

    public static String getName(EffectId effect) {
        return nameToEffect.inverse().get(effect);
    }

    public static NamedType[] getNamedSubTypes() {
        return nameToEffect
                .entrySet()
                .stream()
                .map((entry) -> Map.entry(entry.getKey(), effectToData.get(entry.getValue().getId())))
                .map((entry) -> new NamedType(entry.getValue(), entry.getKey()))
                .toArray(NamedType[]::new);
    }

}
