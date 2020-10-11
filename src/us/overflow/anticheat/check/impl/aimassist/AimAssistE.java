package us.overflow.anticheat.check.impl.aimassist;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "AimAssist (E)")
public final class AimAssistE extends RotationCheck {
    private int buffer;

    public AimAssistE(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaPitch % 1.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        } else {
            buffer = 0;
        }

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaYaw % 1.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        } else {
            buffer = 0;
        }

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaPitch % 10.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }

        if (deltaYaw > 1.0 && deltaPitch > 0.0 && deltaYaw % 10.0 == 0.0) {
            buffer += 5;

            if (buffer > 15) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }
    }
}
