package org.butter.pistonautodupe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import org.butter.pistonautodupe.monitor.Monitor;
import org.butter.pistonautodupe.monitor.PistonMonitor;

import java.util.List;

public class PistonAutoDupe {
    private MinecraftClient client;
    private FabricPistonAutoDupe fabricPistonAutoDupe;
    private Monitor monitor;
    private boolean duping = false;

    public PistonAutoDupe(FabricPistonAutoDupe fabricPistonAutoDupe) {
        this.fabricPistonAutoDupe = fabricPistonAutoDupe;
        this.client = MinecraftClient.getInstance();
        setMonitor();
    }

    public void tick(MinecraftClient client) {
    }

    public void handlePacket(Packet<?> packet) {
        if (client.isPaused()) {
            return;
        }
        if (client.targetedEntity instanceof ItemFrameEntity) {
            monitor.handlePacket(this, packet, client);
        }

    }


    public void doDupe() {
        List<ItemFrameEntity> itemFrames;
        assert client.player != null;
        assert client.interactionManager != null;
        assert client.world != null;
        Box box = new Box(client.player.getPos().add(-3,-3,-3), client.player.getPos().add(3,3,3));
        itemFrames = client.world.getEntitiesByClass(ItemFrameEntity.class, box, itemFrameEntity -> true);
        if (itemFrames.isEmpty()) return;
        ItemFrameEntity itemFrame = itemFrames.get(0);

        client.interactionManager.interactEntity(client.player, itemFrame, Hand.MAIN_HAND);
    }

    public void attackFrame() {
        ClientPlayerInteractionManager c = MinecraftClient.getInstance().interactionManager;
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        ClientWorld w = MinecraftClient.getInstance().world;

        assert c != null;
        assert p != null;
        assert w != null;

        List<ItemFrameEntity> itemFrames;
        ItemFrameEntity itemFrame;
        Box box;

        box = new Box(p.getPos().add(-3,-3,-3), p.getPos().add(3,3,3));
        itemFrames = w.getEntitiesByClass(ItemFrameEntity.class, box, itemFrameEntity -> true);

        itemFrame = itemFrames.get(0);
        if (itemFrame.getHeldItemStack().getCount() > 0) {
            c.attackEntity(p, itemFrame);
        }

    }

    public void setMonitor() {
            monitor = new PistonMonitor();
    }
}
