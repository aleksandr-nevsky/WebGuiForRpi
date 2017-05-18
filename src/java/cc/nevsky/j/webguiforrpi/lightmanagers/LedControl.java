package cc.nevsky.j.webguiforrpi.lightmanagers;

import cc.nevsky.j.webguiforrpi.timemanagers.DateAndTimeForTurningOnLed;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.wiringpi.SoftPwm;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import java.io.Serializable;
import static cc.nevsky.j.webguiforrpi.core.GpioUsage.GPIO;

/**
 * Класс управления светодиодной лентой. Яркость регулируется через ШИМ.
 * Использует Синглтон.
 *
 * @author aleksandr-nevsky
 */
public class LedControl implements Serializable {

    private static final long serialVersionUID = 1;
    private static final GpioPinDigitalOutput STRIPLIGHT_RELAY_PIN
            = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_00, "stripLightRelayPin", PinState.LOW);
    private static final GpioPinDigitalOutput STRIPLIGHT_BUTTON_OUTPUT_PIN
            = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_28, "outputButtonPin", PinState.HIGH);
    private static final GpioPinDigitalInput STRIPLIGHT_BUTTON_INPUT_PIN
            = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_29, "inputButtonPin");
    private static final int STRIPLIGHT_RELAY_PIN_ADDRESS = STRIPLIGHT_RELAY_PIN.getPin().getAddress();

    private static final Logger LOG = Logger.getLogger(LedControl.class.getName());
    private final DateAndTimeForTurningOnLed dateAndTimeForTurningOnLed;
    private static volatile LedControl instance;

    /**
     * Создаётся инстанс класса управления временем подсветки.
     */
    private LedControl() {
        this.dateAndTimeForTurningOnLed = DateAndTimeForTurningOnLed.getInstance();
        LOG.log(Level.INFO, "LedControl constructor");
    }

    /**
     * Синглтон.
     *
     * @return инстанс.
     */
    public static LedControl getInstance() {
        if (instance == null) {
            synchronized (LedControl.class) {
                if (instance == null) {
                    instance = new LedControl();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @return true, если лента включена. Иначе false.
     */
    public boolean getStripLightPowerState() {
        return (STRIPLIGHT_RELAY_PIN.getState() == PinState.HIGH);
    }

    /**
     *
     * @param state true, если включить. Иначе false.
     */
    public void setStripLightPowerState(boolean state) {
        STRIPLIGHT_RELAY_PIN.setState(state);
    }

    /**
     * Физическая кнопка нажата.
     */
    public void clickPowerButton() {
        switchPinState(STRIPLIGHT_RELAY_PIN);
    }

    /**
     * Главный метод класса.
     *
     * @throws InterruptedException прерывание треда во время sleep.
     */
    public void start() throws InterruptedException {
        while (!Thread.interrupted()) {
            if (STRIPLIGHT_BUTTON_INPUT_PIN.isHigh()) {
                switchPinState(STRIPLIGHT_RELAY_PIN);
            } else if (dateAndTimeForTurningOnLed.enableLight()) {
                this.pwmLEDMode();
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    /**
     * Завершающий метод класса. Выключает свет.
     */
    public static void shutdown() {
        STRIPLIGHT_RELAY_PIN.setState(PinState.LOW);
    }

    /**
     * Обеспечивает постепенный прирост яркости от 0 до 100%. Включается по
     * таймеру.
     *
     * @throws InterruptedException прерывание треда во время sleep.
     */
    private void pwmLEDMode() throws InterruptedException {
        SoftPwm.softPwmCreate(STRIPLIGHT_RELAY_PIN_ADDRESS, 0, 100);

        for (int pwmPercentValue = 0; pwmPercentValue < 100; pwmPercentValue++) {
            if (checkLedButtomDuringPwm()) {
                SoftPwm.softPwmWrite(STRIPLIGHT_RELAY_PIN_ADDRESS, pwmPercentValue);
            } else {
                return;
            }
        }
        SoftPwm.softPwmStop(STRIPLIGHT_RELAY_PIN_ADDRESS);
        STRIPLIGHT_RELAY_PIN.setState(true);
    }

    /**
     * Проверяет нужно ли продолжать увеличение яркости.
     *
     * @return true if need continue PWM, else false.
     */
    private boolean checkLedButtomDuringPwm() throws InterruptedException {
        for (int i = 0; i < 12; i++) {
            if (STRIPLIGHT_BUTTON_INPUT_PIN.isHigh()) {
                STRIPLIGHT_RELAY_PIN.setState(false);
                SoftPwm.softPwmStop(STRIPLIGHT_RELAY_PIN_ADDRESS);
                return false;
            } else {
                Thread.sleep(500);
            }
        }
        return true;
    }

    /**
     * Переключает состояние пина.
     *
     * @param pin пин для переключения состояния.
     */
    private void switchPinState(GpioPinDigitalOutput pin) {
        LOG.log(Level.INFO, "start switchPinState");
        pin.toggle();
        LOG.log(Level.INFO, "stop switchPinState");
    }
}
