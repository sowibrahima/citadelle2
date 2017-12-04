package carte.characters;


import carte.Construction;
import utils.Utils;
import carte.Joueur;

import java.util.Arrays;
import java.util.List;

public class Assasin extends Character {

    public Assasin(int position, String name, String description) {
        super(position, name, description);
    }

    @Override
    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck){
        System.out.println("\nVeuillez choisir le joueur à assassiner : ");
        List<Integer> playersToExclude = Arrays.asList(1);
        Character victim = Utils.victim(characters, playersToExclude);

        for(int i=0; i<players.size(); i++){
            if(players.get(i).getCharacter().getPosition() == victim.getPosition())
                players.get(i).setAssassinated(true);
        }
    }
}
