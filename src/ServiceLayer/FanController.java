package ServiceLayer;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.AssertAlreadyExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;

import java.util.List;

public class FanController extends UserController {


    /**
     * UseCase 3.2 - follow after ProfileEventMaker.
     * the method add a new ProfileEventMaker that the fan wants to follow after.
     * @param fan - the current fan who want to the follow after the EventMaker.
     * @param profileEventMaker - the ProfileEventMaker that the fan want to follow after.
     * @return true - if adding the EventMaker succeeded. else - return false.
     * @throws IllegalValueException
     * @throws AssertAlreadyExistException
     */
    public static boolean followEventMaker(Fan fan, ProfileEventMaker profileEventMaker) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] entered followEventMaker method.");
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] checking null arguments.");
        if(profileEventMaker == null || fan == null)
        {
            AMEDYSystem.systemLogger.warning("[FanController][followEventMaker] event maker or fan are null.");
            throw new IllegalValueException("at list one of the entities are null.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] passed null argument check.");
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] checking if fan already follows after eventMaker (hold the event maker as observable) and if eventMaker hold the fan as observer.");
        if(fan.isFollowAfterPEM(profileEventMaker) || profileEventMaker.isFanObserverExist(fan)) //PEM = ProfileEventMaker
        {
            AMEDYSystem.systemLogger.warning("[FanController][followEventMaker] fan already hold event maker as an observable or event maker already hold fan as an observer.");
            throw new AssertAlreadyExistException("fan/profile event maker already exist at profile event maker/fan.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] both fan and event maker hold each others.");
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] try adding the event maker to the fan observables list.");
        if(!fan.addNewEventMaker(profileEventMaker))
        {
            AMEDYSystem.systemLogger.warning("[FanController][followEventMaker] failed adding event maker to the the fan observable list.");
            throw new PermissionDeniedException("failed adding event maker to the the fan observable list.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] succeeded adding the event maker to the fan observables list.");
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] try adding the fan to the event maker list.");
        if(!profileEventMaker.addFanObserver(fan))
        {
            AMEDYSystem.systemLogger.warning("[FanController][followEventMaker] failed adding fan to the the event maker observable list.");
            AMEDYSystem.systemLogger.warning("[FanController][followEventMaker] delete event maker from the event fan observers list.");
            fan.removeFollower(profileEventMaker);
            throw new PermissionDeniedException("failed adding event maker to the the fan observable list.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followEventMaker] succeeded following after the event maker.");
        return true;
    }

    /**
     * UseCase 3.3 - follow after Match.
     * the method add new Match that the fan wants to follow after.
     * @param fan - the current fan who want to the follow after the EventMaker.
     * @param match - the current Match that the fan want to follow after.
     * @return true - if adding the match succeeded. else - return false.
     * @throws IllegalValueException
     */
    public static boolean followMatch(Fan fan, Match match) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        AMEDYSystem.systemLogger.info("[FanController][followMatch] entered followMatch method.");
        AMEDYSystem.systemLogger.info("[FanController][followMatch] checking null arguments.");
        if(match == null || fan == null)
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] match or fan are null.");
            throw new IllegalValueException("at list one of the entities are null.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followMatch] passed null argument check.");
        AMEDYSystem.systemLogger.info("[FanController][followMatch] checking if fan already follows after this current match (hold the match as observable) and if the match hold the fan as observer.");
        if(fan.isFollowAfterMatch(match) || match.isFanObserverExist(fan)) //PEM = ProfileEventMaker
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] fan already hold event maker as an observable or event maker already hold fan as an observer.");
            throw new AssertAlreadyExistException("fan/match already exist at match/fan.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followMatch] both fan and match hold each others.");
        AMEDYSystem.systemLogger.info("[FanController][followMatch] try adding the match to the fan observables list.");
        if(!fan.addNewMatchToFollow(match))
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] failed adding the match the the fan observable list.");
            throw new PermissionDeniedException("failed adding event maker to the the fan observable list.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followMatch] succeeded adding the match to the fan observables list.");
        AMEDYSystem.systemLogger.info("[FanController][followMatch] try adding the fan to the match's list.");
        if(!match.addFanObserver(fan))
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] failed adding fan to the the match observers list.");
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] delete match from fan observers list.");
            fan.removeFollower(match);
            throw new PermissionDeniedException("failed adding match to the the fan observable list.");
        }
        AMEDYSystem.systemLogger.info("[FanController][followMatch] succeeded following after match.");
        return true;
    }

    /**
     * UseCase 3.4 - fill complaint.
     * the method taking the the complaint filled by the fan and and add to the DB.
     * @param fan - the fan who is filling the complaint.
     * @param subject - String. the subject of the complaint.
     * @param content - String. the content of the complaint.
     */
    public static boolean fillComplaint(Fan fan, String subject, String content) throws IllegalValueException, PermissionDeniedException {
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] entered fillComplaint method.");
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] checking null arguments.");
        if(fan == null || subject == null || content == null)
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] fan or one the complaint values are null.");
            throw new IllegalValueException("fan or one the complaint values are null.");
        }
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] passed null argument check.");
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] checking if the complaint arguments are valid.");
        if(subject.length() < 6 || content.length() < 10)
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] one of the complaint values are invalid.");
            throw new IllegalValueException("one of the complaint values are invalid.");
        }
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] passed complaint values check.");
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] generating a new complaint object.");
        Complaint newComplaint = new Complaint(fan, subject, content);
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] adding the the complaint to the fan complaint collection and to the database.");
        if(!fan.addComplaintToFan(newComplaint))
        {
            AMEDYSystem.systemLogger.warning("[FanController][followMatch] failed adding the complaint to the database.");
            throw new PermissionDeniedException("failed adding the complaint to the database.");
        }
        AMEDYSystem.systemLogger.info("[FanController][fillComplaint] added the complaint to the database.");

        return true;
    }

    public List<Object> seeHistorySearch() {
        return null;
    }
}