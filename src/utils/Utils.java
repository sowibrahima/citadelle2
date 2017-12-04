package utils;

import carte.Construction;
import carte.Joueur;
import carte.characters.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Utils {

    public static Scanner scanner = new Scanner(System.in);

    //Déterminer si une chaîne de caractère est un chiffre
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }


    public static Character victim(List<Character> characters, List<Integer> playersToExclude) {
        List<Character> possibleVictims = new ArrayList<>();

        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
            if (!playersToExclude.contains(character.getPosition()))
                possibleVictims.add(character);
        }

        for (int i = 0; i < possibleVictims.size(); i++) {
            Character character = possibleVictims.get(i);
            System.out.println((i+1) + " - " + character.getName());
        }

        System.out.println("\nChoix : ");
        int input = scanner.nextInt();
        Utils.scanner.nextLine();
        while (input < 1 || input > possibleVictims.size()) {
            System.out.println("\nVeuillez entrer un choix valide : ");
            input = scanner.nextInt();
            Utils.scanner.nextLine();
        }

        return possibleVictims.get(input-1);
    }

    public static int findPlayerIndexByCharacter(List<Joueur> players, int character){
        int playerIndex = 0;
        for (int i=0; i<players.size(); i++){
            if(players.get(i).getCharacter().getPosition() == character)
                playerIndex = i;
        }
        return playerIndex;
    }
}
