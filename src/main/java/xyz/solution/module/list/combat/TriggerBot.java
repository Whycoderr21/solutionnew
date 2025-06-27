package xyz.solution.module.list.combat;

import com.google.common.eventbus.Subscribe;
import xyz.solution.event.list.EventUpdate;
import xyz.solution.module.Module;
import xyz.solution.module.ModuleCategory;
import xyz.solution.module.ModuleInformation;
import xyz.solution.module.settings.BooleanSetting;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Hand;

@SuppressWarnings("all")
@ModuleInformation(moduleName = "TriggerBot", moduleCategory = ModuleCategory.COMBAT)
public class TriggerBot extends Module {
    public final BooleanSetting pauseEating = new BooleanSetting("Pause If Eating", this, true);
    public final BooleanSetting onlyCriticals = new BooleanSetting("Only Criticals", this, true);
    public final BooleanSetting spaceOnly = new BooleanSetting("Space Only", this, false);

    private int delay;

    public TriggerBot() {
        addSetting(pauseEating);
        addSetting(onlyCriticals);
        addSetting(spaceOnly);
    }

    @Subscribe
    public void onEvent(EventUpdate e) {
        if (mc.player == null) return;

            if (mc.player.isUsingItem() && pauseEating.getValue()) {
                return;
            }

            if (delay > 0) {
                delay--;
                return;
            }

            if (!autoCrit()) return;

            Entity ent = mc.targetedEntity;
            if (ent != null) {
                mc.interactionManager.attackEntity(mc.player, ent);
                mc.player.swingHand(Hand.MAIN_HAND);
                delay = 10;
            }
    }

    @Override
    public void onDisable() {
        delay = 0;
        super.onDisable();
    }

    private boolean autoCrit() {
        boolean reasonForSkipCrit = !onlyCriticals.getValue()
                || mc.player.getAbilities().flying
                || mc.player.hasStatusEffect(StatusEffects.LEVITATION)
                || mc.player.hasStatusEffect(StatusEffects.BLINDNESS)
                || mc.world.getBlockState(mc.player.getBlockPos()).getBlock() == Blocks.LADDER;

        if (mc.player.getAttackCooldownProgress(0.5f) < (mc.player.isOnGround() ? 1f : 0.9f))
            return false;

        boolean mergeWithSpeed = mc.player.isOnGround();

        if (!mc.options.jumpKey.isPressed() && mergeWithSpeed && spaceOnly.getValue())
            return true;

        if (mc.player.isInLava())
            return true;

        if (!reasonForSkipCrit)
            return !mc.player.isOnGround() && mc.player.fallDistance > 0.0f;
        return true;
    }
}
