package carte.characters;


import carte.Construction;
import carte.Joueur;
import utils.Utils;

import java.util.Arrays;
import java.util.List;

public class Voleur extends Character {

    public Voleur(int position, String name, String description) {
        super(position, name, description);
    }

    @Override
    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck){
        System.out.println("\nVeuillez choisir le joueur à voler : ");
        List<Integer> playersToExclude = Arrays.asList(1,2);
        Character victim = Utils.victim(characters, playersToExclude);
        System.out.println("Victim " + victim.getName());

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacter().getPosition() == victim.getPosition())
                players.get(i).setStolen(true);
        }
    }
}
