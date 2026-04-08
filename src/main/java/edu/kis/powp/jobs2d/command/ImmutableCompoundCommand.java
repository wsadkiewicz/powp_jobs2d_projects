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

    public String getName() {
        return name;
    }

    public int getCommandCount() {
        return commands.size();
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return null;
    }

    @Override
    public void execute(Job2dDriver driver) {

    }
}
