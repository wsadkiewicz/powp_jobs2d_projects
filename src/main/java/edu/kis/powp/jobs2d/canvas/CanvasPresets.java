package edu.kis.powp.jobs2d.canvas;

/**
 * Factory for common paper-format canvases. Sizes are scaled to fit
 * the typical drawer panel coordinate range (a few hundred units per side).
 * Using approximate aspect ratios of real paper formats.
 */
public final class CanvasPresets {

    private static final int DEFAULT_MARGIN = 20;

    private CanvasPresets() {
        // utility class - no instances
    }

    /**
     * A4 paper format proportions (210x297 mm), scaled to fit the panel.
     */
    public static ICanvas a4() {
        return new RectangleCanvas("A4", 420, 594, DEFAULT_MARGIN);
    }

    /**
     * B3 paper format proportions (353x500 mm), scaled to fit the panel.
     */
    public static ICanvas b3() {
        return new RectangleCanvas("B3", 353, 500, DEFAULT_MARGIN);
    }

    /**
     * A5 paper format proportions (148x210 mm), scaled to fit the panel.
     */
    public static ICanvas a5() {
        return new RectangleCanvas("A5", 296, 420, DEFAULT_MARGIN);
    }

    /**
     * Circular custom shape for demonstrating non-rectangular canvases.
     */
    public static ICanvas circle() {
        return new CircleCanvas("Circle r=250", 250, 250, 250, DEFAULT_MARGIN);
    }
}