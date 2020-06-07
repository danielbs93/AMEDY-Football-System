package UnitTesting;

import BuisinessLayer.LogicalOperations.Coach;
import BuisinessLayer.LogicalOperations.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoachTest
{
    private static Coach coach;


    @BeforeEach
    void initial()
    {
        coach = (Coach) myFactory.Generate("coach", "Aviv");
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> PersonalInfo = this.coach.getPersonalInfo();
        for(Pair currentPair : PersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("Coach", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals("aviv", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("qualification"))
            {
                assertEquals(currentPair.getValue(),"1");
            }
            else if(currentPair.getKey().equals("role"))
            {
                assertEquals(currentPair.getValue(), "1");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }
}
