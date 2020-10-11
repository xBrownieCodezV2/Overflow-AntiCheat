package us.overflow.anticheat.check.impl.killaura;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInUseEntity;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;

@CheckData(name = "KillAura (A)", threshold = 7)
public final class KillAuraA extends PacketCheck {
    private boolean sent;
    private long lastFlying, lastAttack;
    private double buffer;

    public KillAuraA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final long now = System.currentTimeMillis();

            if (sent) {
                final long postDelay = now - lastAttack;

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
        } else if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            final long now = System.currentTimeMillis();

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK) {
                final long flyingDelay = now - lastFlying;

                if (flyingDelay < 10L) {
                    this.sent = true;
                    this.lastAttack = now;
                } else {
                    buffer = Math.max(buffer - 0.025, 0);
                }
            }
        }
    }
}
