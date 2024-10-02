package fr.mb.brewshop.outils;

public class StringFormatterService {

    public static String singleWord(String input) {
        String trimmedInput = input.trim();
        return trimmedInput.substring(0, 1).toUpperCase() + trimmedInput.substring(1).toLowerCase();
    }

    public static String formatCountryName(String nomPays) {
        nomPays = nomPays.trim().replaceAll("\\s+", " ").replaceAll("[-]+", " ");
        String[] words = nomPays.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words)
            formattedName.append(singleWord(word)).append(" ");
        return formattedName.toString().trim();
    }

    public static String formatOthersName(String nomPays) {
        nomPays = nomPays.trim().replaceAll("\\s+", " ");
        String[] words = nomPays.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words)
            formattedName.append(singleWord(word)).append(" ");
        return formattedName.toString().trim();
    }
}