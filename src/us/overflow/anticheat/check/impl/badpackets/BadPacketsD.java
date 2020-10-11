package us.overflow.anticheat.check.impl.badpackets;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInEntityAction;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.packet.type.enums.EnumPlayerAction;

@CheckData(name = "BadPackets (D)")
public final class BadPacketsD extends PacketCheck {
    private int count;

    public BadPacketsD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInEntityAction) {
            final WrappedPacketPlayInEntityAction wrapper = (WrappedPacketPlayInEntityAction) packet;

            if (wrapper.getUseAction() == EnumPlayerAction.START_SPRINTING) {
                count++;
            } else if (wrapper.getUseAction() == EnumPlayerAction.STOP_SPRINTING) {
                count++;
            }
        } else if (packet instanceof WrappedPacketPlayInFlying) {
            final boolean invalid = count > 1 && !playerData.getConnectionManager().getDelayed().get();

            if (invalid) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }

            this.count = 0;
        }
    }
}
