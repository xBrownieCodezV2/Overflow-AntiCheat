package us.overflow.anticheat.check.type;

import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;

public class RotationCheck extends Check<RotationUpdate> {

    public RotationCheck(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {

    }
}
