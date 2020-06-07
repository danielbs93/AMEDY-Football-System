package BuisinessLayer.LogicalOperations;

import java.util.concurrent.atomic.AtomicInteger;

public class Profile {

    private static AtomicInteger ID_Counter = new AtomicInteger(0);
    //Fields
    protected int id;
    protected String name;
    protected String ClassType;
    protected User myUser;


    public Profile(String name, String classtype) {
        this.name = name;
        this.ClassType = classtype;
        this.id = ID_Counter.get();
        ID_Counter.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.ClassType;
    }

    public void setType(String type) {
        this.ClassType = type;
    }

    public int getId() {
        return id;
    }

    public void setUser(User myuser) {
        this.myUser = myuser;
    }

    protected AMEDYSystem getAMEDYSystem() {
        return this.myUser.getAMEDYSystem();
    }

    public User getMyUser() {
        return myUser;
    }



}
