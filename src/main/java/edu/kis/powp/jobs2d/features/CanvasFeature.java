package edu.kis.powp.jobs2d.features;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.canvas.PaperFormat;
import edu.kis.powp.jobs2d.canvas.TriangleFormat;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

public class CanvasFeature implements IFeature {
    private static ICanvas currentFormat;

    @Override
    public void setup(Application application) {
        setupCanvasPlugin(application);
    }

    @Override
    public String getName() {
        return "Canvas";
    }

    public static void setupCanvasPlugin(Application application) {
        application.addComponentMenu(CanvasFeature.class, "Canvas", 0);

        for (PaperFormat format : PaperFormat.values()) {
            application.addComponentMenuElement(CanvasFeature.class, format.getName(), event -> setCanvas(format));
        }

        for (TriangleFormat format : TriangleFormat.values()) {
            application.addComponentMenuElement(CanvasFeature.class, format.getName(), event -> setCanvas(format));
        }
    }

    public static void clearPanel() {
        DrawerFeature.getDrawerController().clearPanel();
        redrawCanvas(currentFormat, true);
    }

    public static void setCanvas(ICanvas format) {
        redrawCanvas(format, false);
    }

    public static ICanvas getCanvas() {
        return currentFormat;
    }

    private static void redrawCanvas(ICanvas format, boolean forceRedraw) {
        if (!forceRedraw && format == currentFormat) {
            return;
        }

        format.toCommand().execute(new LineDriverAdapter(DrawerFeature.getDrawerController(), LineFactory.getSpecialLine(), "Canvas Guides"));

        currentFormat = format;
    }
}
