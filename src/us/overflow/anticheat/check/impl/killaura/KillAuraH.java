package us.overflow.anticheat.check.impl.killaura;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "KillAura (H)")
public final class KillAuraH extends RotationCheck {
    private float lastDeltaPitch;
    private int buffer;

    public KillAuraH(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final long now = System.currentTimeMillis();

        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final boolean valid = !playerData.getCinematic().get() && deltaYaw > 3.0 && Math.abs(to.getPitch()) < 90.f;

        if (valid) {
            final boolean invalid = deltaPitch == lastDeltaPitch && now - playerData.getActionManager().getLastAttack() < 300L;

            if (invalid) {
                if (++buffer > 3) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();

                    buffer = 0;
                }
            } else {
                buffer = Math.max(buffer - 1, 0);
            }
        } else {
            buffer = Math.max(buffer - 1, 0);
        }

        this.lastDeltaPitch = deltaPitch;
    }
}
