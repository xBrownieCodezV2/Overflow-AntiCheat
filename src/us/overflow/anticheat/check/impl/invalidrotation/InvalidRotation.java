package us.overflow.anticheat.check.impl.invalidrotation;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "InvalidRotation")
public final class InvalidRotation extends RotationCheck {

    public InvalidRotation(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float rotationPitch = Math.max(from.getPitch(), to.getPitch());

        if (Math.abs(rotationPitch) > 0.0 && !Double.isNaN(rotationPitch)) {
            final double threshold = playerData.getPositionManager().getTouchingClimbable().get() ? 90.11f : 90.f;

            if (Math.abs(rotationPitch) > threshold) {
                this.handleViolation().addViolation(ViolationLevel.HIGH).create();
            }
        }
    }
}
