package UnitTesting;

import BuisinessLayer.LogicalOperations.Fan;
import BuisinessLayer.LogicalOperations.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTest
{

    private static Match firstMatch;
    private static Match secondMatch;
    private static Fan firstFan;
    private static Fan secondFan;


    @BeforeEach
    void initial()
    {
        firstMatch = (Match) myFactory.Generate("match", "");
        secondMatch = (Match) myFactory.Generate("match", "");
        firstFan = (Fan) myFactory.Generate("fan", "Aviv");
        secondFan = (Fan) myFactory.Generate("fan", "Yarden");

//        DataBaseReturnTrueAndAdding DB = new DataBaseReturnTrueAndAdding();
//        DB.initialDBStruct();
//
//        new CRUD(DB);
    }

    @Test
    void test_addFanObserver()
    {
        assertEquals(true, this.firstMatch.addFanObserver(this.firstFan));
        assertEquals(false, this.firstMatch.addFanObserver(this.firstFan));

        assertEquals(true, this.firstMatch.addFanObserver(this.secondFan));
        assertEquals(false, this.firstMatch.addFanObserver(this.secondFan));
    }

    @Test
    void test_isFanObserverExist()
    {
        this.firstMatch.addFanObserver(this.firstFan);
        this.firstMatch.addFanObserver(this.secondFan);

        assertEquals(true, this.firstMatch.isFanObserverExist(this.firstFan));
        assertEquals(true, this.firstMatch.isFanObserverExist(this.secondFan));

        this.secondMatch.addFanObserver(this.firstFan);

        assertEquals(true, this.secondMatch.isFanObserverExist(this.firstFan));
        assertEquals(false, this.secondMatch.isFanObserverExist(this.secondFan));
    }

}
