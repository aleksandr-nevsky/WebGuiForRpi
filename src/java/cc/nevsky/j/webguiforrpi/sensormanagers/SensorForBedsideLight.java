package cc.nevsky.j.webguiforrpi.sensormanagers;

import static cc.nevsky.j.webguiforrpi.core.GpioUsage.GPIO;
import cc.nevsky.j.webguiforrpi.lightmanagers.BedsideLight;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс отвечающий за обработку событий от датчик-переключателя подсветки
 * кровати.
 *
 * @author aleksandr-nevsky
 */
public class SensorForBedsideLight {

    private static final GpioPinDigitalInput SENSOR_INPUT_PIN
            = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_26, "inputButtonPin");

    private static final Logger LOG = Logger.getLogger(SensorForBedsideLight.class.getName());

    private static volatile SensorForBedsideLight instance;

    /**
     * Прячем конструктор т.к. Синглтон.
     */
    private SensorForBedsideLight() {
        LOG.log(Level.INFO, "SensorForBedsideLight constructor");
    }

    /**
     * Синглтон.
     *
     * @return инстанс.
     */
    public static SensorForBedsideLight getInstance() {
        if (instance == null) {
            synchronized (SensorForBedsideLight.class) {
                if (instance == null) {
                    instance = new SensorForBedsideLight();
                }
            }
        }
        return instance;
    }

    /**
     * Главный метод класса. Мониторим датчик. При срабатывании датчика: если
     * включен - выключаем, если выключан - включаем на якрость 5.
     *
     * @throws InterruptedException прерывание треда во время sleep.
     */
    public void start() throws InterruptedException {
        int vaitingCounter = 0;
        BedsideLight bedsideLight = BedsideLight.getInstance();
        while (!Thread.interrupted()) {
            if (SENSOR_INPUT_PIN.isHigh() && vaitingCounter == 0) {
                if (bedsideLight.getPwmValue() == 0) {
                    bedsideLight.setPwmValue(5);
                    LOG.info("bedsideLight.setPwmValue(5);");
                    vaitingCounter = 10;
                } else {
                    bedsideLight.setPwmValue(0);
                    LOG.info("bedsideLight.setPwmValue(0);");
                    vaitingCounter = 10;
                }
            }
            Thread.sleep(500);
            if (vaitingCounter > 0) {
                vaitingCounter--;
                LOG.info("vaitingCounter: " + vaitingCounter);
            }
        }
    }

    private void waitAfterChangeState() throws InterruptedException {
        Thread.sleep(10000);
    }
}
