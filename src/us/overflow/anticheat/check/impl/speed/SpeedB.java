package us.overflow.anticheat.check.impl.speed;

import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.data.type.PositionManager;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.MathUtil;

@CheckData(name = "Speed (B)")
public final class SpeedB extends PositionCheck {
    private double lastHorizontal;
    private int buffer, iceTicks;

    public SpeedB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final PositionManager positionManager = playerData.getPositionManager();

        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double horizontal = playerData.getVelocityManager().getMaxHorizontal();
        final double vertical = playerData.getVelocityManager().getMaxVertical();

        final double horizontalDistance = MathUtil.vectorDistance(from, to);

        if (getPlayerData().getPositionManager().getTouchingIce().get()) {
            if (iceTicks < 20) iceTicks+=5;
        } else {
            if (iceTicks > 0) iceTicks--;
        }

        if (iceTicks > 0 || positionManager.getTouchingGround().get() || positionManager.getTouchingClimbable().get() || positionManager.getTouchingIllegalBlocks().get() || positionManager.getTouchingHalfBlocks().get() || positionManager.getBelowBlocks().get()) {
            buffer = 0;
            return;
        }

        if (playerData.getVelocityManager().getMaxHorizontal() > 0.0 || playerData.getVelocityManager().getMaxVertical() > 0.0) {
            buffer = 0;
            return;
        }

        if (horizontalDistance > 0.005 && horizontal + vertical == 0.0) {
            double estimated = lastHorizontal * 0.91 + 0.02;

            if (playerData.getSprinting().get()) {
                estimated += 0.0063;
            }

            if (horizontalDistance - estimated > 1e-12 && horizontalDistance > 0.1 && estimated > 0.075) {
                if (++buffer > 4) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            } else {
                buffer = 0;
            }
        }

        this.lastHorizontal = horizontalDistance;
    }
}
