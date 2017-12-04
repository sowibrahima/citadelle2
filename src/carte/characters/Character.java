package carte.characters;


import carte.Carte;
import carte.Construction;
import carte.Joueur;
import utils.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Character implements Carte {

    int position;
    String name, description;

    public Character(int position, String name, String description){
        this.position = position;
        this.name = name;
        this.description = description;
    }

    public int getPosition(){
        return this.position;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void power(Joueur joueur, List<Joueur> players, List<Character> characters, List<Construction> cardsOnDeck){

    }

    public void pickCards(List<Construction> constructions, Joueur joueur){
        final int[] randomInts = new Random().ints(0, constructions.size()).distinct().limit(2).toArray();

        System.out.println("\nCartes choisies : \n");
        for(int i=0; i<randomInts.length; i++){
            System.out.println((i+1)+" - "+(constructions.get(randomInts[i])).getName());
        }

        System.out.println("\nChoisir la carte à garder : \n");
        int input = Utils.scanner.nextInt();
        Utils.scanner.nextLine();
        while (input < 1 || input > randomInts.length) {
            System.out.println("\nVeuillez choisir une carte valide : ");
            input = Utils.scanner.nextInt();
            Utils.scanner.nextLine();
        }

        Construction consToAdd = constructions.get(randomInts[input-1]);
        consToAdd.changeOwnership();
        joueur.addConstruction(consToAdd);
        System.out.println("\nVous avez choisi la carte : "+constructions.get(randomInts[input-1]).getName());
        constructions.remove(randomInts[input-1]);
    }


    //Choix de deux cartes au hasard au debut
    public void pickTwoCards(List<Construction> constructions, Joueur joueur){
        final int[] randomInts = new Random().ints(0, constructions.size()).distinct().limit(2).toArray();

        Construction consToAdd1 = constructions.get(randomInts[0]);
        Construction consToAdd2 = constructions.get(randomInts[1]);

        consToAdd1.changeOwnership();
        consToAdd2.changeOwnership();

        joueur.addConstruction(consToAdd1);
        joueur.addConstruction(consToAdd2);
        constructions.remove(randomInts[0]);
        constructions.remove(randomInts[1]);
    }

    public void build(Joueur joueur){
        displayConstruction(joueur);
        int nbConstruits = 0;
        int nbMaxCons  = joueur.getCharacter().getPosition() == 8 ? 3 : 2;
        int input = Utils.scanner.nextInt();
        Utils.scanner.nextLine();

        while (input != -1 && nbConstruits<nbMaxCons && input > 0) {
            if(input <= joueur.getConstructions().size()){
                if(joueur.getConstructions().get(input-1).getPrice() <= joueur.getCoins()){
                    joueur.getConstructions().get(input-1).changeBuilding();
                    joueur.removeCoins(joueur.getConstructions().get(input-1).getPrice());
                    System.out.println(joueur.getConstructions().get(input-1).getName()+" construit");
                    nbConstruits++;
                } else
                    System.out.println("Il vous manque : "+(joueur.getConstructions().get(input).getPrice()-joueur.getCoins())+" pièces");
            } else
                System.out.println("\nChoix invalide\n");

            displayConstruction(joueur);
            input = Utils.scanner.nextInt();
            Utils.scanner.nextLine();
        }

        if(nbConstruits == nbMaxCons)
            System.out.println("Vous avez déjà construit "+nbConstruits+" biens");

        System.out.println("\nFin construction\n");
    }


    private void displayConstruction(Joueur joueur){
        System.out.println("\nConstrure : ");
        System.out.println("-1 - Fin construction");
        for (int i=0; i<joueur.getConstructions().size(); i++){
            Construction currentConstruction = joueur.getConstructions().get(i);
            if(!currentConstruction.isBuilt())
                System.out.println((i+1)+" - "+currentConstruction.getName()+" - "+currentConstruction.getPrice()+" pièces");
        }
    }

    //Donner une pièce au joueur pour chaque quartier rapportant correspondant à son personnage
    public int bonusCoins(Joueur joueur) {
        int bonusCoins = 0;
        for (int i = 0; i < joueur.getConstructions().size(); i++) {
            if (joueur.getConstructions().get(i).isBuilt() && joueur.getConstructions().get(i).getCharacter() == this.getPosition())
                bonusCoins += 1;
        }
        return bonusCoins;
    }

    @Override
    public String toString(){
        return this.getName()+"("+this.position+")";
    }

}
