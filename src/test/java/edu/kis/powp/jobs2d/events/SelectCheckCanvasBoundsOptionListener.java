package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.visitor.CanvasBoundsCheckingVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;

/**
 * Runs {@link CanvasBoundsCheckingVisitor} on the current command for a
 * given canvas and logs the result. Multiple instances can be registered
 * with different canvases (e.g. one for A4, one for B3).
 */
public class SelectCheckCanvasBoundsOptionListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final ICanvas canvas;

    public SelectCheckCanvasBoundsOptionListener(ICanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (command == null) {
            logger.info("No command loaded - nothing to check.");
            return;
        }

        CanvasBoundsCheckingVisitor visitor = new CanvasBoundsCheckingVisitor(canvas);
        command.accept(visitor);

        String commandName = CommandsFeature.getDriverCommandManager().getCurrentCommandString();
        if (!visitor.hasExceeded()) {
            logger.info("Command '" + commandName + "' fits within canvas '" + canvas.getName() + "'.");
        } else {
            logger.info("Command '" + commandName + "' EXCEEDS canvas '" + canvas.getName() + "': "
                    + "SetPosition out-of-bounds=" + visitor.getExceedingSetPositionCount()
                    + ", OperateTo out-of-bounds=" + visitor.getExceedingOperateToCount()
                    + ", total=" + visitor.getTotalExceedingCount());
        }
    }
}