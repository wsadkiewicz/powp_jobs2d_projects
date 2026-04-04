package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;
import edu.kis.powp.observer.Publisher;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 * Recording is provided by a RecordingDriver decorator which is built on top
 * of this manager (RecordingDriver's target is always the current driver).
 */
public class DriverManager {

    private Job2dDriver currentDriver = new LoggerDriver();
    private Publisher changePublisher = new Publisher();
    private final RecordingDriver recordingDriver = new RecordingDriver(currentDriver);

    /**
     * Set the driver as current. This updates the recording decorator target
     * and notifies subscribers about driver change.
     */
    public synchronized void setCurrentDriver(Job2dDriver driver) {
        this.currentDriver = driver;
        recordingDriver.setTarget(driver);
        changePublisher.notifyObservers();
    }

    /**
     * Return driver wrapped with recording decorator so callers get recording behavior transparently.
     */
    public synchronized Job2dDriver getCurrentDriver() {
        return recordingDriver;
    }

    /**
     * Access to the recording driver (to read/clear recorded commands, enable/disable).
     */
    public synchronized RecordingDriver getRecordingDriver() {
        return recordingDriver;
    }

    /**
     * Publisher for driver-change events.
     */
    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
