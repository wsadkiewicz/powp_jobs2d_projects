package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.logger.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.transformations.*;
import edu.kis.powp.jobs2d.events.SelectLoadSecretCommandOptionListener;
import edu.kis.powp.jobs2d.events.SelectRunCurrentCommandOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigure2OptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     * 
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
                DriverFeature.getDriverManager());
        SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
                DriverFeature.getDriverManager());

        application.addTest("Figure Joe 1", selectTestFigureOptionListener);
        application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
    }

    /**
     * Setup test using driver commands in context.
     * 
     * @param application Application context.
     */
    private static void setupCommandTests(Application application) {
        application.addTest("Load secret command", new SelectLoadSecretCommandOptionListener());

        application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));

    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     * 
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        Job2dDriver TrackingLoggerDriver = new TrackingLoggerDriver();
        DriverFeature.addDriver("Tracking Logger driver", TrackingLoggerDriver);

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        Job2dDriver driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("Line Simulator", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special line Simulator", driver);
        DriverFeature.updateDriverInfo();

        // --- TESTY TRANSFORMACJI ---
        //Przygotowanie driver do poprawnego działania testów
        driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        // TEST 1: Powiększenie 2x
        CoordinateTransformer scale = new ScaleTransformer(2.0, 2.0);
        Job2dDriver scaledDriver = new TransformingDriver(driver, scale, "Transform: Scaled 2x");
        DriverFeature.addDriver("Transform: Scaled 2x", scaledDriver);

        // TEST 2: Pomniejszenie o połowę (żeby zmieścić wielkie figury z zadania 2 [cite: 2])
        CoordinateTransformer scaleDown = new ScaleTransformer(0.5, 0.5);
        Job2dDriver scaledDownDriver = new TransformingDriver(driver, scaleDown, "Transform: Scaled 0.5x");
        DriverFeature.addDriver("Transform: Scaled 0.5x", scaledDownDriver);

        // TEST 3: Odbicie lustrzane w pionie (Flip Y)
        CoordinateTransformer flip = new FlipTransformer(false, true);
        Job2dDriver flippedDriver = new TransformingDriver(driver, flip, "Transform: Flipped Y");
        DriverFeature.addDriver("Transform: Flipped Y", flippedDriver);

        // TEST 4: Obrót o 45 stopni
        CoordinateTransformer rotate = new RotateTransformer(45.0);
        Job2dDriver rotatedDriver = new TransformingDriver(driver, rotate, "Transform: Rotated 45 degrees");
        DriverFeature.addDriver("Transform: Rotated 45 degrees", rotatedDriver);

        // TEST 5: Kombinacja (Powiększenie 2x + Obrót 45 stopni)
        Job2dDriver scaledAndRotatedDriver = new TransformingDriver(scaledDriver, rotate, "Transform: Scaled 2x & Rotated 45");
        DriverFeature.addDriver("Transform: Scaled 2x & Rotated 45", scaledAndRotatedDriver);


    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
        application.addWindowComponent("Command Manager", commandManager);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
    }

    /**
     * Setup menu for adjusting logging settings.
     * 
     * @param application Application context.
     */
    private static void setupLogger(Application application) {

        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log",
                (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level",
                (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level",
                (ActionEvent e) -> logger.setLevel(Level.SEVERE));
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                DrawerFeature.setupDrawerPlugin(app);
                CommandsFeature.setupCommandManager();

                DriverFeature.setupDriverPlugin(app);
                setupDrivers(app);
                setupPresetTests(app);
                setupCommandTests(app);
                setupLogger(app);
                setupWindows(app);

                app.setVisibility(true);
            }
        });
    }

}
