package edu.kis.powp.jobs2d.canvas;

/**
 * Rectangular canvas with optional margin. The drawable area is a rectangle
 * centred at the origin with the specified width and height, shrunk on all
 * sides by the margin.
 *
 * Coordinates are treated as in screen-style (origin at top-left of the
 * drawable area: 0..width on X, 0..height on Y).
 */
public class RectangleCanvas implements ICanvas {

    private final int width;
    private final int height;
    private final int margin;
    private final String name;

    /**
     * @param name   human-readable canvas name (e.g. "A4")
     * @param width  width in pixels (or generic units)
     * @param height height in the same units as width
     * @param margin margin applied on every side, in the same units
     */
    public RectangleCanvas(String name, int width, int height, int margin) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.margin = margin;
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= margin
                && x <= width - margin
                && y >= margin
                && y <= height - margin;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMargin() {
        return margin;
    }
}