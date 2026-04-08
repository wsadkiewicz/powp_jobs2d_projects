package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class ImmutableCompoundCommand implements ICompoundCommand {
    private List<DriverCommand> commands;
    private String name;

    /**
     * Create immutable compound command with sequence of commands.
     * @param name      name of the compound command, optional
     * @param commands  list of the commands
     */
    public ImmutableCompoundCommand(@Nullable String name, List<DriverCommand> commands) {
        if (name == null) {
            this.name = "ImmutableCompoundCommand";
        } else {
            this.name = name;
        }
        this.commands = commands;
    }

    /**
     * Get the name of this compound command.
     *
     * @return name of the command, or null if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Get the number of commands in this compound command.
     *
     * @return number of commands
     */
    public int getCommandCount() {
        return commands.size();
    }

    /**
     * Get iterator over commands.
     *
     * @return iterator over commands
     */
    @Override
    public Iterator<DriverCommand> iterator() {
        return commands.iterator();
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
