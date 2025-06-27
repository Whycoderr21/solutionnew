package xyz.solution.util.chat;

import xyz.solution.util.MinecraftUtil;
import net.minecraft.text.Text;

public class ChatUtil {
    public static void send(String message) {
        if (MinecraftUtil.mc.player == null) return;

        MinecraftUtil.mc.player.sendMessage(Text.of("Exort§bWare §f» " + message), false);
    }
}
