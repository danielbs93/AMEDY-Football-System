package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;


import java.net.ConnectException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class myFactory {

    public static Object Generate(String profilE, String namE) {
        String profile = profilE.toLowerCase();
        String name = namE.toLowerCase();
        if(profile.equals("player")) {
            return new Player(name,generateNewDate(), PlayerType.Defender);
        }
        else if(profile.equals("coach")) {
            return new Coach(name,"1", "1");
        }
        else if(profile.equals("referee")) {

            if (name.equals(RefereeType.MainReferee.toString().toLowerCase()))
                return new Referee(name,RefereeType.MainReferee);

            if (name.equals(RefereeType.SideReferee.toString().toLowerCase()))
                return new Referee(name,RefereeType.SideReferee);
        }
        else if(profile.equals("stadium")) {
            return new Stadium(name);
        }
        else if(profile.equals("league")) {

            if (name.equals(LeagueRank.league1.toString()))
                return new League(LeagueRank.league1);

            if (name.equals(LeagueRank.league2.toString()))
                return new League(LeagueRank.league2);

            if (name.equals(LeagueRank.league3.toString()))
                return new League(LeagueRank.league3);
        }
        else if(profile.equals("team")) {

            List<Player> players = new ArrayList<>();

            for (int i = 0; i < 11; i++) {
                players.add((Player)Generate("player",name + "name"+i));
            }

            Coach coach = (Coach)Generate("coach",name + " coach1");
            League league = (League)Generate("league",name + " league1");
            Stadium stadium = (Stadium)Generate("stadium",name + " stadium1");
            return new Team(name,players,coach,league,stadium);
        }
        else if(profile.equals("fan")) {
            return new Fan(name);
        }
        else if(profile.equals("teammanager")) {
            return new TeamManager(name,(Team)Generate( "team",name + " TeamManagerTeam"));
        }
        else if(profile.equals("teamowner")) {
            return new TeamOwner(name,(Team)Generate( "team",name + " TeamOwnerTeam"));
        }
        else if(profile.equals("leaguerankpolicy")) {
            return new LeagueRankPolicy() {
                @Override
                public Double calcRank(Team team) {
                    return null;
                }

                @Override
                public boolean equals(LeagueRankPolicy leagueRankPolicy) {
                    return false;
                }

                @Override
                public String getName() {
                    return null;
                }
            };
        }
        else if(profile.equals("authentication")) {
            try {
                return new Authentication(null);
            } catch (ConnectException e) {
                e.printStackTrace();
            }
        }
        else if(profile.equals("amedysystem")) {
            return new AMEDYSystem((Authentication)Generate("authentication",""));
        }
        else if(profile.equals("season")) {
            return new Season(Integer.parseInt(name),null,(LeagueRankPolicy)Generate("leagueRankPolicy",""));
        }

        else if(profile.equals("event")) {
            if (name.equals(EventType.Foul.toString().toLowerCase()))
                return new Event(generateNewDate(), LocalTime.now(), 15, EventType.Foul, (Player)Generate("player",name+"player " + Math.random()*1000),"bla bla");
            if (name.equals(EventType.Goal.toString().toLowerCase()))
                return new Event(generateNewDate(), LocalTime.now(), 15, EventType.Goal, (Player)Generate("player",name+"player " + Math.random()*1000),"bla bla");
            if (name.equals(EventType.Injury.toString().toLowerCase()))
                return new Event(generateNewDate(), LocalTime.now(), 15, EventType.Injury, (Player)Generate("player",name+"player " + Math.random()*1000),"bla bla");
            if (name.equals(EventType.Offside.toString().toLowerCase()))
                return new Event(generateNewDate(), LocalTime.now(), 15, EventType.Offside, (Player)Generate("player",name+"player " + Math.random()*1000),"bla bla");
            if (name.equals(EventType.RedCard.toString().toLowerCase()))
                return new Event(generateNewDate(), LocalTime.now(), 15, EventType.RedCard, (Player)Generate("player",name+"player " + Math.random()*1000),"bla bla");
            if (name.equals(EventType.Extension.toString().toLowerCase()))
                return new Event(generateNewDate(), LocalTime.now(), 15, EventType.Extension, (Player)Generate("player",name+"player " + Math.random()*1000),"20");
        }
        else if(profile.equals("eventdiary")) {
            return new EventDiary(generateNewDate(), (Match) Generate("match", name));
        }
        else if(profile.equals("rfa")) {
            return new RepresentativeFootballAssociation(name);
        }
        else if(profile.equals("systemmanager")) {
            AMEDYSystem system = (AMEDYSystem)myFactory.Generate("AMEDYSystem", "AMEDYSystem");

            return new SystemManager("username","password", name, system);
        }
        else if (profile.equals("match")) {
            Referee[] referees = {(Referee)Generate("referee","MainReferee"),(Referee)Generate("referee","SideReferee")};
            try {
                return new Match(
                        generateNewDate(),
                        LocalTime.now(),
                        (Stadium)Generate("stadium",name + " stadium"),
                        (Team)Generate("team",name + "1"),
                        (Team)Generate("team",name + "2"),
                        referees
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static Date generateNewDate() {
        int year = (int) Math.random()*2000;
        int month = (int) Math.random()*12;
        int day = (int) Math.random()*31;
        return new Date(year,month,day);
    }

}
