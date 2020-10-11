package us.overflow.anticheat.command;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.OverflowPlugin;
import us.overflow.anticheat.command.impl.OverflowCommand;
import us.overflow.anticheat.command.type.AbstractCommand;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.LogUtil;

public final class CommandManager implements Startable {

    @Override
    public void start() {
        final OverflowPlugin plugin = OverflowAPI.INSTANCE.getPlugin();

        LogUtil.log("Attempting to register the commands...");

        new OverflowCommand(plugin);
    }
}
