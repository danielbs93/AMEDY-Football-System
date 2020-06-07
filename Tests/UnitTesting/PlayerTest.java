package UnitTesting;

import BuisinessLayer.LogicalOperations.Pair;
import BuisinessLayer.LogicalOperations.Player;
import BuisinessLayer.LogicalOperations.PlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static Player player;

    @BeforeAll
    static void setUp() {
        player = new Player("Daniel",new Date(1993,4,9), PlayerType.Defender);
    }

    @Test
    void getUserType() {

        assertTrue(player.getPlayerType().equals(PlayerType.Defender));
        assertFalse(player.getPlayerType().equals(PlayerType.GoalKeeper));
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> PersonalInfo = this.player.getPersonalInfo();
        for(Pair currentPair : PersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("Player", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals(currentPair.getValue(), "Daniel");
            }
            else if(currentPair.getKey().equals("birthday"))
            {
                assertEquals(currentPair.getValue(),"09/05/3893");
            }
            else if(currentPair.getKey().equals("PlayerType"))
            {
                assertEquals(currentPair.getValue(), "Defender");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }

}