package me.xemor.skillslibrary.effects;

public enum EffectMode {
    SELF, OTHER, BLOCK, ALL;

    public boolean runs(EffectMode target) {
        return this == EffectMode.ALL || this == target;
    }
}