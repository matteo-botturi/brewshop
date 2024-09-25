package fr.mb.brewshop.outils;

public class StringFormatterService {

    public static String singleWord(String input) {
        String trimmedInput = input.trim();
        return trimmedInput.substring(0, 1).toUpperCase() + trimmedInput.substring(1).toLowerCase();
    }

    public static String formatCountryName(String countryName) {
        countryName = countryName.trim().replaceAll("\\s+", " ").replaceAll("[-]+", " ");
        String[] words = countryName.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (word.length() > 1)
                formattedName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
            else
                formattedName.append(word.toUpperCase());  // Per abbreviazioni tipo "U.S.A"
            formattedName.append(" ");
        }
        return formattedName.toString().trim();
    }
}