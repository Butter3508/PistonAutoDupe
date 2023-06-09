package org.butter.pistonautodupe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.network.Packet;
import net.minecraft.text.Text;
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
                "Turn on dupe",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                "PistonAutoDupe")
        );

        this.autoDupe = new PistonAutoDupe(this);

        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }

    // Counter = 1 => Start dupe | Counter = 2 => stop dupe
    public void tick(MinecraftClient client) {
        if (keyBinding.wasPressed()) {
            counter++;
            handleDupe(client);
        }
    }

        public void handleDupe(MinecraftClient client) {
            assert client.player != null;
            if (counter == 1) {
                client.player.sendMessage(Text.literal("[&eButter&aDuper&f] Dupe &6started&f!".replace("&", "§")));
            }
            if (counter == 2) {
                counter = 0;
                client.player.sendMessage(Text.literal("[&eButter&aDuper&f] Dupe&c stopped&f!".replace("&", "§")));
            }
            if (client.targetedEntity instanceof ItemFrameEntity) {
                client.options.useKey.setPressed(isDuping());
            }
        }

    // Mixin callback for Sound
        public void handlePacket (Packet<?> packet){
            autoDupe.handlePacket(packet);
        }

        public static FabricPistonAutoDupe getInstance() {
            return instance;
        }

        public boolean isDuping() {
            if (counter == 1) {
                return true;
            }
            if (counter == 2) {
                return false;
            }
            return MinecraftClient.getInstance().mouse.wasRightButtonClicked();
        }

    }
