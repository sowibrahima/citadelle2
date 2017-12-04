package game;

import carte.Construction;
import carte.Joueur;
import carte.characters.Character;
import jdk.nashorn.internal.scripts.JO;
import utils.Command;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Tour {

    List<Character> characters;

    public Tour(List<Character> characters) {
        this.characters = characters;
    }

    //Jouer le tour du joueur
    public void playTour(Joueur joueur, List<Joueur> joueurs, List<Construction> cardsOnDeck) {
        if (joueur.isStolen()) {
            System.out.println("Le voleur vous a volé vos pièces");
            int pieces = joueur.getCoins();
            joueur.removeCoins(pieces);
            for (int i = 0; i < joueurs.size(); i++) {
                if (joueurs.get(i).getCharacter().getPosition() == 2)
                    joueurs.get(i).addCoins(pieces);
            }
            joueur.setStolen(false);
        }


        boolean endTour = false;
        List<Integer> excludeCommands = new ArrayList<>();
        Command.displayCommands();
        int input = Utils.scanner.nextInt();
        Utils.scanner.nextLine();
        while (!endTour) {
            if (input < 1 || input > 6)
                System.out.println("Commande invalide");
            else if (excludeCommands.contains(input))
                System.out.println("Commande déjà choisie");
            else {
                switch (input) {
                    case 1:
                        displayGameData(joueur, joueurs);
                        Command.displayCommands();
                        break;
                    case 2:
                        System.out.println("\nVous avez pris 2 pièces");
                        joueur.addCoins(2);
                        excludeCommands.add(2);
                        Command.displayCommands();
                        break;
                    case 3:
                        joueur.getCharacter().pickCards(cardsOnDeck, joueur);
                        excludeCommands.add(3);
                        Command.displayCommands();
                        break;
                    case 4:
                        joueur.getCharacter().build(joueur);
                        Command.displayCommands();
                        break;
                    case 5:
                        joueur.getCharacter().power(joueur, joueurs, characters, cardsOnDeck);
                        excludeCommands.add(5);
                        Command.displayCommands();
                        break;
                    case 6:
                        endTour = true;
                        break;
                }
            }
            if(!endTour){
                input = Utils.scanner.nextInt();
                Utils.scanner.nextLine();
            }

        }
        System.out.println("Fin du tour");
    }


    //Afficher les informations de la partie
    private void displayGameData(Joueur joueur, List<Joueur> joueurs) {
        String leftAlignFormat = "| %-11s | %-14s | %-8s | %-25s |%n";

        System.out.format("+-------------+----------------+----------+---------------------------+%n");
        System.out.format("|   Joueur    |   Personnage   |  Pièces  |           Biens           |%n");
        System.out.format("+-------------+----------------+----------+---------------------------+%n");
        for (int i = 0; i < joueurs.size(); i++) {
            System.out.format(leftAlignFormat, joueurs.get(i).getName() + "", joueurs.get(i).getCharacter().getName(), joueurs.get(i).getCoins() + "", "-");
            for (int j = 0; j < joueurs.get(i).getConstructions().size(); j++) {
                if (joueurs.get(i).getConstructions().get(j).isBuilt())
                    System.out.format(leftAlignFormat, "-", "-", "-", joueurs.get(i).getConstructions().get(j).getName() + '(' + joueurs.get(i).getConstructions().get(j).getPrice() + " pièces)");
            }
        }
        System.out.format("+-------------+----------------+----------+---------------------------+%n");

        System.out.println("C'est le tour de : " + joueur.getName());
    }

}
