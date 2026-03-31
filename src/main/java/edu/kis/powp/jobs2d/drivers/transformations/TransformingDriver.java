package edu.kis.powp.jobs2d.drivers.transformations;

import edu.kis.powp.jobs2d.Job2dDriver;

public class TransformingDriver implements Job2dDriver {
    private final Job2dDriver innerDriver;
    private final CoordinateTransformer transformer;
    private final String name;

    public TransformingDriver(Job2dDriver innerDriver, CoordinateTransformer transformer, String name) {
        this.innerDriver = innerDriver;
        this.transformer = transformer;
        this.name = name;
    }

    @Override
    public void setPosition(int x, int y) {
        int[] newCoords = transformer.transform(x, y);
        innerDriver.setPosition(newCoords[0], newCoords[1]);
    }

    @Override
    public void operateTo(int x, int y) {
        int[] newCoords = transformer.transform(x, y);
        innerDriver.operateTo(newCoords[0], newCoords[1]);
    }

    @Override
    public String toString() {
        return name;
    }
}