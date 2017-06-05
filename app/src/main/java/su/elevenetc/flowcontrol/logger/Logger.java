package su.elevenetc.flowcontrol.logger;

/**
 * Created by eugene.levenetc on 05/06/2017.
 */
public class Logger {

    public static void log(Object objTag, String message) {
        log(objTag.getClass(), message);
    }

    public static void log(Class classTag, String message) {
        System.out.println(classTag.getSimpleName() + ": " + message);
    }
}
