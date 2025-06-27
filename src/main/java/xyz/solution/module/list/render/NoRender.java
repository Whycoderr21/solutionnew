package xyz.solution.module.list.render;

import xyz.solution.module.Module;
import xyz.solution.module.ModuleCategory;
import xyz.solution.module.ModuleInformation;
import xyz.solution.module.settings.BooleanSetting;

@ModuleInformation(moduleName = "NoRender", moduleCategory = ModuleCategory.RENDER)
public class NoRender extends Module {

    public BooleanSetting fire = new BooleanSetting("Fire", this, false);
    public BooleanSetting water = new BooleanSetting("Water", this, false);
    public BooleanSetting wall = new BooleanSetting("Wall", this, false);

    public NoRender() {
        addSetting(fire);
        addSetting(water);
        addSetting(wall);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
