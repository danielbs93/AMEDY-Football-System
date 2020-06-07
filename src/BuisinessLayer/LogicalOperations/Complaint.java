package BuisinessLayer.LogicalOperations;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Complaint
{
    private static AtomicInteger ID_Counter = new AtomicInteger(0);

    private Fan fanComplainer;
    private SystemManager systemManagerHandle;
    private Date complaintSentDate;
    private Date complaintHandledDate;

    private String subject;
    private String content;
    private String SystemManagerAnswer;

    private int ComplaintID;

    public Complaint(Fan fanComplainer, String subject, String content)
    {
        this.fanComplainer = fanComplainer;
        this.subject = subject;
        this.content = content;
        this.complaintSentDate = new Date();
        this.ComplaintID = ID_Counter.incrementAndGet();
    }

    public int getComplaintID() {
        return ComplaintID;
    }

    public int getFanComplainerID() {
        return fanComplainer.getId();
    }

    public void setSystemManagerHandle(SystemManager systemManagerHandle) {
        this.systemManagerHandle = systemManagerHandle;
    }

    public void setSystemManagerAnswer(String systemManagerAnswer) {
        SystemManagerAnswer = systemManagerAnswer;
    }

    public String getSystemManagerAnswer() {
        return SystemManagerAnswer;
    }
}
