package xyz.solution;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import xyz.solution.event.list.EventKeyInput;
import xyz.solution.module.Module;
import xyz.solution.module.ModuleStorage;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;

public class Solution implements ModInitializer {

	private static Solution instance;

	private final EventBus eventBus;

	private final ModuleStorage moduleStorage;

	public Solution() {
		instance = this;

		eventBus = new EventBus();
		eventBus.register(this);

		moduleStorage = new ModuleStorage();
	}

	public static Solution getInstance() {
		return instance == null ? new Solution() : instance;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public ModuleStorage getModuleStorage() {
		return moduleStorage;
	}

	@Override
	public void onInitialize() {
		getModuleStorage().injectRegisterModules();
	}

	@Subscribe
	private void onModuleKeyPressed(EventKeyInput event) {
		for (Module module : getModuleStorage().getModules()) {
			if (event.getAction() == 1 && MinecraftClient.getInstance().currentScreen == null) {
				if (module.getKeybind() == event.getKey()) {
					module.toggle();
				}
			}
		}
	}
}