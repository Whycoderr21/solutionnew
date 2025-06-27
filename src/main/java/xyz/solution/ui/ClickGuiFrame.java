package xyz.solution.ui;

import com.google.common.base.Suppliers;
import xyz.solution.Solution;
import xyz.solution.module.Module;
import xyz.solution.module.ModuleCategory;
import xyz.solution.module.settings.BooleanSetting;
import xyz.solution.module.settings.ModeSetting;
import xyz.solution.module.settings.NumberSetting;
import xyz.solution.module.settings.Setting;
import xyz.solution.util.render.builders.Builder;
import xyz.solution.util.render.builders.states.QuadColorState;
import xyz.solution.util.render.builders.states.QuadRadiusState;
import xyz.solution.util.render.builders.states.SizeState;
import xyz.solution.util.render.helper.HoverUtil;
import xyz.solution.util.render.msdf.MsdfFont;
import xyz.solution.util.render.renderers.impl.BuiltBlur;
import xyz.solution.util.render.renderers.impl.BuiltText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class ClickGuiFrame extends Screen {

    public float x = 219, y = 100, width = 100, height = 20;
    private static final Color CATEGORY_COLOR = new Color(89, 255, 231);
    private static final Color ENABLED_COLOR = Color.WHITE;
    private static final Color DISABLED_COLOR = new Color(131, 131, 131);
    private static final Color SETTING_COLOR = new Color(200, 200, 255);

    private static final Supplier<MsdfFont> BIKO_FONT = Suppliers.memoize(() -> MsdfFont.builder().atlas("biko").data("biko").build());

    private Module selectedModule = null;

    public ClickGuiFrame() {
        super(Text.of("Frame"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        float currentX = x;

        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();

        for (ModuleCategory category : ModuleCategory.values()) {
            int moduleCount = Solution.getInstance().getModuleStorage().getCategory(category).size();
            float totalHeight = height + (moduleCount * (height + 5));

            if (selectedModule != null && selectedModule.getCategory() == category) {
                int settingCount = selectedModule.getSettings().size();
                totalHeight = height + ((settingCount + 1) * (height + 5));
            }

            BuiltBlur blur = Builder.blur()
                    .size(new SizeState(width, totalHeight))
                    .radius(new QuadRadiusState(4))
                    .blurRadius(12f)
                    .smoothness(1f)
                    .color(new QuadColorState(new Color(30,30,30,255)))
                    .build();
            blur.render(matrix, currentX, y);

            if (selectedModule != null && selectedModule.getCategory() == category) {
                BuiltText moduleNameText = Builder.text()
                        .text(selectedModule.getName())
                        .color(CATEGORY_COLOR)
                        .size(10f)
                        .font(BIKO_FONT.get())
                        .thickness(0.05F)
                        .build();
                moduleNameText.render(matrix, currentX + width / 2 - 44, y + 6);

                float elementY = y + height + 1.5f;
                BuiltText backText = Builder.text()
                        .text("← Back")
                        .color(ENABLED_COLOR)
                        .size(10f)
                        .font(BIKO_FONT.get())
                        .thickness(0.05F)
                        .build();
                backText.render(matrix, currentX + width / 2 - 42, elementY + 6);

                elementY += height + 5;
                for (Setting setting : selectedModule.getSettings()) {
                    String displayText = setting.getName() + ": " + setting.getValueAsString();
                    BuiltText settingText = Builder.text()
                            .text(displayText)
                            .color(SETTING_COLOR)
                            .size(10f)
                            .font(BIKO_FONT.get())
                            .thickness(0.05F)
                            .build();
                    settingText.render(matrix, currentX + width / 2 - 42, elementY + 6);

                    elementY += height + 5;
                }
            } else {
                BuiltText text = Builder.text()
                        .text(category.name())
                        .color(CATEGORY_COLOR)
                        .size(10f)
                        .font(BIKO_FONT.get())
                        .thickness(0.05F)
                        .build();
                text.render(matrix, currentX + width / 2 - 44, y + 6);

                float moduleY = y + height + 1.5f;
                for (Module module : Solution.getInstance().getModuleStorage().getCategory(category)) {
                    BuiltText text1 = Builder.text()
                            .text(module.getName())
                            .color(module.isEnabled() ? ENABLED_COLOR.getRGB() : DISABLED_COLOR.getRGB())
                            .size(10f)
                            .font(BIKO_FONT.get())
                            .thickness(0.05F)
                            .build();
                    text1.render(matrix, currentX + width / 2 - 42, moduleY + 6);

                    moduleY += height + 5;
                }
            }

            currentX += width + 5;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float currentX = x;

        for (ModuleCategory category : ModuleCategory.values()) {
            List<Module> modules = Solution.getInstance().getModuleStorage().getCategory(category);
            int moduleCount = modules.size();
            float totalHeight = height + (moduleCount * (height + 5)) + 5;

            if (selectedModule != null && selectedModule.getCategory() == category) {
                int settingCount = selectedModule.getSettings().size();
                totalHeight = height + ((settingCount + 1) * (height + 5)) + 5;
            }

            if (HoverUtil.isHovered(mouseX, mouseY, currentX, y, width, totalHeight)) {
                if (selectedModule != null && selectedModule.getCategory() == category) {
                    float elementY = y + height + 3;

                    if (HoverUtil.isHovered(mouseX, mouseY, currentX, elementY, width, height)) {
                        if (button == 0) {
                            selectedModule = null;
                            return true;
                        }
                    }
                    elementY += height + 5;

                    for (Setting setting : selectedModule.getSettings()) {
                        if (HoverUtil.isHovered(mouseX, mouseY, currentX, elementY, width, height)) {
                            if (button == 0) {
                                handleSettingClick(setting);
                                return true;
                            }
                        }
                        elementY += height + 5;
                    }
                } else {
                    float moduleY = y + height + 3;
                    for (Module module : modules) {
                        if (HoverUtil.isHovered(mouseX, mouseY, currentX, moduleY, width, height)) {
                            if (button == 0) {
                                module.toggle();
                                return true;
                            } else if (button == 1) {
                                selectedModule = module;
                                return true;
                            }
                        }
                        moduleY += height + 5;
                    }
                }
            }
            currentX += width + 5;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void handleSettingClick(Setting setting) {
        if (setting instanceof BooleanSetting) {
            BooleanSetting boolSetting = (BooleanSetting) setting;
            boolSetting.toggle();
        } else if (setting instanceof ModeSetting) {
            ModeSetting modeSetting = (ModeSetting) setting;
            modeSetting.cycle();
        } else if (setting instanceof NumberSetting) {
            NumberSetting numSetting = (NumberSetting) setting;
            numSetting.setValue(numSetting.getValue() + numSetting.getStep());
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}