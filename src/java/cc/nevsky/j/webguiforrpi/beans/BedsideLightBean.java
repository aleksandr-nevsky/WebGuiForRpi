package cc.nevsky.j.webguiforrpi.beans;

import cc.nevsky.j.webguiforrpi.lightmanagers.BedsideLight;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import org.primefaces.event.SlideEndEvent;

/**
 * Бин подсветки кроватки.
 *
 * @author aleksandr-nevsky
 */
@Named(value = "BedsideLightBean")
@ApplicationScoped
public class BedsideLightBean implements Serializable {

    private static final long serialVersionUID = 1;
    BedsideLight bedsideLight = BedsideLight.getInstance();
    private static final Logger LOG = Logger.getLogger(BedsideLightBean.class.getName());

    /**
     * Устанавливает значение яркости подсветки.
     *
     * @param pwm значение.
     */
    public void setPwm(int pwm) {
        bedsideLight.setPwmValue(pwm);
    }

    /**
     * Возвращает значение яркости подсветки.
     *
     * @return значение от 0 до 100.
     */
    public int getPwm() {
        return bedsideLight.getPwmValue();
    }

    /**
     * @deprecated
     */
    public void onChange() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "You have selected: " + bedsideLight.getPwmValue(), null));
    }

    /**
     * Выключить подсветку.
     */
    public void offBedsideLight() {
        bedsideLight.setPwmValue(0);
    }

    /**
     * Выводит значение яркости после изменения на экран.
     *
     * @param event
     */
    public void onSlideEnd(SlideEndEvent event) {
        FacesMessage message = new FacesMessage("процент яркости установлен", "Значение: " + event.getValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
