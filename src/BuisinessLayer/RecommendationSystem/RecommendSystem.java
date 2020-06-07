package BuisinessLayer.RecommendationSystem;

import BuisinessLayer.LogicalOperations.Match;

public class RecommendSystem {

    public  RecommendSystem() {
    }

    /**
     * Recommend witch team to bet according the the teams budget, normalization values between 0-1
     * @param match
     * @return array[0] - HomeTeam chance to win, array[1] - chance to draw, array[2] - AwayTeam Chance to win
     *
     */
    public double [] recommend(Match match){
        double [] gamblingRatio = new double[3];
        double maxBudget;
        double homeBudget = match.getHomeTeam().getSeasonBudget().get(0).getValue().getAmount();
        double awayBudget = match.getAwayTeam().getSeasonBudget().get(0).getValue().getAmount();

        if (homeBudget>awayBudget)
            maxBudget = homeBudget;
        else
            maxBudget = awayBudget;
        //Chance of homeTeam to win
        gamblingRatio[0] = homeBudget/maxBudget - 0.01 ;
        //Chance of draw
        gamblingRatio[1] = (homeBudget+awayBudget)/(2*maxBudget);
        //Chance of awayTeam tto win
        gamblingRatio[2] = awayBudget/maxBudget - 0.01;
        return gamblingRatio;
    }
}
