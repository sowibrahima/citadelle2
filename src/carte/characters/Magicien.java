package carte.characters;


import carte.Construction;
import carte.Joueur;
import game.Game;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Magicien extends Character {
    public Magicien(int position, String name, String description) {
        super(position, name, description);
    }

    @Override
    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck){
        System.out.println("\nVeuillez choisir :\n1 pour echanger tes cartes contre un autre joueur");
        System.out.println("2 pour echanger certaines cartes dans la pioche\nChoix : ");
        int input = Utils.scanner.nextInt();
        Utils.scanner.nextLine();
        while (input != 1 && input != 2) {
            System.out.println("\nVeuillez choisir 1 ou 2 : ");
            input = Utils.scanner.nextInt();
            Utils.scanner.nextLine();
        }

        switch (input) {
            case 1:
                System.out.println("\nVeuillez choisir le joueur contre qui echanger vos cartes ");
                List<Integer> playersToExclude = Arrays.asList(3);
                Character victim = Utils.victim(characters, playersToExclude);

                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCharacter().getPosition() == victim.getPosition()) {

                        List<Construction> magicianConstructions = joueur.getConstructions();
                        int victimPlayerIndex = Utils.findPlayerIndexByCharacter(players, victim.getPosition());
                        joueur.setConstructions(players.get(victimPlayerIndex).getConstructions());
                        players.get(i).setConstructions(magicianConstructions);
                    }
                }
            case 2:
                List<Construction> consToRemove = new ArrayList<>();
                List<String> consToRemoveNames = new ArrayList<>();
                for (int i = 0; i < joueur.getConstructions().size(); i++) {
                    System.out.print("\n" + (i+1)+ " - " + joueur.getConstructions().get(i).getName());
                }
                System.out.println("\nVeuillez choisir les cartes à échanger et terminer par -1\n");
                int card = Utils.scanner.nextInt();
                Utils.scanner.nextLine();
                while (card != -1) {
                    if (card > 0 && card <= joueur.getConstructions().size()) {
                        Construction cardToAdd = joueur.getConstructions().get(card-1);
                        if (consToRemoveNames.contains(cardToAdd.getName()))
                            System.out.println("Carte déja choisie");
                        else{
                            consToRemove.add(cardToAdd);
                            consToRemoveNames.add(cardToAdd.getName());
                        }

                    } else
                        System.out.println("Choix invalide");
                    card = Utils.scanner.nextInt();
                    Utils.scanner.nextLine();
                }

                final int[] randomInts = new Random().ints(0, cardsOnDeck.size()).distinct().limit(consToRemove.size()).toArray();
                for (int i = 0; i < consToRemove.size(); i++) {
                    Construction newConsToAdd = cardsOnDeck.get(randomInts[i]);
                    cardsOnDeck.remove(randomInts[i]);
                    newConsToAdd.changeOwnership();
                    joueur.addConstruction(newConsToAdd);
                    cardsOnDeck.add(consToRemove.get(i));
                }
                break;
            default:
                System.out.println("\nChoix invalide");
                break;
        }
    }
}
