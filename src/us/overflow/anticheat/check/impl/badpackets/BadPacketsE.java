package us.overflow.anticheat.check.impl.badpackets;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "BadPackets (E)")
public final class BadPacketsE extends PacketCheck {

    public BadPacketsE(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            if (playerData.getActionManager().getTeleported().get()) {
                return;
            }

            if (wrapper.getY() < -999.1) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }
    }
}
