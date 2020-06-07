package BuisinessLayer.LogicalOperations;

public class Score {

    //Frields
    private int homeScore;
    private int awayScore;

    public Score() {
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
