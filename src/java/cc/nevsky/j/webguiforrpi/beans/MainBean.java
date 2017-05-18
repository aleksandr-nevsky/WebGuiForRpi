package cc.nevsky.j.webguiforrpi.beans;

import cc.nevsky.j.webguiforrpi.lightmanagers.LedControl;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Бин управления светодиодной лентой.
 *
 * @author aleksandr-nevsky
 */
@Named(value = "MainBean")
@ApplicationScoped
public class MainBean implements Serializable {

    private static final long serialVersionUID = 1;

    LedControl ledControl = LedControl.getInstance();

    /**
     * Светодиодная лента.
     *
     * @return true, если лента включена. Иначе false.
     */
    public boolean isStripLightPower() {
        return ledControl.getStripLightPowerState();
    }

    /**
     * Светодиодная лента.
     *
     * @param state true, если включить. Иначе false.
     */
    public void setStripLightPower(boolean state) {
        ledControl.setStripLightPowerState(state);
    }

    /**
     * Показывает состояние светодиодной ленты после изменения положения
     * переключателя.
     */
    public void stripLedPoserMessage() {
        String msg = ledControl.getStripLightPowerState() ? "ON" : "OFF";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
}
