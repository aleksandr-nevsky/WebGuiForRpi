package cc.nevsky.j.webguiforrpi.core;

import cc.nevsky.j.webguiforrpi.sensormanagers.SensorForBedsideLight;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleksandr-nevsky
 */
public class AutoStartSensorForBedsideLight extends Thread {

    private static final Logger LOG = Logger.getLogger(AutoStartSensorForBedsideLight.class.getName());

    @Override
    public void run() {
        try {
            SensorForBedsideLight bedsideLight = SensorForBedsideLight.getInstance();
            bedsideLight.start();
        } catch (InterruptedException ex) {
            LOG.info("Interrupt thread SensorForBedsideLight");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "AutoStart run error", ex);
        }
    }

}
