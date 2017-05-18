package cc.nevsky.j.webguiforrpi.beans;

import cc.nevsky.j.webguiforrpi.timemanagers.DateAndTimeForTurningOnLed;
import java.io.Serializable;
import java.time.DayOfWeek;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Бин настройки и отображения информации о включении подсветки.
 *
 * @author aleksandr-nevsky
 */
@Named(value = "DateTimeBin")
@ApplicationScoped
public class DateTimeBin implements Serializable {

    private static final long serialVersionUID = 1;
    private final DateAndTimeForTurningOnLed dateAndTimeForTurningOnLed = DateAndTimeForTurningOnLed.getInstance();

    /**
     *
     * @return true, если подсветка включается в понедельник. Иначе false.
     */
    public boolean isMonday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.MONDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setMonday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.MONDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.MONDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void mondayMessage() {
        String msg = isMonday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return true, если подсветка включается во вторник. Иначе false.
     */
    public boolean isTuesday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.TUESDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setTuesday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.TUESDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.TUESDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void tuesdayMessage() {
        String msg = isTuesday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return true, если подсветка включается в среду. Иначе false.
     */
    public boolean isWensday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.WEDNESDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setWensday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.WEDNESDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.WEDNESDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void wensdayMessage() {
        String msg = isWensday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return true, если подсветка включается в четверг. Иначе false.
     */
    public boolean isThursday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.THURSDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setThursday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.THURSDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.THURSDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void thursdayMessage() {
        String msg = isThursday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return true, если подсветка включается в пятницу. Иначе false.
     */
    public boolean isFriday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.FRIDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setFriday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.FRIDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.FRIDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void fridayMessage() {
        String msg = isFriday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return true, если подсветка включается в субботу. Иначе false.
     */
    public boolean isSaturday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.SATURDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setSaturday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.SATURDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.SATURDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void saturdayMessage() {
        String msg = isSaturday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return true, если подсветка включается в воскресенье. Иначе false.
     */
    public boolean isSunday() {
        return dateAndTimeForTurningOnLed.containsConcreteDayInDayOnEnableLed(DayOfWeek.SUNDAY);
    }

    /**
     * Устанавливает состояние включения в определённый день.
     *
     * @param state true, если включать. Иначе false.
     */
    public void setSunday(boolean state) {
        if (state) {
            dateAndTimeForTurningOnLed.insertDayOnEnableLed(DayOfWeek.SUNDAY);
        } else {
            dateAndTimeForTurningOnLed.removeDayOnEnableLed(DayOfWeek.SUNDAY);
        }
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void sundayMessage() {
        String msg = isSunday() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return час включания подсветки.
     */
    public int getHour() {
        return dateAndTimeForTurningOnLed.getOnHour();
    }

    /**
     * Задаёт час, когда будет происходить включение подсветки.
     *
     * @param hour час включения.
     */
    public void setHour(int hour) {
        dateAndTimeForTurningOnLed.setOnHour(hour);
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void hourMessage() {
        String msg = dateAndTimeForTurningOnLed.getOnHour() + " hour";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return минута включения подсветки.
     */
    public int getMinute() {
        return dateAndTimeForTurningOnLed.getOnMinute();
    }

    /**
     * Задаёт минуту, когда будет происходить включение подсветки.
     *
     * @param minute минута включения.
     */
    public void setMinute(int minute) {
        dateAndTimeForTurningOnLed.setOnMinute(minute);
    }

    /**
     * Показывает состояние переключателя после изменения положения.
     */
    public void minuteMessage() {
        String msg = dateAndTimeForTurningOnLed.getOnMinute() + " minute";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    /**
     *
     * @return @deprecated не используется.
     */
    public int getSecond() {
        return dateAndTimeForTurningOnLed.getOnSecond();
    }

    /**
     *
     * @param second
     * @deprecated не используется.
     */
    public void setSecond(int second) {
        dateAndTimeForTurningOnLed.setOnSecond(second);
    }

    /**
     *
     * @deprecated не используется.
     */
    public void secondMessage() {
        String msg = dateAndTimeForTurningOnLed.getOnSecond() + " second";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
}
