package us.overflow.anticheat.processor;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.processor.impl.MovementProcessor;
import us.overflow.anticheat.processor.impl.PacketProcessor;
import us.overflow.anticheat.processor.type.Processor;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.LogUtil;

import java.util.Collection;

public final class ProcessorManager implements Startable {
    // Better than using an array both performance-wise and in execution speed. Also very convenient in general.
    private final ClassToInstanceMap<Processor> processors;

    public ProcessorManager() {
        processors = new ImmutableClassToInstanceMap.Builder<Processor>()
                .put(PacketProcessor.class, new PacketProcessor())
                .put(MovementProcessor.class, new MovementProcessor())
                .build();
    }

    // Get a specific processor for pushing a packet/event, or do whatever action possible within said processor class
    public final <T extends Processor> T getProcessor(final Class<T> clazz) {
        return processors.getInstance(clazz);
    }

    // Get all processors we have stored
    public final Collection<Processor> getProcessors() {
        return processors.values();
    }

    @Override
    public void start() {
        LogUtil.log("Initializing Processor Manager...");
    }
}
