package us.overflow.anticheat.check.impl.killaura;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;
import us.overflow.anticheat.utils.MathUtil;

@CheckData(name = "KillAura (E)")
public final class KillAuraE extends RotationCheck {

    public KillAuraE(final PlayerData playerData) {
        super(playerData);
    }

    private float lastPitchDifference;

    private final double offset = Math.pow(2.0, 24.0);;

    private int verbose;

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

       final long gcd = MathUtil.gcd((long) (deltaPitch * offset), (long) (this.lastPitchDifference * offset));

       if (playerData.getCinematic().get()) {
           if (verbose > 0) verbose-=7;
       }

       if (to != from &&  Math.abs(to.getPitch() - from.getPitch()) > 0.0 && Math.abs(to.getPitch()) != 90.0f) {
           if (gcd < 131072L) {
               if (verbose > 9) {
                   this.handleViolation().addViolation(ViolationLevel.HIGH).create();
               }

               if (verbose < 20) verbose++;
           } else {
               if (verbose > 0) verbose--;
           }
       }

        this.lastPitchDifference = deltaPitch;
    }
}
