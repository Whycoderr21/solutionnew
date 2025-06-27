package xyz.solution.module.list.render.hud;

import com.google.common.base.Suppliers;
import com.google.common.eventbus.Subscribe;
import xyz.solution.event.list.EventHUD;
import xyz.solution.module.Module;
import xyz.solution.module.ModuleCategory;
import xyz.solution.module.ModuleInformation;
import xyz.solution.module.settings.BooleanSetting;
import xyz.solution.util.render.builders.Builder;
import xyz.solution.util.render.builders.states.QuadColorState;
import xyz.solution.util.render.builders.states.QuadRadiusState;
import xyz.solution.util.render.builders.states.SizeState;
import xyz.solution.util.render.msdf.MsdfFont;
import xyz.solution.util.render.renderers.impl.BuiltBlur;
import xyz.solution.util.render.renderers.impl.BuiltText;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.function.Supplier;

@ModuleInformation(moduleName = "Hud", moduleCategory = ModuleCategory.RENDER)
public class Hud extends Module {

    public BooleanSetting watermark = new BooleanSetting("Watermark", this, true);
    public BooleanSetting coordinates = new BooleanSetting("CoordsInfo", this, false);
    public BooleanSetting notification = new BooleanSetting("Notification", this, false);

    private static final Supplier<MsdfFont> BIKO_FONT = Suppliers.memoize(() -> MsdfFont.builder().atlas("biko").data("biko").build());

    public Hud() {
        addSetting(watermark);
        addSetting(coordinates);
        addSetting(notification);
    }

    @Subscribe
    public void onEventHUD(EventHUD event) {
        if (watermark.getValue()) {
            renderWatermark(event.getDrawContext());
        }
        if (coordinates.getValue()) {
            renderCoordsInfo(event.getDrawContext());
        }
    }

    private void renderWatermark(DrawContext context) {
        if (mc.player == null) return;

        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();

        String watermarkTitle = "Solution  >  " + mc.player.getName().getString() + "  >  " + mc.getCurrentFps() + " fps";

        BuiltBlur blur = Builder.blur()
                .size(new SizeState(BIKO_FONT.get().getWidth(watermarkTitle, 10) + 15, 20))
                .color(new QuadColorState(new Color(30, 30, 30, 255)))
                .radius(new QuadRadiusState(4))
                .smoothness(1f)
                .blurRadius(6)
                .build();
        blur.render(matrix, 5, 5);

        BuiltText text = Builder.text()
                .font(BIKO_FONT.get())
                .size(10)
                .text(watermarkTitle)
                .thickness(0.02f)
                .color(-1)
                .build();
        text.render(matrix, 11, 10.5f);
    }

    private void renderCoordsInfo(DrawContext context) {
        if (mc.player == null) return;

        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();

        float x = Math.round(mc.player.getPos().getX());
        float y = Math.round(mc.player.getPos().getY());
        float z = Math.round(mc.player.getPos().getZ());

        String coordsTitle = "X: " + x + ", Y: " + y + ", Z: " + z;

        BuiltBlur blur = Builder.blur()
                .size(new SizeState(BIKO_FONT.get().getWidth(coordsTitle, 10) + 15, 20))
                .color(new QuadColorState(new Color(30, 30, 30, 255)))
                .radius(new QuadRadiusState(4))
                .smoothness(1f)
                .blurRadius(6)
                .build();
        blur.render(matrix, 5, 5 + 510);

        BuiltText text = Builder.text()
                .font(BIKO_FONT.get())
                .size(10)
                .text(coordsTitle)
                .thickness(0.02f)
                .color(-1)
                .build();
        text.render(matrix, 11, 10.5f + 510);
    }
}
