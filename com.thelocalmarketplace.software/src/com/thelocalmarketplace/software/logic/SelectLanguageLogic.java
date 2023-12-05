package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.software.AbstractLogicDependant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementing Select Language Logic 
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
 * @author Maheen Nizmani (30172615)
 * @author Christopher Lo (30113400)
 * @author Michael Svoboda (30039040)
 * @author Sukhnaaz Sidhu (30161587)
 * @author Ian Beler (30174903)
 * @author Gareth Jenkins (30102127)
 * @author Jahnissi Nwakanma (30174827)
 * @author Camila Hernandez (30134911)
 * @author Ananya Jain (30196069)
 * @author Zhenhui Ren (30139966)
 * @author Eric George (30173268)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */


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
        for (String language : getSupportedLanguages()) {
            System.out.println(language);
        }
    }

    public boolean selectLanguage(String language) {
        if (getSupportedLanguages().contains(language)) {
            this.selectedLanguage = language;
            return true;
         } else if (language == defaultLanguage) {
            	System.out.println("This is already selected");
            	selectedLanguage = defaultLanguage; 
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


	public List<String> getSupportedLanguages() {
		return supportedLanguages;
	}
	

}
