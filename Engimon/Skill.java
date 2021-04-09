package Engimon;

import java.util.*;

public class Skill {
    // Atribut
    protected String skillName;
    protected int basePower;
    protected int masteryLevel;
    protected ArrayList<String> elements; // array of element yang boleh mempelajari skill ini

    public Skill(String skillname, int basePower, int masteryLevel, ArrayList<String> NewElements) {
        this.skillName = skillname;
        this.basePower = basePower;
        this.masteryLevel = masteryLevel;
        this.elements = new ArrayList<String>();
        this.elements.addAll(NewElements);
    }

    public String getSkillName(){
        return this.skillName;
    }

    public int getBasePower(){
        return this.basePower;
    }

    public int getMasteryLevel(){
        return this.masteryLevel;
    }

    public void setMasteryLevel(int newMasteryLevel){
        this.masteryLevel = newMasteryLevel;
    }

    public ArrayList<String> getElements(){
        ArrayList<String> temp = new ArrayList<String>();
        temp.addAll(this.elements);
        return temp;
    }

    public boolean IsEqual (Skill otherSkill){
        return (this.skillName == otherSkill.getSkillName() && this.masteryLevel == otherSkill.getMasteryLevel());
    }
}

