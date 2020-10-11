package us.overflow.anticheat.check.impl.autotool;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInHeldItemSlot;

@CheckData(name = "AutoTool", threshold = 3)
public final class AutoTool extends PacketCheck {
    private int lastSlot;

    public AutoTool(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInHeldItemSlot) {
            final WrappedPacketPlayInHeldItemSlot wrapper = (WrappedPacketPlayInHeldItemSlot) packet;

            final int slot = wrapper.getSlot();

            if (slot == lastSlot) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
            }

            this.lastSlot = slot;
        }
    }
}
