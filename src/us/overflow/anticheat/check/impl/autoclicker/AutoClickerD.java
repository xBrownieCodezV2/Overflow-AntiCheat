package us.overflow.anticheat.check.impl.autoclicker;

import lombok.val;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInArmAnimation;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInBlockDig;
import us.overflow.anticheat.packet.type.enums.EnumPlayerDigType;

public final class AutoClickerD extends PacketCheck {
    private boolean dug;
    private int buffer;

    public AutoClickerD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInBlockDig) {
            final WrappedPacketPlayInBlockDig wrapper = (WrappedPacketPlayInBlockDig) packet;

            // Excuse the val I just got fucking bored
            val digType = wrapper.getDigType();

            if (digType == EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.dug = true;
            } else if (digType == EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (dug) {
                    if (++buffer > 9) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                } else {
                    buffer = Math.max(buffer - 3, 0);
                }
            }
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            //sent an armanimation between dig and stopped digging
            this.dug = false;
        }
    }
}
