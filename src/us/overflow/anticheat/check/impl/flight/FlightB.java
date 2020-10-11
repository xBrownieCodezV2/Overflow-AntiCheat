package us.overflow.anticheat.check.impl.flight;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.data.type.PositionManager;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "Flight (B)")
public final class FlightB extends PacketCheck {
    private int buffer;

    public FlightB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            final PositionManager positionManager = playerData.getPositionManager();

            final boolean clientGround = wrapper.isOnGround();
            final boolean serverGround = wrapper.getY() % 0.015625 == 0.0;

            final boolean illegal = !playerData.getPositionManager().getTouchingClimbable().get() && playerData.getPlayer().isInsideVehicle() || !positionManager.getTouchingAir().get() || positionManager.getTouchingLiquid().get() || positionManager.getTouchingFence().get() || positionManager.getTouchingClimbable().get() || positionManager.getTouchingIllegalBlocks().get() || playerData.getPositionManager().getTouchingHalfBlocks().get();

            if (clientGround != serverGround && !illegal) {
                if (++buffer > 5) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            } else {
                buffer = 0;
            }
        }
    }
}
