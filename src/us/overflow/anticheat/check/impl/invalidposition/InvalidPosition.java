package us.overflow.anticheat.check.impl.invalidposition;

import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.ReflectionUtil;

import java.sql.Ref;

@CheckData(name = "InvalidPosition")
public final class InvalidPosition extends PositionCheck {

    public InvalidPosition(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double horizontalDistance = MathUtil.vectorDistance(from, to);

        if (horizontalDistance > 1e-12 && horizontalDistance < 0.00089) {
            final double velocityV = playerData.getVelocityManager().getMaxVertical();
            final double velocityH = playerData.getVelocityManager().getMaxHorizontal();

            final double motionX = ReflectionUtil.getMotionX(playerData);
            final double motionY = ReflectionUtil.getMotionY(playerData);
            final double motionZ = ReflectionUtil.getMotionZ(playerData);

            if (velocityV == Math.abs(motionY) && Math.hypot(motionX, motionZ) == velocityH) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        }
    }
}
