package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileEventMakerTest
{

    private static Fan firstFan;
    private static Fan secondFan;
    private static Fan thirdFan;

    private static Player player;
    private static Coach coach;
    private static Referee referee;
    private static TeamOwner teamOwner;


    @BeforeEach
    void initial()
    {
        this.firstFan = (Fan) myFactory.Generate("fan", "Aviv");
        this.secondFan = (Fan) myFactory.Generate("fan", "Yarden");
        this.thirdFan = (Fan) myFactory.Generate("fan", "Michal");
        this.player = (Player) myFactory.Generate("player", "Daniel");
        this.coach = (Coach) myFactory.Generate("coach","Eran");
        this.referee = (Referee) myFactory.Generate("referee", "MainReferee");
        this.teamOwner = (TeamOwner) myFactory.Generate("TeamOwner", "Noa");
    }

    @Test
    void test_addFanObserver()
    {
        assertEquals(true, this.player.addFanObserver(this.firstFan));
        assertEquals(false, this.player.addFanObserver(this.firstFan));

        assertEquals(true, this.coach.addFanObserver(this.firstFan));
        assertEquals(true, this.coach.addFanObserver(this.secondFan));
        assertEquals(false, this.coach.addFanObserver(this.firstFan));
        assertEquals(false, this.coach.addFanObserver(this.secondFan));

        assertEquals(true, this.teamOwner.addFanObserver(this.firstFan));
        assertEquals(true, this.teamOwner.addFanObserver(this.secondFan));
        assertEquals(true, this.teamOwner.addFanObserver(this.thirdFan));
        assertEquals(false, this.teamOwner.addFanObserver(this.firstFan));
        assertEquals(false, this.teamOwner.addFanObserver(this.secondFan));
        assertEquals(false, this.teamOwner.addFanObserver(this.thirdFan));
    }

    @Test
    void test_isFanObserverExist()
    {
        this.referee.addFanObserver(this.firstFan);
        this.referee.addFanObserver(this.secondFan);

        assertEquals(true, this.referee.isFanObserverExist(this.firstFan));
        assertEquals(true, this.referee.isFanObserverExist(this.secondFan));
        assertEquals(false, this.referee.isFanObserverExist(this.thirdFan));

        assertEquals(false, this.player.isFanObserverExist(this.firstFan));


    }
}
