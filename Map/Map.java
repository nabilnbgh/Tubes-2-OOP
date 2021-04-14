

import Engimon.Engimon;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.io.*;

public class Map
{
    private ArrayList<ArrayList<Cell>> map_matrix;
    private Point PlayerPos;
    private  Point ActivePos;
    private  int EngimonCount = 0;
    private  int turnCounter = 0;
    private  int movementTurn = 5;
    private static int maxEngimon = 6;
    private static  int minSpawnLevel = 1;


    public Map(String file)
    {
        try {
            // read in the data
            this.map_matrix = new ArrayList<>();
            Scanner input = new Scanner(new File(file));
            int j = 0;
            while(input.hasNextLine())
            {

                ArrayList<Cell> col = new ArrayList<>();
                int i = 0;
                CharacterIterator it = new StringCharacterIterator(input.nextLine());
                while(it.current() != CharacterIterator.DONE)
                {
                    switch (it.current()) {
                        case 'M': 
                            col.add(new MountainsCell(i, j));
                            break;
                        case 'G': 
                            col.add(new GrasslandCell(i, j));
                            break;
                        case 'S': 
                            col.add(new SeaCell(i, j));
                            break;
                        case 'T': 
                            col.add(new TundraCell(i, j));
                            break;
                        default:
                    }
                    i++;
                    it.next();
                }
                this.map_matrix.add(0, col);
                j++;
            }
            this.PlayerPos = new Point(5,5);
            this.ActivePos = new Point(4,5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<ArrayList<Cell>> getMap(){
        return this.map_matrix;
    }

    public Cell getCell(int x, int y) {
        return this.map_matrix.get(this.map_matrix.size() - y-1).get(x);
    }

    public Cell getCell(Point p){
        return  getCell(p.get_x(), p.get_y());
    }

    public Cell getCell(Engimon e) {
        for (ArrayList<Cell> ac : this.map_matrix){
            for (Cell c : ac){
                if (c.getEngimon() == e){
                    return c;
                }
            }
        }
        return null;
    }

    public void spawnEngimon(){
        Random rand = new Random();
        Cell c;
        do{
            int x = rand.nextInt(this.map_matrix.size());
            int y = rand.nextInt(this.map_matrix.get(0).size());
            c = this.getCell(x,y);
        }while (c.isBlocked());
        c.spawnEngimon();
        //set level engimon
        int bound = (int) (minSpawnLevel*1.5);
        int level = rand.nextInt(bound);
        c.getEngimon().setLevel(minSpawnLevel + level);
        EngimonCount++;
    }

    public static void setMaxEngimon(int x){
        maxEngimon = x;
    }

    public static void setMinSpawnLevel(int x){
        minSpawnLevel = x;
    }

    public void removeEngimon(Engimon e){
        Cell C = getCell(e);
        C.removeEngimon();
        EngimonCount--;
    }


}

