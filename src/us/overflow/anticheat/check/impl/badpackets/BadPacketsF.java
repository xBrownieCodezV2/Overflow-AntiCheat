package us.overflow.anticheat.check.impl.badpackets;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInArmAnimation;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "BadPackets (F)")
public final class BadPacketsF extends PacketCheck {
    private boolean sent;
    private long lastFlying, lastSwing;
    private double buffer;

    public BadPacketsF(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final long now = System.currentTimeMillis();

            if (sent) {
                final long postDelay = now - lastSwing;

                if (postDelay > 40L && postDelay < 100L) {
                    buffer += 0.25;

                    if (buffer > 0.5) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                } else {
                    buffer = Math.max(buffer - 0.025, 0);
                }

                this.sent = false;
            }

            this.lastFlying = now;
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final long now = System.currentTimeMillis();

            final long flyingDelay = now - lastFlying;

            if (flyingDelay < 10L) {
                this.sent = true;
                this.lastSwing = now;
            } else {
                buffer = Math.max(buffer - 0.025, 0);
            }
        }
    }
}
