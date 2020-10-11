package us.overflow.anticheat.processor.type;

import us.overflow.anticheat.data.PlayerData;

public interface Processor<T> {
    // This will handle the processing of any inbound/outbound packets as well as events
    void process(final PlayerData playerData, final T t);
}
