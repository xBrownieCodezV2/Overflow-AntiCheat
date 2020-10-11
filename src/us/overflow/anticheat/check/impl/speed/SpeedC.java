package us.overflow.anticheat.check.impl.speed;

import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.data.type.PositionManager;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.MathUtil;

@CheckData(name = "Speed (C)")
public final class SpeedC extends PositionCheck {
    private double lastHorizontal;
    private int buffer;

    public SpeedC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final PositionManager positionManager = playerData.getPositionManager();

        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double horizontalDistance = MathUtil.vectorDistance(from, to);

        if ((System.currentTimeMillis() - playerData.getLastUnknownTeleport()) < 1000L || (getPlayerData().getClientTicks()) < 100 || positionManager.getTouchingClimbable().get() || positionManager.getTouchingIllegalBlocks().get() || positionManager.getTouchingHalfBlocks().get() || positionManager.getBelowBlocks().get()) {
            return;
        }

        if (horizontalDistance >= 1) {
            this.handleViolation().addViolation(ViolationLevel.HIGH).create();
        }
    }
}
