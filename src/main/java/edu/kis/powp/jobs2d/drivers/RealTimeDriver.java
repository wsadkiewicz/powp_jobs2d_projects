package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import javax.swing.SwingUtilities;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealTimeDriver implements Job2dDriver {
    private final Job2dDriver innerDriver;
    private final int delayMilliseconds;

    private volatile int currentX;
    private volatile int currentY;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public RealTimeDriver(Job2dDriver innerDriver, int delayMilliseconds) {
        if (delayMilliseconds <= 0) {
            throw new IllegalArgumentException("Delay must be a positive integer (milliseconds)!");
        }

        this.innerDriver = innerDriver;
        this.delayMilliseconds = delayMilliseconds;
    }

    @Override
    public void operateTo(int x, int y) {
        int startX = currentX;
        int startY = currentY;

        currentX = x;
        currentY = y;

        executor.submit(() -> moveRealTime(startX, startY, x, y, true));
    }

    @Override
    public void setPosition(int x, int y) {
        int startX = currentX;
        int startY = currentY;

        currentX = x;
        currentY = y;

        executor.submit(() -> moveRealTime(startX, startY, x, y, false));
    }

    private void moveRealTime(int x0, int y0, int x1, int y1, boolean draw) {
        int x = x0;
        int y = y0;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int stepX = x0 < x1 ? 1 : -1;
        int stepY = y0 < y1 ? 1 : -1;

        int error = dx - dy;

        while (true) {
            int fx = x;
            int fy = y;

            if (draw) {
                SwingUtilities.invokeLater(() -> innerDriver.operateTo(fx, fy));
            } else {
                SwingUtilities.invokeLater(() -> innerDriver.setPosition(fx, fy));
            }

            if (x == x1 && y == y1) {
                break;
            }

            int error2 = 2 * error;

            if (error2 > -dy) {
                error -= dy;
                x += stepX;
            }
            if (error2 < dx) {
                error += dx;
                y += stepY;
            }

            if (delayMilliseconds > 0) {
                try {
                    Thread.sleep(delayMilliseconds);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Real-Time Driver (delay=" + delayMilliseconds + "ms)";
    }
}