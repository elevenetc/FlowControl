package su.elevenetc.flowcontrol.utils;

/**
 * Created by eugene.levenetc on 31/05/2017.
 */
public class Utils {
    public static boolean isTrue(int config, int param) {
        return (config & param) == param;
    }
}
