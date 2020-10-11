package us.overflow.anticheat.check.impl.badpackets;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInUseEntity;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "BadPackets (A)", threshold = 7)
public final class BadPacketsA extends RotationCheck {

    public BadPacketsA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation to = rotationUpdate.getTo();
        final HeadRotation from = rotationUpdate.getFrom();

        if (Math.abs(to.getYaw() - from.getYaw()) > 1000.0) {
            this.handleViolation().addViolation(ViolationLevel.HIGH).create();
        }
    }
}
