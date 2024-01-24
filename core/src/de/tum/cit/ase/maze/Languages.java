package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Languages {
    private ResourceBundle languages;
    private Locale locale;
    private Map<String, ResourceBundle> languageBundles;
    private String currentLanguage;
    private String languageCode;
    public Languages() {
       // loadLanguageBundles();
        setDefaultLanguage();  // You can define the default language code in this method
        currentLanguage = "en";
    }

    public void setLanguage(String languageCode) {
        locale = new Locale(languageCode);
        languages = ResourceBundle.getBundle("language", locale);
    }
    public void setDefaultLanguage() {
        // You can set the default language code here
        setLanguage("en");  // English as an example
        System.out.println(locale.getLanguage());
    }
    public void loadLanguageBundles() {
        // Load ResourceBundle for each supported language
        // In this example, we assume you have properties files (e.g., language_en.properties, language_zh.properties)
        // with key-value pairs for localized strings
        this.languageBundles = new HashMap<>();
        languageBundles.put("en", ResourceBundle.getBundle("language", Locale.ENGLISH));
        languageBundles.put("cn", ResourceBundle.getBundle("language", Locale.SIMPLIFIED_CHINESE));
        languageBundles.put("fr", ResourceBundle.getBundle("language", Locale.FRENCH));
        languageBundles.put("gm", ResourceBundle.getBundle("language", Locale.GERMAN));
        languageBundles.put("tr", ResourceBundle.getBundle("language", new Locale("TURKISH")));
        languageBundles.put("ar", ResourceBundle.getBundle("language", new Locale("ARABIC")));
        languageBundles.put("sp", ResourceBundle.getBundle("language", new Locale("SPANISH")));
        languageBundles.put("ru", ResourceBundle.getBundle("language", new Locale(" RUSSIAN")));
        languageBundles.put("hn", ResourceBundle.getBundle("language", new Locale("HINDI")));
        // Add other supported languages as needed
    }
    public void setLanguage() {
        if (languageBundles.containsKey(languageCode)) {
            currentLanguage = languageCode;
        } else {
            // Fallback to the default language if the requested language is not supported
            currentLanguage = "en";
        }
    }

    public String getString(String key) {
        return languageBundles.get(currentLanguage).getString(key);
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

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

}
