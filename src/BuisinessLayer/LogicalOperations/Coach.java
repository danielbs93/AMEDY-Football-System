package BuisinessLayer.LogicalOperations;

import java.util.ArrayList;
import java.util.List;

public class Coach extends ProfileEventMaker
{
    private String role;
    private String qualification;

    public Coach(String name, String qualification, String role) {

        super(name,"Coach");
        this.qualification = qualification;
        this.role = role;
    }


    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    @Override
    public synchronized List<Pair> getPersonalInfo()
    {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "Coach"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        this.allPersonalInfo.add(new Pair("qualification", this.qualification));
        this.allPersonalInfo.add(new Pair("role", this.role));
        return this.allPersonalInfo;
    }

    public String getQualification() {
        return qualification;
    }


    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

}
