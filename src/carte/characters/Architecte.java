package carte.characters;


import carte.Construction;
import carte.Joueur;
import game.Game;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Architecte extends Character {
    public Architecte(int position, String name, String description) {
        super(position, name, description);
    }

    @Override
    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck) {
        final int[] randomInts = new Random().ints(0, cardsOnDeck.size()).distinct().limit(2).toArray();
        for (int i = 0; i < 2; i++) {
            Construction newConsToAdd = cardsOnDeck.get(randomInts[i]);
            cardsOnDeck.remove(randomInts[i]);
            newConsToAdd.changeOwnership();
            joueur.addConstruction(newConsToAdd);
        }
    }

    /*
    @Override
    public void pickCards(List<Construction> constructions, Joueur joueur) {
        final int[] randomInts = new Random().ints(0, constructions.size()).distinct().limit(4).toArray();

        List<Construction> choosenCards = new ArrayList<>();
        for (int i = 0; i < randomInts.length; i++)
            choosenCards.add(constructions.get(randomInts[i]));

        System.out.println("\nChoisir les 3 cartes à garder : \n");

        while (choosenCards.size() > 1) {
            for (int i = 0; i < choosenCards.size(); i++)
                System.out.println((i + 1) + " - " + choosenCards.get(i).getName());

            int input = Utils.scanner.nextInt();
            while (input < 1 || input > choosenCards.size()) {
                System.out.println("\nVeuillez choisir une carte valide : ");
                input = Utils.scanner.nextInt();
            }

            choosenCards.remove(input - 1);
            joueur.addConstruction(constructions.get(randomInts[input - 1]));
            System.out.println("\nVous avez choisi la carte : " + constructions.get(randomInts[input - 1]).getName()+"\n");
            constructions.remove(randomInts[input - 1]);
        }
        System.out.println("Fin du choix");
    }
    */
}
