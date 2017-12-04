package carte.characters;


import carte.Construction;
import carte.Joueur;
import utils.Utils;

import java.util.List;

public class Condotierre extends Character  {

    public Condotierre(int position, String name, String description) {
        super(position, name, description);
    }

    @Override
    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck){
        joueur.addCoins(bonusCoins(joueur));
        destroyBuildings(joueur, players);
    }

    //Méthode pour détruite des quartiers
    public void destroyBuildings(Joueur player, List<Joueur> players) {
        displayPlayers(players);
        System.out.println("Veuillez choisir un joueur pour afficher ses quartiers et terminer par -1");
        int input = Utils.scanner.nextInt();
        Utils.scanner.nextLine();
        while (input != -1) {
            if (input > 0 && input <= players.size()) {
                if (nbConstructions(players.get(input-1)) >= 8)
                    System.out.println("Ce joueur a déja son cité complet");
                else {
                    int joueurPos = input-1;
                    int buildingInput = 1;
                    while (buildingInput != -1) {
                        showPlayerBuilding(players.get(joueurPos));
                        System.out.println("Veuilez choisir le batiment à détruire : ");
                        buildingInput = Utils.scanner.nextInt();
                        Utils.scanner.nextLine();
                        if (buildingInput != -1) {
                            if (players.get(joueurPos).getConstructions().get(buildingInput).getPrice() - 1 <= player.getCoins()) {
                                players.get(joueurPos).getConstructions().get(buildingInput).changeBuilding();
                                player.removeCoins(players.get(joueurPos).getConstructions().get(buildingInput).getPrice() - 1);
                            } else
                                System.out.println("Vous n'avez pas assez de pièces");
                        }
                    }
                }
            } else
                System.out.println("Choix invalide");

            System.out.println("Veuillez choisir un joueur ou -1 pour quitter");
            input = Utils.scanner.nextInt();
            Utils.scanner.nextLine();
        }
        System.out.println("Fin destruction");
    }

    //Nombre de biens construits par un joueur
    private int nbConstructions(Joueur player) {
        int nbCons = 0;
        for (Construction construction : player.getConstructions()) {
            if (construction.isBuilt())
                nbCons++;
        }
        return nbCons;
    }


    //Afficher les personnages des joueurs
    private void displayPlayers(List<Joueur> players) {
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i+1) + " - " + players.get(i).getCharacter().getName());
        }
    }

    //Afficher les bien construits par un joueur
    private void showPlayerBuilding(Joueur player) {
        for (int i = 0; i < player.getConstructions().size(); i++) {
            if (player.getConstructions().get(i).isBuilt())
                System.out.println((i+1) + " - " + player.getConstructions().get(i) + ", valeur : " + player.getConstructions().get(i).getPrice());
        }
    }
}
