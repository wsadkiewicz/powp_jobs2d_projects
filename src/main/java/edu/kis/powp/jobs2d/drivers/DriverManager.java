package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private final RecordingDriver recordingDriver = new RecordingDriver(new LoggerDriver());

    public synchronized void setCurrentDriver(Job2dDriver driver) {
        recordingDriver.setTarget(driver);
    }

    public synchronized Job2dDriver getCurrentDriver() {
        return recordingDriver;
    }

    public synchronized RecordingDriver getRecordingDriver() {
        return recordingDriver;
    }

}
