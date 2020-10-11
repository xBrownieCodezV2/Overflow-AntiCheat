package us.overflow.anticheat.check.impl.aimassist;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "AimAssist (G)")
public final class AimAssistD extends RotationCheck {
    private int buffer;

    public AimAssistD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        if (deltaYaw > 0.0 && deltaPitch > 0.0) {
            final double sensitivity = playerData.getSensitivityX();
            final double deltaX = playerData.getMouseDeltaX();

            final float format = this.getPredictedRotation(to.getPitch(), sensitivity);
            final float length = String.valueOf(format).length();

            if ((format > 0.0 && length > 0 && length < 8) || format == 0.0f) {
                buffer += 20;
            }

            if (deltaX > 0.0 && buffer > 0.0) {
                buffer = Math.max(buffer - 1, 0);

                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }
    }

    private float getPredictedRotation(final float pitch, final double sensitivity) {
        float f = (float) (sensitivity * 0.6F + 0.2F);
        float f2 = f * f * f * 1.2F;

        return (pitch % f2);
    }
}
