package xyz.solution.module.list.render;

import xyz.solution.module.Module;
import xyz.solution.module.ModuleCategory;
import xyz.solution.module.ModuleInformation;
import xyz.solution.ui.ClickGuiFrame;
import org.lwjgl.glfw.GLFW;

@ModuleInformation(moduleName = "ClickGui", moduleCategory = ModuleCategory.RENDER, moduleKeybind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public class ClickGui extends Module {
    @Override
    public void onEnable() {
        mc.setScreen(new ClickGuiFrame());
        toggle();
    }
}
