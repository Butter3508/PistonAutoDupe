package org.butter.pistonautodupe.monitor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import org.butter.pistonautodupe.PistonAutoDupe;

public interface Monitor {
    void handlePacket(PistonAutoDupe autoDupe, Packet<?> packet, MinecraftClient client);
}
