package carte.characters;


import carte.Construction;
import carte.Joueur;

import java.util.List;

public class Roi extends Character{
    public Roi(int position, String name, String description) {
        super(position, name, description);
    }

    @Override
    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck){
        joueur.setKing(true);
        joueur.addCoins(bonusCoins(joueur));
    }
}
