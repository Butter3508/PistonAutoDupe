package org.butter.pistonautodupe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.Packet;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class FabricPistonAutoDupe implements ModInitializer {
    private static int counter = 0;
    private static FabricPistonAutoDupe instance;
    private PistonAutoDupe autoDupe;
    private KeyBinding keyBinding;


    @Override
    public void onInitialize() {
        if (instance == null) instance = this;

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.piston_auto_dupe.dupe",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                "PistonAutoDupe")
        );

        this.autoDupe = new PistonAutoDupe(this);

        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }

    public void tick(MinecraftClient client) {
        if (keyBinding.wasPressed()) {
            counter++;
        }
        if (counter == 1) {
            assert client.player != null;
            client.player.sendMessage(
                    new LiteralText("&f[&6PAD&f] Dupe started.".replace("&", "§")),
                    false
            );

            autoDupe.doDupe();
            client.options.keyUse.setPressed(true);
        }
        if (counter == 2) {
            assert client.player != null;
            client.player.sendMessage(
                    new LiteralText("&f[&6PAD&f] Dupe stopped.".replace("&", "§")),
                    false
            );

            client.options.keyUse.setPressed(false);
        }
    }

    // Mixin callback for Sound
        public void handlePacket (Packet<?> packet){
            autoDupe.handlePacket(packet);
        }

        public static FabricPistonAutoDupe getInstance() {
            return instance;
        }

    }
