package us.overflow.anticheat.alert.type;

import lombok.Getter;

public enum ViolationLevel {
    EXPERIMENTAL(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    @Getter
    private final int level;

    ViolationLevel(final int level) {
        this.level = level;
    }
}
