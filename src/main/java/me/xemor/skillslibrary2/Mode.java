package me.xemor.skillslibrary2;

public enum Mode {
    SELF, OTHER, BLOCK, ALL;

    public boolean runs(Mode target) {
        return this == Mode.ALL || this == target;
    }
}