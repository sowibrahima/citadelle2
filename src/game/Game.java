package game;

import carte.Construction;
import carte.Joueur;
import carte.PlayerData;
import carte.characters.Character;
import utils.Utils;

import javax.rmi.CORBA.Util;
import java.util.*;

public class Game {

    static GameData gameData;
    private int nbJoueurs;
    private List<Joueur> joueurs;
    private List<Construction> cardsOnDeck;
    private List<Integer> caracteresChoisis;
    private List<Character> caracteresDisponibles;
    private ArrayList<PlayerData> playerDatas;
    private boolean gameOver;
    private int nbTours;

    public Game(int nbJoueurs) {
        this.gameOver = false;
        this.nbTours = 0;
        this.nbJoueurs = nbJoueurs;
        this.joueurs = new ArrayList<>();
        this.cardsOnDeck = new ArrayList<>();
        this.playerDatas = new ArrayList<>();
        this.caracteresChoisis = new ArrayList<>();
        this.caracteresDisponibles = new ArrayList<>();
    }


    //Instantier le jeu
    private void instantiateGame() {
        caracteresDisponibles = gameData.getCharacters();
        for (int i = 1; i <= nbJoueurs; i++) {
            System.out.println("Joueur " + i + ", veuillez entrer votre nom : ");
            String name = Utils.scanner.nextLine();


            System.out.println("Veuillez choisir 1 pour un humain et 2 pour un robot\n");
            int inputHuman = Utils.scanner.nextInt();
            Utils.scanner.nextLine();

            while (inputHuman != 1 && inputHuman != 2) {
                System.out.println("\nVeuillez entrer un choix valide : ");
                inputHuman = Utils.scanner.nextInt();
                Utils.scanner.nextLine();
            }

            this.joueurs.add(new Joueur(new Character(-1, null, null), false, new ArrayList<>(), 2, name));

            if (inputHuman == 1)
                this.joueurs.get(i - 1).changeHumanity();
        }

        for (int i=0; i<gameData.getBiens().size(); i++){
            Construction c = (Construction) gameData.getBiens().get(i);
            for (int k=0; k<c.getNumberOccur(); k++){
                cardsOnDeck.add(c);
            }
        }
        choixCarte();
    }

    //Jouer un tour tant que le jeu n'est pas terminé
    private void tour() {
        while (!gameOver) {
            /*
            int[] characterPresent = new int[gameData.getCharacters().size()];
            for(int i=0; i<characterPresent.length; i++)
                characterPresent[i] = -1;

            for(int i=0; i<this.joueurs.size(); i++){
                switch (this.joueurs.get(i).getCharacter().getPosition()){
                    case 1:
                        characterPresent[0] = i;
                        break;
                    case 2:
                        characterPresent[1] = i;
                        break;
                    case 3:
                        characterPresent[2] = i;
                        break;
                    case 4:
                        characterPresent[3] = i;
                        break;
                    case 5:
                        characterPresent[4] = i;
                        break;
                    case 6:
                        characterPresent[5] = i;
                        break;
                    case 7:
                        characterPresent[6] = i;
                        break;
                    case 8:
                        characterPresent[7] = i;
                        break;

                }
            }
            */


            for (int i = 0; i < caracteresChoisis.size(); i++) {
                nbTours++;

                //Appeler les joueurs par leur ordres
                for (int j = 0; j < this.joueurs.size(); j++) {
                    if ((i + 1) == this.joueurs.get(j).getCharacter().getPosition()) {
                        System.out.println("\nTour " + nbTours + " - " + this.joueurs.get(j).getName() + " - " + this.joueurs.get(j).getCharacter().getName());

                        if (joueurs.get(j).isAssassinated()) {
                            System.out.println("x_x Vous êtes assasinés");
                            joueurs.get(j).setAssassinated(false);
                        } else {
                            Tour tour = new Tour(gameData.getCharacters());
                            tour.playTour(joueurs.get(j), joueurs, cardsOnDeck);
                        }

                        gameOver = gameEnded();
                        if (gameOver) {
                            int max = 0;
                            int posMax = 0;
                            for (int k = 0; k < playerDatas.size(); k++) {
                                if (playerDatas.get(k).getWealth() > max) {
                                    max = playerDatas.get(k).getWealth();
                                    posMax = k;
                                }
                            }
                            System.out.println(this.joueurs.get(posMax).getName() + " a gagné avec une fortune de " + playerDatas.get(posMax).getWealth() + " pièces");
                            break;
                        }


                        for (int k = 0; k < joueurs.size(); k++) {
                            if (joueurs.get(k).getCharacter().getPosition() == 4)
                                joueurs.get(k).setKing(true);
                        }
                    }
                }

            }

            caracteresChoisis = new ArrayList<>();
            caracteresDisponibles = gameData.getCharacters();
            choixCarte();

        }
    }

    //Distribuer les cartes aux joueurs pour qu'ils fassent leur choix
    private void choixCarte() {
        List<Joueur> joueursClone = new ArrayList<>();
        int roi = -1;
        //Déterminer la position de la personne qui possède le couronne afin qu'il puisse choisir son personnage en premier
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).isKing())
                roi = i;
        }


        if (roi == -1) {
            //Personne ne possède la carte couronne
            for (int j = 0; j < this.joueurs.size(); j++)
                joueursClone.add(choixCaractere(j));
        } else {
            //Un joueur possède déjà la carte couronne

            //Parcours du Roi à la fin
            for (int j = roi; j < this.joueurs.size(); j++)
                joueursClone.add(choixCaractere(j));

            //Parcours du debut jusqu'au Roi-1
            for (int j = 0; j < roi; j++)
                joueursClone.add(choixCaractere(j));
        }

        this.joueurs = joueursClone;
        displayLeftCards();
    }

    //Afficher la liste des personnages disponibles lors d'un choix de personnage
    private void displayAvailableCharacters() {
        for (int i = 0; i < caracteresDisponibles.size(); i++) {
            System.out.println((i + 1) + " - " + caracteresDisponibles.get(i).getName());
        }
    }

    //Afficher certaines cartes restantes
    private void displayLeftCards() {
        System.out.println("Personnages restants : ");
        int[] randomInts;
        List<Character> leftCharacters = new ArrayList<>();
        for (int i = 0; i < gameData.getCharacters().size(); i++) {
            if (!caracteresChoisis.contains(gameData.getCharacters().get(i).getPosition()))
                leftCharacters.add(gameData.getCharacters().get(i));
        }

        switch (leftCharacters.size()) {
            case 0:
                System.out.println("Tout les personnages sont pris");
                break;
            case 1:
                System.out.println(leftCharacters.get(0).getName() + " - affiché");
                break;
            case 2:
                randomInts = new Random().ints(0, 2).distinct().limit(1).toArray();
                System.out.println(leftCharacters.get(randomInts[0]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                break;
            case 3:
                randomInts = new Random().ints(0, 3).distinct().limit(2).toArray();
                System.out.println(leftCharacters.get(randomInts[0]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                System.out.println(leftCharacters.get(randomInts[1]).getName() + " - affiché");
                break;
            case 4:
                randomInts = new Random().ints(0, 4).distinct().limit(2).toArray();
                System.out.println(leftCharacters.get(randomInts[0]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                System.out.println(leftCharacters.get(randomInts[1]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                break;
            case 5:
                randomInts = new Random().ints(0, 5).distinct().limit(3).toArray();
                System.out.println(leftCharacters.get(randomInts[0]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                System.out.println(leftCharacters.get(randomInts[1]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                System.out.println(leftCharacters.get(randomInts[2]).getName() + " - affiché");
                break;
            case 6:
                randomInts = new Random().ints(0, 6).distinct().limit(3).toArray();
                System.out.println(leftCharacters.get(randomInts[0]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                System.out.println(leftCharacters.get(randomInts[1]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                System.out.println(leftCharacters.get(randomInts[2]).getName() + " - affiché");
                System.out.println("Personnage inconnu - caché");
                break;
        }
    }

    //Choisir un personnage pour chaque tour
    private Joueur choixCaractere(int index) {
        System.out.println(joueurs.get(index).getName() + " : Veuillez choisir un personnage : ");
        displayAvailableCharacters();
        System.out.println("Choix : ");
        int input = Utils.scanner.nextInt();
        Utils.scanner.nextLine();
        while (input < 1 || input > caracteresDisponibles.size()) {
            System.out.println("Veuillez entrer un choix valide");
            input = Utils.scanner.nextInt();
            Utils.scanner.nextLine();
        }

        this.joueurs.get(index).setCharacter(caracteresDisponibles.get(input - 1));
        caracteresDisponibles.remove(input - 1);
        caracteresChoisis.add(this.joueurs.get(index).getCharacter().getPosition());


        //Si on a un joueur qui a choisi la personnage Roi, lui donner la carte couronne
        if (this.joueurs.get(index).getCharacter().getPosition() == 4) {
            for (int i = 0; i < this.joueurs.size(); i++)
                this.joueurs.get(i).setKing(false);
            this.joueurs.get(index).setKing(true);
        }

        return this.joueurs.get(index);
    }

    //Determiner si le jeu est terminé
    private boolean gameEnded() {
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur joueur = this.joueurs.get(i);
            int nbCons = joueur.getConstructions().size();
            int consPrice = 0;
            for (int j = 0; j < nbCons; j++) {
                consPrice += joueur.getConstructions().get(j).getPrice();
            }
            playerDatas.add(new PlayerData(nbCons, joueur.getCoins(), consPrice));
        }

        //Terminer le jeu si un joueur a au moins 8 biens construits dans son cité
        for (int i = 0; i < playerDatas.size(); i++) {
            if (playerDatas.get(i).getNbCons() >= 8) {
                return true;
            }

        }
        return false;
    }

    //Démarrer une partie
    private void startGame() {
        instantiateGame();
        tour();
    }

    public static void main(String args[]) {
        gameData = new GameData("assets/jsonData.json");

        if (gameData.readCSV())
            System.out.println("Data read successfully");

        System.out.println("Veuillez choisir le nombre de joueurs entre 2 et 8");
        int nbJoueurs = Utils.scanner.nextInt();
        Utils.scanner.nextLine();
        while (nbJoueurs < 2 || nbJoueurs > 8) {
            System.out.println("Choix invalide");
            nbJoueurs = Utils.scanner.nextInt();
            Utils.scanner.nextLine();
        }
        Game game = new Game(nbJoueurs);
        game.startGame();
    }


}
