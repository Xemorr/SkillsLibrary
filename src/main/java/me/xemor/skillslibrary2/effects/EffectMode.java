package me.xemor.skillslibrary2.effects;

public enum EffectMode {
    SELF, OTHER, BLOCK, ALL;

    public boolean runs(EffectMode target) {
        return this == EffectMode.ALL || this == target;
    }
}