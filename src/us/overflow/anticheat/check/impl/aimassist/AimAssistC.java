package us.overflow.anticheat.check.impl.aimassist;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "AimAssist (C)")
public final class AimAssistC extends RotationCheck {

    public AimAssistC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final long now = System.currentTimeMillis();

        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        if (now - playerData.getLastJoin() < 1000L || playerData.getActionManager().getTeleported().get()) {
            return;
        }

        if (from == to) {
            this.handleViolation().addViolation(ViolationLevel.HIGH).create();
        }
    }
}
