package xyz.solution.module.settings;

import xyz.solution.module.Module;

public class BooleanSetting extends Setting {
    private boolean value;

    public BooleanSetting(String name, Module parent, boolean defaultValue) {
        super(name, parent);
        this.value = defaultValue;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return Boolean.toString(value);
    }

    @Override
    public void setValueFromString(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    public void toggle() {
        this.value = !this.value;
    }
}