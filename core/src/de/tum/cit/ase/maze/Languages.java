package de.tum.cit.ase.maze;

import java.util.Locale;
import java.util.ResourceBundle;

public class Languages {
    private ResourceBundle languages;
    private Locale locale;

    public Languages() {
        setDefaultLanguage();  // You can define the default language code in this method
    }

    public void setLanguage(String languageCode) {
        locale = new Locale(languageCode);
        languages = ResourceBundle.getBundle("language", locale);
    }
    public void setDefaultLanguage() {
        // You can set the default language code here
        setLanguage("en");  // English as an example
    }

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
