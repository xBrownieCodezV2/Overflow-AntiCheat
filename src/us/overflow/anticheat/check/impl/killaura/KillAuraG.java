package us.overflow.anticheat.check.impl.killaura;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInBlockDig;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInUseEntity;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;

@CheckData(name = "KillAura (G)")
public final class KillAuraG extends PacketCheck {

    public KillAuraG(final PlayerData playerData) {
        super(playerData);
    }

    private int lastBlockDig = -1;

    @Override
    public void process(WrappedPacket packet) {

        if (packet instanceof WrappedPacketPlayInBlockDig) {
            lastBlockDig = playerData.getClientTicks();
        }

        if (packet instanceof WrappedPacketPlayInUseEntity) {
            WrappedPacketPlayInUseEntity wrappedPacketPlayInUseEntity = (WrappedPacketPlayInUseEntity) packet;

            if (wrappedPacketPlayInUseEntity.getEntity() != null && wrappedPacketPlayInUseEntity.getUseAction() == EnumEntityUseAction.ATTACK && playerData.getClientTicks() == lastBlockDig) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
            }
        }
    }
}
