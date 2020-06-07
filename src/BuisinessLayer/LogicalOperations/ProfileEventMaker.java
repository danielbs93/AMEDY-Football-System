package BuisinessLayer.LogicalOperations;

import java.util.ArrayList;
import java.util.List;

public abstract class ProfileEventMaker extends Profile
{
    //Fields
    protected List<Pair> allPersonalInfo;

    //Observable Fields
    private boolean changed = false;
    private List<Fan> allEventMakerObserverList;


    /**
     * constructor. initial the fans Observers List.
     * @param name - String. the EventMaker class type.
     * @param classtype -
     */
    public ProfileEventMaker(String name, String classtype)
    {
//        super(userName, password, AMEDYSystem);
        super(name,classtype);
        this.allEventMakerObserverList = new ArrayList<>();
    }

    private void editMyPersonalInfo() {
        //TODO:
    }

    //Observable Code
    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param   fanObserver   an observer to be added.
     * @throws NullPointerException   if the parameter o is null.
     */
    public synchronized boolean addFanObserver(Fan fanObserver)
    {
        if (!this.allEventMakerObserverList.contains(fanObserver)) {
            if(this.allEventMakerObserverList.add(fanObserver))
            {
                return true;
            }
        }
        return false;
    }

    /**
     *the method checks if the fan exist as an Observer in the Observers List.
     * @param fanObserver - the current fan to check if exist in the Observers List.
     * @return true - if the fan exist in the list. else - return false.
     */
    public synchronized  boolean isFanObserverExist(Fan fanObserver)
    {
        if(this.allEventMakerObserverList.contains(fanObserver))
        {
            return true;
        }
        return false;
    }
    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     * @param   fanObserver   the observer to be deleted.
     */
    public synchronized void deleteFanObserver(Fan fanObserver) {
        this.allEventMakerObserverList.remove(fanObserver);
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see     java.util.Observable*#clearChanged()
     * @see     java.util.Observable#hasChanged()
     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void notifyFansObservers() {
        notifyFansObservers(null);
    }

    /**
     *
     * @param arg
     */
    public void notifyFansObservers(Object arg) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            if (!changed)
                return;
            arrLocal = this.allEventMakerObserverList.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Fan)arrLocal[i]).update(this, arg);
    }

    /**
     * Marks this <tt>Observable</tt> object as having been changed; the
     * <tt>hasChanged</tt> method will now return <tt>true</tt>.
     */
    protected synchronized void setChanged() {
        changed = true;
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its observers of its most recent change,
     * so that the <tt>hasChanged</tt> method will now return <tt>false</tt>.
     * This method is called automatically by the
     * <code>notifyObservers</code> methods.
     *
     * @see     java.util.Observable#notifyObservers()
     * @see     java.util.Observable#notifyObservers(java.lang.Object)
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /**
     * Tests if this object has changed.
     *
     * @return  <code>true</code> if and only if the <code>setChanged</code>
     *          method has been called more recently than the
     *          <code>clearChanged</code> method on this object;
     *          <code>false</code> otherwise.
     * @see     java.util.Observable#*clearChanged()
     * @see     java.util.Observable#*setChanged()
     */
    public synchronized boolean hasChanged() {
        return changed;
    }


    /**
     * the method returns a list of all the needed personal info to show to the
     * user by pairs: <Type, data>
     * @return List of pair<String, String></String,>
     */
    public abstract List<Pair> getPersonalInfo();

}
