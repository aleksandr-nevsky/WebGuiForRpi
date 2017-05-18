package cc.nevsky.j.webguiforrpi.core;

import cc.nevsky.j.webguiforrpi.lightmanagers.LedControl;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс автозапуска отдельньного потока для работы со светодиодной лентой.
 *
 * @author aleksandr-nevsky
 */
public class AutoStartLedControlThread extends Thread {

    private static final Logger LOG = Logger.getLogger(AutoStartLedControlThread.class.getName());

    /**
     * Конструктор пишет в лог о том, что он запустился.
     */
    public AutoStartLedControlThread() {
        LOG.log(Level.INFO, "AutoStart constructor");
    }

    /**
     * Запуск потока. Ловит InterruptedException при завершении потока.
     */
    @Override
    public void run() {
        try {
            LedControl ledControl = LedControl.getInstance();
            ledControl.start();
        } catch (InterruptedException ex) {
            LOG.info("Interrupt thread");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "AutoStart run error", ex);
        }
    }

}
