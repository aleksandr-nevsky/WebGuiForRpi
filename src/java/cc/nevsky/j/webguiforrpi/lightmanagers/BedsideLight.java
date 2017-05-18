package cc.nevsky.j.webguiforrpi.lightmanagers;

import static cc.nevsky.j.webguiforrpi.core.GpioUsage.GPIO;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.SoftPwm;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс управления подсветкой кровати. Яркость регулируется через ШИМ.
 * Использует Синглтон.
 *
 * @author aleksandr-nevsky
 */
public class BedsideLight implements Serializable {

    private static final long serialVersionUID = 1;
    private static volatile BedsideLight instance;
    private static final GpioPinDigitalOutput BEDSIDE_LIGHT_RELAY_PIN
            = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_25, "BedsideLightRelayPin");
    private static final int BEDSIDE_LIGHT_RELAY_PIN_ADDRESS = BEDSIDE_LIGHT_RELAY_PIN.getPin().getAddress();
    private int pwmValue = 0;
    private static final Logger LOG = Logger.getLogger(BedsideLight.class.getName());

    /**
     * В конструкторе создаётся ШИМ для управления яркостью.
     */
    private BedsideLight() {
        SoftPwm.softPwmCreate(BEDSIDE_LIGHT_RELAY_PIN_ADDRESS, 0, 100);
        LOG.log(Level.INFO, "BedsideLight constructor");
    }

    /**
     * Синглтон.
     *
     * @return инстанс.
     */
    public static BedsideLight getInstance() {
        if (instance == null) {
            synchronized (LedControl.class) {
                if (instance == null) {
                    instance = new BedsideLight();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @return значение ШИМ яркости.
     */
    public int getPwmValue() {
        return pwmValue;
    }

    /**
     * Устанавливает значение ШИМ яркости.
     *
     * @param pwmValue percent between 0 and 100
     */
    public void setPwmValue(int pwmValue) {
        this.pwmValue = pwmValue;
        SoftPwm.softPwmWrite(BEDSIDE_LIGHT_RELAY_PIN_ADDRESS, this.pwmValue);
        LOG.log(Level.INFO, "pwmValue: " + this.pwmValue);
    }

    /**
     * Завершающий метод класса. Выключает ШИМ. Выключает свет.
     */
    public void shutdown() {
        SoftPwm.softPwmStop(BEDSIDE_LIGHT_RELAY_PIN_ADDRESS);
        BEDSIDE_LIGHT_RELAY_PIN.setState(PinState.LOW);
        LOG.log(Level.INFO, "shutdown");
    }

}
