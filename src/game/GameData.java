package game;

import carte.*;
import carte.characters.*;
import carte.characters.Character;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameData {

    //VARIABLES
    private List<Character> characters;
    private List<Bien> biens;
    private String path;

    //Constructeur avec le chemin des données
    public GameData(String path){
        this.path = path;
        this.biens = new ArrayList<>();
        this.characters = new ArrayList<>();
    }

    //Lire le fichier JSON contenant les données
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    //Obtenir la liste de tout les biens dans le jeu
    public List<Bien> getBiens(){
        return this.biens;
    }

    //Obtenir la liste de tout les personnages sur le jeu
    public List<Character> getCharacters(){
        return this.characters;
    }

    //Obtenir un personnage
    public Character getCharacter(int position){
        return this.characters.get(position);
    }

    //Read the CSV file data
    public boolean readCSV(){
        try {
            JSONObject obj = parseJSONFile(path);

            //CONSTRUCTIONS
            JSONArray arr = obj.getJSONArray("CONSTRUCTIONS");
            for (int i = 0; i < arr.length(); i++)
            {
                String name = arr.getJSONObject(i).getString("name");
                int price = arr.getJSONObject(i).getInt("price");
                int numOccur = arr.getJSONObject(i).getInt("num");

                if(arr.getJSONObject(i).has("specialPowerID")){
                    int specPow = arr.getJSONObject(i).getInt("specialPowerID");
                    String desc = arr.getJSONObject(i).getString("desc");
                    biens.add(new Merveille(specPow, name, desc, price, numOccur, false, false));
                } else {
                    int role = arr.getJSONObject(i).getInt("role");
                    biens.add(new Batiment(name, price, role, numOccur, false, false));
                }
            }

            //CHARACTERS
            JSONArray arrChars = obj.getJSONArray("CHARACTERS");
            for (int i = 0; i < arrChars.length(); i++)
            {
                String name = arrChars.getJSONObject(i).getString("name");
                String desc = arrChars.getJSONObject(i).getString("desc");
                switch (i){
                    case 0:
                        characters.add(new Assasin(1, name, desc));
                        break;
                    case 1:
                        characters.add(new Voleur(2, name, desc));
                        break;
                    case 2:
                        characters.add(new Magicien(3, name, desc));
                        break;
                    case 3:
                        characters.add(new Roi(4, name, desc));
                        break;
                    case 4:
                        characters.add(new Eveque(5, name, desc));
                        break;
                    case 5:
                        characters.add(new Marchand(6, name, desc));
                        break;
                    case 6:
                        characters.add(new Architecte(7, name, desc));
                        break;
                    case 7:
                        characters.add(new Condotierre(8, name, desc));
                        break;
                }
            }


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
