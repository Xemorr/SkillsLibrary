package me.xemor.skillslibrary2;

public enum Mode {
    SELF, OTHER, LOCATION, ALL;

    public boolean runs(Mode target) {
        return this == Mode.ALL || this == target;
    }
}