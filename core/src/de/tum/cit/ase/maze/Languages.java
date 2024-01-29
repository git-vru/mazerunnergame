package de.tum.cit.ase.maze;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The Languages class manages language localization for the MazeRunnerGame.
 */
public class Languages {
    private ResourceBundle languages;
    private Locale locale;


    /**
     * Constructs a Languages instance and sets the default language to English.
     */
    public Languages() {
        setDefaultLanguage();  // You can define the default language code in this method
    }


    /**
     * Sets the current language based on the provided language code.
     *
     * @param languageCode The language code to set (e.g., "en" for English).
     */
    public void setLanguage(String languageCode) {
        locale = new Locale(languageCode);
        languages = ResourceBundle.getBundle("language", locale);
    }
    public void setDefaultLanguage() {
        // You can set the default language code here
        setLanguage("en");  // English as an example
    }

    /**
     * Retrieves the localized string for the given key in the current language.
     *
     * @param key The key for the desired localized string.
     * @return The localized string associated with the given key.
     */
    public String get(String key) {
        return languages.getString(key);
    }

    public ResourceBundle getLanguages() {
        return languages;
    }

    public void setLanguages(ResourceBundle languages) {
        this.languages = languages;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
