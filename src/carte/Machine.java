package carte;


import carte.characters.Character;

import java.util.ArrayList;
import java.util.List;

public class Machine extends Joueur{

    int intelligence;

    public Machine(Character character, List<Construction> constructions, int coins, int intelligence, String name) {
        super(character, false, constructions, coins, name);
        this.intelligence = intelligence;
    }
}
