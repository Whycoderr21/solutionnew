package xyz.solution.module;

import xyz.solution.module.list.combat.FightAura;
import xyz.solution.module.list.combat.TriggerBot;
import xyz.solution.module.list.movement.Flight;
import xyz.solution.module.list.movement.HighJump;
import xyz.solution.module.list.movement.Sprint;
import xyz.solution.module.list.player.FakePlayer;
import xyz.solution.module.list.render.ClickGui;
import xyz.solution.module.list.render.FullBright;
import xyz.solution.module.list.render.NoRender;
import xyz.solution.module.list.render.hud.Hud;

import java.util.ArrayList;
import java.util.List;

public class ModuleStorage {
    private final List<Module> modules = new ArrayList<>();

    public void injectRegisterModules() {
        modules.addAll(List.of(
                new FullBright(),
                new ClickGui(),
                new Sprint(),
                new FakePlayer(),
                new Hud(),
                new TriggerBot(),
                new HighJump(),
                new NoRender(),
                new Flight(),
                new FightAura()
        ));
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getCategory(ModuleCategory category) {
        return modules.stream()
                .filter(module -> module.getCategory().equals(category))
                .toList();
    }

    public <T extends Module> T get(final Class<T> clazz) {
        return modules.stream()
                .filter(module -> clazz.isAssignableFrom(module.getClass()))
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }
}
