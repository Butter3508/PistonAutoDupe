package org.butter.pistonautodupe.monitor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import org.butter.pistonautodupe.PistonAutoDupe;


public class PistonMonitor implements Monitor {
    @Override
    public void handlePacket(PistonAutoDupe autoDupe, Packet<?> packet, MinecraftClient client) {
        if (packet instanceof PlaySoundS2CPacket || packet instanceof PlaySoundFromEntityS2CPacket || packet instanceof PlaySoundIdS2CPacket) {
            String soundName;
            if (packet instanceof PlaySoundS2CPacket) {
                PlaySoundS2CPacket soundPacket = (PlaySoundS2CPacket) packet;
                soundName = soundPacket.getSound().getId().toString();
            } else if (packet instanceof PlaySoundIdS2CPacket) {
                PlaySoundIdS2CPacket soundPacket = (PlaySoundIdS2CPacket) packet;
                soundName = soundPacket.getSoundId().toString();
            } else {
                return;
            }

            if (soundName.equalsIgnoreCase(SoundEvents.BLOCK_PISTON_CONTRACT.getId().toString()) || soundName.equalsIgnoreCase("block.piston.contract") || soundName.equalsIgnoreCase("minecraft:block.piston.contract")) {
                autoDupe.attackFrame();
            }
        }
    }
}
