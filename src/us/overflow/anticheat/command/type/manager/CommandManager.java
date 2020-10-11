package us.overflow.anticheat.command.type.manager;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.OverflowPlugin;
import us.overflow.anticheat.command.impl.OverflowCommand;
import us.overflow.anticheat.command.type.AbstractCommand;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.LogUtil;

import java.util.Collection;

public final class CommandManager implements Startable {

    private final OverflowPlugin plugin = OverflowAPI.INSTANCE.getPlugin();

    // This is where we will store our commands
    private final ClassToInstanceMap<AbstractCommand> commands = new ImmutableClassToInstanceMap.Builder<AbstractCommand>()
            .put(OverflowCommand.class, new OverflowCommand(plugin))
            .build();

    @Override
    public void start() {
        LogUtil.log("Successfully started the command manager");
    }

    // Get all the registered commands
    public final Collection<AbstractCommand> getCommands() {
        return commands.values();
    }

    // Get a specific command class
    public final <T extends AbstractCommand> T getCommand(final Class<T> clazz) {
        return commands.getInstance(clazz);
    }
}
