package fr.mb.brewshop.outils;

public class StringFormatterService {

    public static String singleWord(String input) {
        String trimmedInput = input.trim();
        return trimmedInput.substring(0, 1).toUpperCase() + trimmedInput.substring(1).toLowerCase();
    }
}