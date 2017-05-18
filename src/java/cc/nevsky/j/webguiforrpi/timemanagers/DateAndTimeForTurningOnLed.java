package cc.nevsky.j.webguiforrpi.timemanagers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс управления датой и временем для включения/выключения света. Использует
 * Синглтон.
 *
 * @author aleksandr-nevsky
 */
public class DateAndTimeForTurningOnLed implements Serializable {

    private static final long serialVersionUID = 1;
    private static final Logger LOG = Logger.getLogger(DateAndTimeForTurningOnLed.class.getName());
    private final Set<DayOfWeek> daysOnEnableLed = new HashSet<>();
    private final File CONF_FILE = new File("dateAndTime.conf");
    private final Properties PROPERTIES = new Properties();
    private static volatile DateAndTimeForTurningOnLed instance;

    private Integer onHour;
    private Integer onMinute;
    private Integer onSecond;

    /**
     * Конструктор. Устанавливливаются значения по умолчанию. Потом вычитываются
     * из конфига.
     */
    private DateAndTimeForTurningOnLed() {
        LOG.log(Level.INFO, "DateAndTimeForTurningOnLed constructor");
        loadConfig();
    }

    /**
     * Синглтон.
     *
     * @return инстанс.
     */
    public static DateAndTimeForTurningOnLed getInstance() {
        if (instance == null) {
            synchronized (DateAndTimeForTurningOnLed.class) {
                if (instance == null) {
                    instance = new DateAndTimeForTurningOnLed();
                }
            }
        }
        return instance;
    }

    /**
     * Читаам конфиг. Если конфига нет - создаём файл и инициализируемся
     * значениями по умолчанию.
     */
    private void loadConfig() {
        try {
            if (CONF_FILE.createNewFile()) {
                LOG.info("Create new config file");
                initializeFromDefaultValue();
            } else {
                LOG.info("Config file exist");
                readDataFromConfig();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "loadConfig error", ex);
        }
    }

    /**
     * Читаем конфиг из файла.
     */
    private void readDataFromConfig() {
        try (FileInputStream fis = new FileInputStream(CONF_FILE)) {
            PROPERTIES.load(fis);
            this.onHour = Integer.parseInt(PROPERTIES.getProperty("Hour"));
            this.onMinute = Integer.parseInt(PROPERTIES.getProperty("Minute"));
            this.onSecond = Integer.parseInt(PROPERTIES.getProperty("Second"));
            for (DayOfWeek day : DayOfWeek.values()) {
                String dayName = PROPERTIES.getProperty(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                if (dayName.equals("true")) {
                    this.daysOnEnableLed.add(day);
                }
            }
        } catch (NumberFormatException ex) {
            LOG.log(Level.WARNING, "maybe brocen config file", ex);
            initializeFromDefaultValue();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "readDataFromConfig error", ex);
        }
    }

    /**
     * Пишем конфиг в файл.
     */
    private void writeDataToConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONF_FILE)) {
            PROPERTIES.setProperty("Hour", this.onHour.toString());
            PROPERTIES.setProperty("Minute", this.onMinute.toString());
            PROPERTIES.setProperty("Second", this.onSecond.toString());
            LOG.info("setProperty Hour " + this.onHour.toString());
            LOG.info("setProperty Minute " + this.onMinute.toString());
            LOG.info("setProperty Second " + this.onSecond.toString());

            for (DayOfWeek day : DayOfWeek.values()) {
                if (containsConcreteDayInDayOnEnableLed(day)) {
                    PROPERTIES.setProperty(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH), "true");
                    LOG.info("setProperty " + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " true");
                } else {
                    PROPERTIES.setProperty(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH), "false");
                    LOG.info("setProperty " + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " false");
                }
            }
            PROPERTIES.store(fos, "Automatic modification");
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "readDataFromConfig error", ex);
        }

    }

    /**
     * Иницализируемся значениями по умолчанию.
     */
    private void initializeFromDefaultValue() {
        this.onHour = 6;
        this.onMinute = 20;
        this.onSecond = 0;

        this.daysOnEnableLed.add(DayOfWeek.MONDAY);
        this.daysOnEnableLed.add(DayOfWeek.TUESDAY);
        this.daysOnEnableLed.add(DayOfWeek.WEDNESDAY);
        this.daysOnEnableLed.add(DayOfWeek.THURSDAY);
        this.daysOnEnableLed.add(DayOfWeek.FRIDAY);
    }

    /**
     *
     * @return час включания подсветки.
     */
    public int getOnHour() {
        return onHour;
    }

    /**
     * Задаёт час, когда будет происходить включение подсветки.
     *
     * @param onHour час включения.
     */
    public void setOnHour(int onHour) {
        if (onHour < 0 || onHour > 23) {
            LOG.warning("Invalid hour value: " + onHour);
        } else {
            this.onHour = onHour;
            writeDataToConfig();
        }
    }

    /**
     *
     * @return минута включения подсветки.
     */
    public int getOnMinute() {
        return onMinute;
    }

    /**
     * Задаёт минуту, когда будет происходить включение подсветки.
     *
     * @param onMinute минута включения.
     */
    public void setOnMinute(int onMinute) {
        if (onMinute < 0 || onMinute > 59) {
            LOG.warning("Invalid minute value: " + onMinute);
        } else {
            this.onMinute = onMinute;
            writeDataToConfig();
        }
    }

    /**
     *
     * @return @deprecated
     */
    public int getOnSecond() {
        return onSecond;
    }

    /**
     *
     * @param onSecond
     * @deprecated
     */
    public void setOnSecond(int onSecond) {
        if (onSecond < 0 || onSecond > 59) {
            System.out.println("Invalid second value: " + onSecond);
        } else {
            this.onSecond = onSecond;
            writeDataToConfig();
        }
    }

    /**
     * Добавляет день недели в список дней для срабатывания.
     *
     * @param day день недели.
     */
    public void insertDayOnEnableLed(DayOfWeek day) {
        this.daysOnEnableLed.add(day);
        writeDataToConfig();
    }

    /**
     * Удаляет день недели из списка дней для срабдатывания.
     *
     * @param day день недели.
     */
    public void removeDayOnEnableLed(DayOfWeek day) {
        this.daysOnEnableLed.remove(day);
        writeDataToConfig();
    }

    /**
     * Присутствует ли день недели в списке дней недели для срабатывания.
     *
     * @param day день недели.
     * @return true, если в переданный день происходит включение. Иначе false.
     */
    public boolean containsConcreteDayInDayOnEnableLed(DayOfWeek day) {
        return this.daysOnEnableLed.contains(day);

    }

    /**
     * Сейчас день для включения света?
     *
     * @return true, если день недели в списке дней недели для срабатывания.
     * Иначе false.
     */
    private boolean isDayToLight() {
        return daysOnEnableLed.contains(LocalDate.now().getDayOfWeek());
    }

    /**
     * Сейчас время для включения света?
     *
     * @return true - время подходящее, время не подходящее.
     */
    private boolean isTimeToLight() {
        LocalTime timeNow = LocalTime.now();
        return this.onHour == timeNow.getHour()
                && this.onMinute == timeNow.getMinute()
                && this.onSecond == timeNow.getSecond();
    }

    /**
     * @return true если нужно включать свет. Иначе false.
     */
    public boolean enableLight() {
        return isDayToLight() && isTimeToLight();
    }
}
