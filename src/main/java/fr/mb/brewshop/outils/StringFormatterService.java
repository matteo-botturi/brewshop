package fr.mb.brewshop.outils;

public class StringFormatterService {

    public static String singleWord(String input) {
        String cleanedInput = cleanSpecialChars(input);
        String trimmedInput = cleanedInput.trim();
        return trimmedInput.substring(0, 1).toUpperCase() + trimmedInput.substring(1).toLowerCase();
    }

    public static String formatCountryName(String nomPays) {
        nomPays = cleanSpecialChars(nomPays.trim()).replaceAll("\\s+", " ").replaceAll("[-]+", " ");
        String[] words = nomPays.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words)
            formattedName.append(singleWord(word)).append(" ");
        return formattedName.toString().trim();
    }

    public static String formatOthersName(String nom) {
        nom = cleanSpecialChars(nom.trim()).replaceAll("\\s+", " ");
        String[] words = nom.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words)
            formattedName.append(singleWord(word)).append(" ");
        return formattedName.toString().trim();
    }

    private static String cleanSpecialChars(String input) {
        return input.replaceAll("[^\\w\\s-]", "");  // Mantiene solo lettere, numeri, spazi e trattini
    }
}