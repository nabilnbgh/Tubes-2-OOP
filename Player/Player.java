import java.util.Scanner;

public class Player {
    private String nama;
    private Engimon active_engimon;               
    private Point position;                       
    private Point position_active_engimon;
    private Inventory<Engimon> engimon_as_object; 
    private Inventory<SkillItem> skill_as_object;

    public Player() {
        this.nama = "Traveler";
        this.position = new Point(5,5);
        this.position_active_engimon = new Point(5,4);
        this.active_engimon = new Pikachu();
        this.active_engimon.setLevel(20);
    }
    public Player(String nama) {
        this.nama = nama;
        this.position = new Point(5,5);
        this.position_active_engimon = new Point(5,4);
        this.active_engimon = new Pikachu();
        this.active_engimon.setLevel(20);
    }
    public void switch_out_engimon_meninggal() {
        if(engimon_as_object.getCount() != 0){
            System.out.println("Berikut adalah daftar engimon yang anda punya: ");
            display_list_engimon();
            System.out.print("Pilih engimon nomor berapa untuk menggantikan active engimon: ");
            int idx;
            Scanner S=new Scanner(System.in); 
            idx=S.nextInt();
            System.out.println("");
            if(engimon_as_object.engimonMeninggal(idx, active_engimon)){
                System.out.println("Berhasil menggantikan active engimon");
            } else{
                System.out.println("Gagal menggantikan active engimon");
            }
        } else{
            System.out.println("Anda tidak mempunyai engimon pada inventory");
        }
    }
    public int getTotalInventory() {
        return skill_as_object.getCount() + engimon_as_object.getCount();
    }
    public void setActiveEngimon(Engimon e) {
        this.active_engimon = e;
    }
    public void move(Peta map, char direction) {
        Point P1 = new Point(0,1);
        Point P2 = new Point(1,0);
        Point P3 = new Point(0,-1);
        Point P4 = new Point(-1,0);
        try{
            if (direction == 'W' || direction == 'w')
            {
                map.MovePlayer(map.getPlayerPosition()+P3);
            }
            else if (direction == 'A' || direction == 'a')
            {
                map.MovePlayer(map.getPlayerPosition()+P4);
            }
            else if (direction == 'S' || direction == 's')
            {
                map.MovePlayer(map.getPlayerPosition()+P1);
            }
            else if (direction == 'D' || direction == 'd')
            {
                map.MovePlayer(map.getPlayerPosition()+P2);
            } else{
                System.out.println("Input direction tidak valid");
            }
        } catch(mapException m){};
        this.position = map.getPlayerPosition();
        this.position_active_engimon = map.getActiveEngimonPosition();
        map.print();
    }
    public void display_list_engimon() {
        System.out.println(" List engimon yang dimiliki : ");
        Engimon le = this.engimon_as_object.getObject();
        for (int i = 0; i < this.engimon_as_object.getCount(); i++)
        {
            System.out.println((i + 1) + ". " + le[i].getName());
        }
    }
    public void display_info_engimon() {
        int idx;
        System.out.print("Pilih engimon mana yang ingin anda lihat infonya \n" + "1. Active Engimon\n" + "2. Engimon di inventory\n" + "Pilih (1/2): ");
        Scanner S=new Scanner(System.in); 
        idx=S.nextInt();
        System.out.println("");
        if(idx == 1){
            this.active_engimon.printDetail();
        } else{
            this.display_list_engimon();
            System.out.print("Pilih engimon nomor berapa yang ingin anda lihat infonya: ");
            idx=S.nextInt();
            this.engimon_as_object[idx].printDetail();
        }
    }
    public string get_active_engimon() {
        return this.active_engimon.getName();
    }
    public void switch_out_active_engimon() {
        if(this.engimon_as_object.getCount() != 0){
            System.out.println("Berikut adalah daftar engimon yang anda punya: ");
            this.display_list_engimon();
            System.out.print("Pilih engimon nomor berapa yang akan ditukar: ");
            int idx;
            Scanner S=new Scanner(System.in); 
            idx=S.nextInt();
            System.out.println("");
            if(this.engimon_as_object.switchEngimon(idx, this.active_engimon)){
                System.out.println("Berhasil menukar active engimon");
            } else{
                System.out.println("Gagal menukar active engimon");
            }
        } else{
            System.out.println("Anda tidak mempunyai engimon pada inventory");
        }
    }
    public void display_list_skill() {
        System.out.println(" List skill item yang dimiliki : ");
        SkillItem le = this.skill_as_object.getObject();
        for (int i = 0; i < this.skill_as_object.getCountIdx(); i++)
        {
            System.out.println((i + 1) + ". " + le[i].getNama());
        }
    }
    public void use_skill() {
        if(this.skill_as_object.getCount() == 0){
            System.out.println("Anda tidak mempunyai skill item di inventory");
        } else{
            this.display_list_skill();
            System.out.print("Pilih nomor skill item yang ingin anda gunakan pada suatu engimon: ");
            int idxSI;
            Scanner S=new Scanner(System.in); 
            idxSI=S.nextInt();
            System.out.println("");
            System.out.print("Pilih engimon mana yang ingin anda gunakan skill item: \n1. Active Engimon\n2. Engimon di inventory\nPilih nomor (1/2): ");
            int idxE;
            idxE=S.nextInt();
            System.out.println("");
            if(idxE == 1){
                engimon temp = this.active_engimon;;
                if(this.skill_as_object.learn(temp, idxSI)){
                    this.setActiveEngimon(temp);
                    System.out.println("Berhasil melakukan learn skill item ^_^");
                } else{
                    System.out.println("Gagal melakukan learn skill item :{ ");
                }
            } else if(idxE == 2){
                this.display_list_engimon();
                System.out.println("Pilih engimon nomor berapa yang ingin anda lakukan learn skill item: ");
                idxE=S.nextInt();
                System.out.println("");
                Engimon temp = this.engimon_as_object[idxE];
                if(this.skill_as_object.learn(temp, idxSI)){
                    this.engimon_as_object.setEngimon(idxE, temp);
                    System.out.println("Berhasil melakukan learn skill item ^_^");
                } else{
                    System.out.println("Gagal melakukan learn skill item :{ ");
                }   
            }  
        }
    }
    public void breeding(Engimon e1, Engimon e2) {
        Engimon Child = new Engimon();
        if (this.engimon_as_object.stored(e1) && this.engimon_as_object.stored(e2))
        {
            try
            {
                Child = startBreeding(e1, e2);
                System.out.print("Ingin memberi nama child?(Y/N)");
                char choice;
                String name;
                Scanner S=new Scanner(System.in); 
                choice=S.next().charAt(0);
                if(choice == 'Y')
                {
                    System.out.print("Masukkan nama child: ");
                    name = S.nextLine();
                    Child.setName(name);
                }
                Child.printDetail();
                if(this.getTotalInventory() < this.engimon_as_object.getMaxCapacity()){
                    this.engimon_as_object.insert(Child);
                } else{
                    System.out.println("Maaf inventory penuh :(");
                }
            }
            catch(BreedingException e)
            {
                System.out.println(e.what());
            }
        } 
        else
        {
            System.out.println("Gagal melakukan breeding, engimon tidak terdapat pada inventory");
        }
    }
    public SkillItem random_generator_skill(List<String> element) {
        List<SkillItem> list_of_skill = new List<SkillItem>();
        SkillItem tsunami = new SkillItem("Tsunami",120,1,Arrays.asList("Water"));
        SkillItem fireball = new SkillItem("FireBall",120,1,Arrays.asList("Fire"));
        SkillItem freezing = new SkillItem("Freezing field",120,1,Arrays.asList("Ice"));
        SkillItem fissure = new SkillItem("Fissure",120,1,Arrays.asList("Ground"));
        SkillItem thunder = new SkillItem("Thunder God",120,1,Arrays.asList("Electric"));
        SkillItem cold = new SkillItem("Cold Embrace",150,1,Arrays.asList("Water","Ice"));
        SkillItem watershock = new SkillItem("Watershock",150,1,Arrays.asList("Water","Ground"));
        SkillItem overload = new SkillItem("Overload",150,1,Arrays.asList("Fire","Electric"));
        list_of_skill.push_back(tsunami);
        list_of_skill.push_back(fireball);
        list_of_skill.push_back(freezing);
        list_of_skill.push_back(fissure);
        list_of_skill.push_back(thunder);
        list_of_skill.push_back(cold);
        list_of_skill.push_back(watershock);
        list_of_skill.push_back(overload);
        for (SkillItem it : list_of_skill)
        {
            if(it.getElements() == element)
            {
                return it;
            }
        }
    }
    public void battle(Peta map, bool run) {
        Point P1 = new Point(0, 1);
        Point P2 = new Point(1, 0);
        Point P3 = new Point(0, -1);
        Point P4 = new Point(-1, 0);
        List<Point> LP = new List<Point>();
        LP.push_back(map.getPlayerPosition() + P1);
        LP.push_back(map.getPlayerPosition() + P2);
        LP.push_back(map.getPlayerPosition() + P3);
        LP.push_back(map.getPlayerPosition() + P4);
        List<Engimon> LE = new List<Engimon>();
        for(Point pi : LP){
            if(pi.get_x()>=0 && pi.get_y()>=0 && pi.get_x()<=map.getMatriksSizex()-1 && pi.get_y() <= map.getMatriksSizey()-1){
                if(!(pi == this.position_active_engimon)){
                    Engimon etemp = new Engimon();
                    etemp = map.GetCell(pi).getEngimon();
                    if (etemp != nullptr){
                        LE.push_back(etemp);
                    }
                }
            }
        }
        
        if(LE.size()>0){
            if(LE.size() > 1){
                System.out.println("Terdapat " + LE.size() + " engimon musuh disekitar");
                int i = 1;
                for(Engimon e:LE){
                    System.out.println("[" + i + "] " + e.getName());
                    e.printDetail();
                    i++;
                }
                System.out.print("Pilih engimon nomor berapa yang ingin dilawan: ");
                int idx;
                Scanner S=new Scanner(System.in); 
                idx=S.nextInt();// Asumsi input selalu valid
                i = 1;
                for(Engimon e:LE){
                    if(i == idx){
                        System.out.println("Anda akan melawan engimon " + e.getName());
                        e.printDetail();
                        Battle b = new Battle(this.active_engimon, e);
                        System.out.println("Power level player: " + b.calculatePowerPlayer());
                        System.out.println("Power level enemy: " + b.calculatePowerEnemy());
                        if(b.calculatePowerPlayer() >= b.calculatePowerEnemy()){
                            System.out.println("You win ^_^");
                            System.out.print("Apakah anda ingin memasukkan engimon musuh ke dalam inventory? (Y/N) ");
                            String c;
                            c = S.nextLine();
                            System.out.println();
                            if(c.equals("Y") || c.equals("y")){
                                if (this.getTotalInventory() < this.engimon_as_object.getMaxCapacity()){
                                    if(this.engimon_as_object.insert(e)){
                                        System.out.println("Berhasil menyimpan engimon " + e.getName() + " ke dalam inventory");
                                    } else{
                                        System.out.println("Gagal menyimpan engimon " + e.getName() + " ke dalam inventory");
                                    }
                                } else{
                                    System.out.println("Inventory penuh");
                                }
                                
                            } else{
                                System.out.println("Tidak menyimpan engimon ke inventory");
                            }
                            this.active_engimon.setCumExp(this.active_engimon.getCumExp() + 25);
                            if (this.active_engimon.getExp() + 25 >= 100){
                                this.active_engimon.setLevel(this.active_engimon.getLevel() + 1);
                                this.active_engimon.setExp((this.active_engimon.getExp() + 25)%100);
                            } else{
                                this.active_engimon.setExp(this.active_engimon.getExp() + 25); // add exp statis 25
                            }
                            map.removeEngimon(e);
                        } else{
                            System.out.println("You lose :(");
                            System.out.println("Engimon anda meninggal dunia");
                            if(this.engimon_as_object.getCount() != 0){
                                this.switch_out_engimon_meninggal();
                            } else{
                                System.out.println( "Anda tidak punya engimon didalam inventory terpaksa anda kalah :(((");
                                run = false;
                            }
                        }
                        break;
                    } else{
                        i++;
                    }
                }
            } else{
                for(Engimon e:LE){
                        System.out.println("Anda akan melawan engimon " + e.getName());
                        e.printDetail();
                        Battle b = new Battle(this.active_engimon, e);
                        System.out.println("Power level player: " + b.calculatePowerPlayer());
                        System.out.println("Power level enemy: " + b.calculatePowerEnemy());
                        if(b.calculatePowerPlayer() >= b.calculatePowerEnemy()){
                            System.out.println("You win ^_^");
                            System.out.print("Apakah anda ingin memasukkan engimon musuh ke dalam inventory? (Y/N) ");
                            String c;
                            c = S.nextLine();
                            System.out.println();
                            if(c.equals("Y") || c.equals("y")){
                                if (this.getTotalInventory() < this.engimon_as_object.getMaxCapacity()){
                                    if(this.engimon_as_object.insert(e)){
                                        System.out.println("Berhasil menyimpan engimon " + e.getName() + " ke dalam inventory");
                                    } else{
                                        System.out.println("Gagal menyimpan engimon " + e.getName() + " ke dalam inventory");
                                    }
                                } else{
                                    System.out.println("Inventory penuh");
                                }
                                
                            } else{
                                System.out.println("Tidak menyimpan engimon ke inventory");
                            }
                            this.active_engimon.setCumExp(this.active_engimon.getCumExp() + 25);
                            if (this.active_engimon.getExp() + 25 >= 100){
                                this.active_engimon.setLevel(this.active_engimon.getLevel() + 1);
                                this.active_engimon.setExp((this.active_engimon.getExp() + 25)%100);
                            } else{
                                this.active_engimon.setExp(this.active_engimon.getExp() + 25); // add exp statis 25
                            }
                            map.removeEngimon(e);
                        } else{
                            System.out.println("You lose :(");
                            System.out.println("Engimon anda meninggal dunia");
                            if(this.engimon_as_object.getCount() != 0){
                                this.switch_out_engimon_meninggal();
                            } else{
                                System.out.println( "Anda tidak punya engimon didalam inventory terpaksa anda kalah :(((");
                                run = false;
                            }
                    }
                }
            }
        } else{
            System.out.println("Battle tidak dapat dilakukan karena tidak ada engimon musuh di dekat anda");
        }
    }
    public void insertSkillItem(SkillItem si) {
        if (this.getTotalInventory() < this.engimon_as_object.getMaxCapacity()){
            this.skill_as_object.insert(si);
        } else{
            System.out.println("Inventory anda penuh");
        }
    }
    
    //tambahan untuk breeding
    public void chooseBreeding() {
        int counter = 1;
        Engimon e1;
        Engimon e2;
        if(this.engimon_as_object.getCount() >= 2)
        {
            while (counter<=2)
            {
                System.out.println("Berikut adalah daftar engimon yang anda punya: ");
                this.display_list_engimon();
                System.out.print("Pilih engimon" + counter + " untuk breeding: ");
                int idx;
                Scanner S=new Scanner(System.in); 
                idx=S.nextInt();
                System.out.println("");
                if(counter==1)
                {
                    e1=this.engimon_as_object[idx];
                } 
                else //counter==2
                {
                    e2=this.engimon_as_object[idx];
                }
                counter++;
            }
            if (e1.getName() != e2.getName())
            {
                breeding(e1,e2);
            }
            else
            {
                System.out.println("Masukan salah untuk breeding");
            }
        }
        else
        {
            System.out.println("Anda tidak mempunyai cukup engimon");
        }
    
    }
}