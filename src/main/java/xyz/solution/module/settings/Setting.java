package xyz.solution.module.settings;

import xyz.solution.module.Module;

public abstract class Setting {
    private final String name;
    private final Module parent;

    public Setting(String name, Module parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Module getParent() {
        return parent;
    }

    public abstract String getValueAsString();
    public abstract void setValueFromString(String value);
}