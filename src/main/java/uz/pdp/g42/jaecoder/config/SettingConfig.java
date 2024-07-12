package uz.pdp.g42.jaecoder.config;

import java.util.ResourceBundle;

public class SettingConfig {
    private static final ResourceBundle settings = ResourceBundle.getBundle("application");

    public static String get(String key) {
        return settings.getString(key);
    }
}
