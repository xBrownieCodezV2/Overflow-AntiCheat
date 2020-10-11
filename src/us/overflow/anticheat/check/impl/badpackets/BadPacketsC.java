package us.overflow.anticheat.check.impl.badpackets;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInBlockDig;
import us.overflow.anticheat.packet.type.enums.EnumPlayerDigType;

@CheckData(name = "BadPackets (C)")
public final class BadPacketsC extends PacketCheck {
    private long lastReleaseUseItem;

    public BadPacketsC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInBlockDig) {
            final WrappedPacketPlayInBlockDig wrapper = (WrappedPacketPlayInBlockDig) packet;

            final EnumPlayerDigType digType = wrapper.getDigType();

            if (digType == EnumPlayerDigType.RELEASE_USE_ITEM) {
                final long now = System.currentTimeMillis();

                if (playerData.getConnectionManager().getDelayed().get()) {
                    return;
                }

                if (now - lastReleaseUseItem < 40L) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }

                this.lastReleaseUseItem = now;
            }
        }
    }
}
