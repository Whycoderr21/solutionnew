package xyz.solution.module.list.movement;

import com.google.common.eventbus.Subscribe;
import xyz.solution.event.list.EventUpdate;
import xyz.solution.module.Module;
import xyz.solution.module.ModuleCategory;
import xyz.solution.module.ModuleInformation;

@ModuleInformation(moduleName = "HighJump", moduleCategory = ModuleCategory.MOVEMENT)
public class HighJump extends Module {
    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (mc.player == null) return;

        if (mc.player.age % 4 == 0) {
            mc.player.jump();
            mc.player.setVelocity(0, 3, 0);
            toggle();
        }
    }
}
