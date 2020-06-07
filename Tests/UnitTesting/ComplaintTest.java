package UnitTesting;

import BuisinessLayer.LogicalOperations.Complaint;
import BuisinessLayer.LogicalOperations.Fan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplaintTest
{
    private static Complaint firstComplaint;
    private static Fan firstFan;


    @BeforeEach
    void initial()
    {
        this.firstFan = (Fan) myFactory.Generate("fan", "Aviv");
    }

    @Test
    void test_getFanComplainerID()
    {
        this.firstComplaint = new Complaint(this.firstFan, "aaaaaaaaa", "bbbbbbbbbbbbbb");
        assertEquals(this.firstFan.getId() ,this.firstComplaint.getFanComplainerID());
    }
}
