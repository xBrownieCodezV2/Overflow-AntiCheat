package us.overflow.anticheat.check.impl.flight;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.ReflectionUtil;

@CheckData(name = "Flight (D)")
public final class FlightD extends PositionCheck {
    private int airTicks, horizontalBuffer, hoverBuffer;

    public FlightD(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get() && !playerData.getPositionManager().getTouchingClimbable().get();
        final boolean properMotion = ReflectionUtil.getMotionY(playerData) > 0.0;

        if (playerData.getVelocityManager().getMaxHorizontal() > 0.0 || playerData.getVelocityManager().getMaxVertical() > 0.0) {
            return;
        }

        if (touchingAir && properMotion) {
            ++airTicks;

            final double deltaY = to.getY() - from.getY();
            final double horizontalDistance = MathUtil.vectorDistance(from, to);

            if (airTicks > 8 && deltaY >= 0.0 && horizontalDistance > 0.15) {
                if (++horizontalBuffer > 10) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            } else {
                horizontalBuffer = 0;
            }

            if (airTicks > 9 && deltaY == 0.0) {
                if (++hoverBuffer > 9) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            } else {
                hoverBuffer = 0;
            }
        } else {
            hoverBuffer = 0;
            horizontalBuffer = 0;

            airTicks = 0;
        }
    }
}
