package cc.nevsky.j.webguiforrpi.core;

import cc.nevsky.j.webguiforrpi.lightmanagers.BedsideLight;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Класс отвечающий за запуск и остановку этого приложения.
 *
 * @author aleksandr-nevsky
 */
@WebListener
public class MainClassForApplicationServer implements ServletContextListener {

    private AutoStartLedControlThread autoStartLedControlThread;
    private AutoStartSensorForBedsideLight autoStartSensorForBedsideLight;

    /**
     * Инициализация приложения.
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting up WebGuiForRpi!\n\n\n\n\n");
        autoStartLedControlThread = new AutoStartLedControlThread();
        autoStartLedControlThread.start();

        autoStartSensorForBedsideLight = new AutoStartSensorForBedsideLight();
        autoStartSensorForBedsideLight.start();

    }

    /**
     * Останов приложения.
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down WebGuiForRpi!\n\n\n\n\n");
        if (autoStartLedControlThread != null) {
            autoStartLedControlThread.interrupt();
        }

        if (autoStartSensorForBedsideLight != null) {
            autoStartSensorForBedsideLight.interrupt();
        }

        BedsideLight.getInstance().shutdown();
        cc.nevsky.j.webguiforrpi.core.GpioUsage.GPIO.shutdown();
    }

}
