package com.otraventana;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

public class OtraVentanaMod implements ClientModInitializer {

    private static final String TEXTO = "ESTAS EN OTRA VENTANA";

    @Override
    public void onInitializeClient() {
        // Dibuja el texto sobre el HUD (cuando estas dentro de un mundo)
        HudRenderCallback.EVENT.register((context, tickCounter) -> dibujarAviso(context));

        // Dibuja el texto sobre las pantallas (menu principal, pausa, etc.)
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) ->
                ScreenEvents.afterRender(screen).register((s, context, mouseX, mouseY, delta) -> {
                    // Dentro de un mundo el HUD ya lo dibuja, evitamos dibujarlo doble
                    if (client.world == null) {
                        dibujarAviso(context);
                    }
                })
        );
    }

    /** true cuando la ventana de Minecraft NO tiene el foco (minimizada, alt+tab, otra app, etc.) */
    private static boolean enOtraVentana(MinecraftClient client) {
        long handle = client.getWindow().getHandle();
        boolean sinFoco = GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_FOCUSED) == 0;
        boolean minimizada = GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_ICONIFIED) == 1;
        return sinFoco || minimizada;
    }

    private static void dibujarAviso(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null || !enOtraVentana(client)) {
            return;
        }

        TextRenderer font = client.textRenderer;
        int ancho = context.getScaledWindowWidth();
        int alto = context.getScaledWindowHeight();
        int anchoTexto = font.getWidth(TEXTO);

        int x = ancho - anchoTexto - 8;   // esquina inferior derecha
        int y = alto - font.fontHeight - 8;

        // Fondo semitransparente para que se lea bien
        context.fill(x - 4, y - 4, x + anchoTexto + 4, y + font.fontHeight + 3, 0xAA000000);
        context.drawTextWithShadow(font, TEXTO, x, y, 0xFFFF5555);
    }
}
