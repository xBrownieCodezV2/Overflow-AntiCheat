package us.overflow.anticheat.check.type;

import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;

public class PositionCheck extends Check<PositionUpdate> {

    public PositionCheck(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(PositionUpdate positionUpdate) {

    }
}
