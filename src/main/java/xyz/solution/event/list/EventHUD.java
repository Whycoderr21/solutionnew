package xyz.solution.event.list;

import xyz.solution.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class EventHUD extends Event {
    private final DrawContext drawContext;
    private final RenderTickCounter renderTickCounter;

    public EventHUD(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        this.drawContext = drawContext;
        this.renderTickCounter = renderTickCounter;
    }

    public DrawContext getDrawContext() {
        return drawContext;
    }

    public RenderTickCounter getRenderTickCounter() {
        return renderTickCounter;
    }
}
