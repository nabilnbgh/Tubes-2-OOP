

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
    private  final int movementTurn = 5;
    private final Point[] direction = {new Point(0,1), new Point(1,0), new Point(0,-1), new Point(-1, 0)};
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
                this.map_matrix.add(col);
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
        return this.map_matrix.get(y).get(x);
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
        int x = rand.nextInt(this.map_matrix.size());
        int y = rand.nextInt(this.map_matrix.get(0).size());
        while (this.getCell(x,y).isBlocked()){
            x = rand.nextInt(this.map_matrix.size());
            y = rand.nextInt(this.map_matrix.get(0).size());
        }
        Cell c = this.getCell(x,y);
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
        Cell C = this.getCell(e);
        C.removeEngimon();
        EngimonCount--;
    }

    public Point getPlayerPos(){
        return PlayerPos;
    }

    public Point getActivePos() {
        return ActivePos;
    }

    public void movePlayer(Point dest) throws Exception{
        Cell c = this.getCell(dest);
        if(!c.isBlocked()){
            this.getCell(PlayerPos).setPlayer(false);
            c.setPlayer(true);
            this.ActivePos = this.PlayerPos;
            this.PlayerPos = dest;
            this.nextTurn();
        }else{
            throw new Exception("Cannot move, there is Engimon");
        }
    }

    public void moveEngimon(Cell c, Point dest){
        Cell c1 = this.getCell(dest);
        if (!c1.isBlocked() && !c1.active && c1.canMove(c.getEngimon())){
            c1.addEngimon(c.getEngimon());
            c.removeEngimon();
        }
    }

    public ArrayList<Cell> getWildCells(){
        ArrayList<Cell> arr = new ArrayList<>();
        for (ArrayList<Cell> ac : this.map_matrix){
            for (Cell c : ac){
                if (c.getEngimon() != null){
                    arr.add(c);
                }
            }
        }
        return arr;
    }

    public void moveWildEngimons(ArrayList<Cell> wildCells){
        Random rand = new Random();
        for (Cell c : wildCells){
            Point p = this.direction[rand.nextInt(4)];
            Point p1 = Point.add(c.getPosisi(), p);
            if (checkBound(p1) && !this.getCell(p1).isBlocked() && !this.getCell(p1).isActive())
            this.moveEngimon(c, p1);
        }
    }

    public boolean checkBound(Point P){
        return P.get_y()>= 0 && P.get_y()<this.map_matrix.size() && P.get_x() >= 0 && P.get_x() < this.map_matrix.get(0).size();
    }
    public void levelUpEngimons(ArrayList<Cell> wildCells){
        if (wildCells != null){
            for (Cell c : wildCells){
                c.getEngimon().changeExp(100);
            }
        }
    }

    public void nextTurn(){
        if (turnCounter == 0){
            ArrayList<Cell> wildCells = this.getWildCells();
            if (wildCells.size()>0) {
                levelUpEngimons(wildCells);
                moveWildEngimons(wildCells);
            }
            if (EngimonCount < maxEngimon){
                this.spawnEngimon();
            }
            turnCounter = movementTurn;
        }else{
            turnCounter--;
        }
    }

    public ArrayList<Engimon> getNearbyEngimon(){
        ArrayList<Engimon> engimons = new ArrayList<>();
        for (Point p : this.direction){
            Cell c = getCell(Point.add(p, this.getPlayerPos()));
            if (c.engimon != null){
                engimons.add(c.getEngimon());
            }
        }
        return engimons;
    }


}

