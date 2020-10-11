package us.overflow.anticheat.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import us.overflow.anticheat.check.impl.aimassist.prediction.Prediction;
import us.overflow.anticheat.data.type.*;
import us.overflow.anticheat.update.box.PlayerPosition;
import us.overflow.anticheat.utils.EvictingList;

@Getter @Setter
public final class PlayerData {
    private final Player player;

    @Setter(AccessLevel.NONE)
    private final CheckManager checkManager = new CheckManager(this);

    @Setter(AccessLevel.NONE)
    private final ActionManager actionManager = new ActionManager(this);

    @Setter(AccessLevel.NONE)
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Setter(AccessLevel.NONE)
    private final VelocityManager velocityManager = new VelocityManager();

    @Setter(AccessLevel.NONE)
    private final PositionManager positionManager = new PositionManager();

    private final Observable<Boolean> alerts = new Observable<>(true);
    private final Observable<Boolean> cinematic = new Observable<>(false);
    private final Observable<Boolean> sprinting =  new Observable<>(false);
    private final Observable<Boolean> velocity = new Observable<>(false);

    private final EvictingList<PlayerPosition> locations = new EvictingList<>(10);

    private PlayerPosition playerPosition = new PlayerPosition(0, 0);

    private int standTicks, clientTicks;
    private float lastYaw, lastPitch;

    private double velocityX;
    private double velocityY;
    private double velocityZ;

    private double lastPosX, lastPosY, lastPosZ;

    private double sensitivityX = checkManager.getCheck(Prediction.class).sensitivityX;
    private double sensitivityY = checkManager.getCheck(Prediction.class).sensitivityY;

    private double mouseDeltaX = checkManager.getCheck(Prediction.class).deltaX;
    private double mouseDeltaY = checkManager.getCheck(Prediction.class).deltaY;

    private long lastJoin, lastAttackDamage, lastFallDamage, lastTeleport, lastUnknownTeleport;

    public PlayerData(final Player player) {
        this.player = player;
        this.lastJoin = System.currentTimeMillis();
    }
}
