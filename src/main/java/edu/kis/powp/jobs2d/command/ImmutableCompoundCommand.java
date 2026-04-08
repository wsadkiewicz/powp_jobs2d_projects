package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class ImmutableCompoundCommand implements ICompoundCommand {
    private List<DriverCommand> commands;
    private String name;

    public ImmutableCompoundCommand(@Nullable String name, List<DriverCommand> commands) {
        if (name == null) {
            this.name = "ImmutableCompoundCommand";
        } else {
            this.name = name;
        }
        this.commands = commands;
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return null;
    }

    /**
     * Execute all commands in sequence on the given driver.
     *
     * @param driver the driver to execute commands on
     */
    @Override
    public void execute(Job2dDriver driver) {
        for(DriverCommand command: commands) {
            command.execute(driver);
        }
    }

    @Override
    public String toString() {
        if(name != null) {
            return name;
        }
        return "ImmutableCompoundCommand";
    }
}
