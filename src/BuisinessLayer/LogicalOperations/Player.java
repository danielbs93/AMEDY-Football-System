package BuisinessLayer.LogicalOperations;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Player extends ProfileEventMaker
{
    //Fields
    private Date birthday;
    private PlayerType playerType;

    //Connections
    private List<Event> events;



    public Player(String name, Date birthday, PlayerType playerType) {
//        super(userName, password, AMEDYSystem, name);
        super(name,"Player");
        this.birthday = birthday;
        this.playerType = playerType;
        this.events = new LinkedList<>();
    }


    public PlayerType getPlayerType() {
        return this.playerType;
    }
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    @Override
    public List<Pair> getPersonalInfo() {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "Player"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.allPersonalInfo.add(new Pair("birthday", sdf.format(this.birthday)));
        this.allPersonalInfo.add(new Pair("PlayerType", this.playerType.name()));
        return this.allPersonalInfo;
    }
}


