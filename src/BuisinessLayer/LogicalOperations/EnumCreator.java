package BuisinessLayer.LogicalOperations;


import static BuisinessLayer.LogicalOperations.PlayerType.*;
import static BuisinessLayer.LogicalOperations.RefereeType.*;
import static BuisinessLayer.LogicalOperations.ProfileType.*;

import ServiceLayer.SearchOptionsType;
import static ServiceLayer.SearchOptionsType.name;
import static ServiceLayer.SearchOptionsType.category;
import static ServiceLayer.SearchOptionsType.keyWords;

import ServiceLayer.SearchFilterType;
import static ServiceLayer.SearchFilterType.Team;
import static ServiceLayer.SearchFilterType.Role;
import static ServiceLayer.SearchFilterType.Gender;
import static ServiceLayer.SearchFilterType.League;
import static ServiceLayer.SearchFilterType.Season;

/***
 * This class create a enum instances from string
 */
public class EnumCreator {

    //PlayerType
    public static PlayerType createPlayerType(String type) {

        if(GoalKeeper.toString().toLowerCase().equals(type)) {
            return GoalKeeper;
        }
        else if(Defender.toString().toLowerCase().equals(type)) {
            return PlayerType.Defender;
        }
        else if(Midfielders.toString().toLowerCase().equals(type)) {
            return PlayerType.Midfielders;
        }
        else if(Striker.toString().toLowerCase().equals(type)) {
            return PlayerType.Striker;
        }

        return null;
    }

    //RefereeType
    public static RefereeType createRefereeType(String type) {

        if(MainReferee.toString().toLowerCase().equals(type)) {
            return MainReferee;
        }
        else if(SideReferee.toString().toLowerCase().equals(type)) {
            return SideReferee;
        }

        return null;
    }

    //SearchOption
    public static SearchOptionsType createSearchOption(String type) {

        if(name.toString().toLowerCase().equals(type)) {
            return name;
        }
        else if(category.toString().toLowerCase().equals(type)) {
            return category;
        }
        else if(keyWords.toString().toLowerCase().equals(type)) {
            return keyWords;
        }

        return null;
    }

    //SearchFilters
    public static SearchFilterType createSearchFilter(String type) {

        if(Role.toString().toLowerCase().equals(type)) {
            return Role;
        }
        else if(League.toString().toLowerCase().equals(type)) {
            return League;
        }
        else if(Team.toString().toLowerCase().equals(type)) {
            return Team;
        }
        else if(Season.toString().toLowerCase().equals(type)) {
            return Season;
        }
        else if(Gender.toString().toLowerCase().equals(type)) {
            return Gender;
        }

        return null;
    }

    //ProfileType
    public static ProfileType createProfileType(String type) {

        if(Coach.toString().toLowerCase().equals(type)) {
            return Coach;
        }
        else if(Fan.toString().toLowerCase().equals(type)) {
            return Fan;
        }
        else if(Player.toString().toLowerCase().equals(type)) {
            return Player;
        }
        else if(Referee.toString().toLowerCase().equals(type)) {
            return Referee;
        }
        else if(RepresentativeFootballAssociation.toString().toLowerCase().equals(type)) {
            return RepresentativeFootballAssociation;
        }
        else if(TeamManager.toString().toLowerCase().equals(type)) {
            return TeamManager;
        }
        else if(TeamOwner.toString().toLowerCase().equals(type)) {
            return TeamOwner;
        }

        return null;
    }

}
