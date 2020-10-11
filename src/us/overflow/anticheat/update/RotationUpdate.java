package us.overflow.anticheat.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import us.overflow.anticheat.update.head.HeadRotation;

@AllArgsConstructor @Getter
public final class RotationUpdate {
    private final HeadRotation from, to;
}
