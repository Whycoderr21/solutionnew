package xyz.solution.mixin;

import xyz.solution.Solution;
import xyz.solution.event.list.EventWorldRender;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "HEAD"))
    private void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        Solution.getInstance().getEventBus().post(new EventWorldRender(tickCounter, tick));
    }
}
