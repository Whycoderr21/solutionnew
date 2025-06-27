package xyz.solution.module;

import xyz.solution.Solution;
import xyz.solution.module.list.render.hud.Hud;
import xyz.solution.module.settings.Setting;
import xyz.solution.util.chat.ChatUtil;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private final String name, desc;
    private final ModuleCategory category;
    private int keybind;
    private boolean enabled;

    public final MinecraftClient mc = MinecraftClient.getInstance();

    private final List<Setting> settings = new ArrayList<>();

    public Module() {
        ModuleInformation information = getClass().getAnnotation(ModuleInformation.class);

        this.name = information.moduleName();
        this.desc = information.moduleDesc();
        this.category = information.moduleCategory();
        this.keybind = information.moduleKeybind();
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public Setting getSettingByName(String name) {
        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    public void onEnable() {
        Solution.getInstance().getEventBus().register(this);
    }

    public void onDisable() {
        Solution.getInstance().getEventBus().unregister(this);
    }

    public void toggle() {
        setEnabled(!isEnabled());

        if (Solution.getInstance().getModuleStorage().get(Hud.class).notification.getValue() && Solution.getInstance().getModuleStorage().get(Hud.class).isEnabled()) {
            if (enabled) {
                ChatUtil.send(getName() + " был включен.");
            } else {
                ChatUtil.send(getName() + " был выключен.");
            }
        }
    }
}
