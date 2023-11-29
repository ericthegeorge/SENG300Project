package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SelectLanguageLogic extends AbstractLogicDependant {

    private List<String> supportedLanguages;
    private String selectedLanguage;
    private String defaultLanguage;

    public SelectLanguageLogic(CentralStationLogic logic, String defaultLanguage) {
        super(logic); // Pass the central logic to the superclass constructor
        this.defaultLanguage = defaultLanguage;
        this.selectedLanguage = defaultLanguage;
     // Initialize the supported languages here, or this could be passed from outside
        this.supportedLanguages = Arrays.asList("English", "French", "Spanish"); // Add more as required
    }
    
    
    public void setSupportedLanguages(List<String> languages) {
        this.supportedLanguages = languages;
    }

    public void displayLanguages() {
        // Code to display language options to the user.
        System.out.println("Please select a language:");
        for (String language : supportedLanguages) {
            System.out.println(language);
        }
    }

    public boolean selectLanguage(String language) {
        if (supportedLanguages.contains(language)) {
            this.selectedLanguage = language;
            return true;
        } else {
            System.out.println("Selected language is not supported.");
            return false;
        }
    }

    public String getSelectedLanguage() {
        return Optional.ofNullable(selectedLanguage).orElse(defaultLanguage);
    }

    public void cancelLanguageSelection() {
        this.selectedLanguage = defaultLanguage;
      }

}
