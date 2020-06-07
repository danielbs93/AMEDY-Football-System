package BuisinessLayer.LogicalOperations;

import java.util.ArrayList;


public class User extends BaseFunctionality {

    //Fields
    private String userName;
    private String password;

    private ArrayList<Profile> Profiles;

    /***
     * Constructor.
     *
     * @param userName username.
     * @param password password.
     * @param AMEDYSystem System which the user connected.
     */
    public User(String userName, String password, AMEDYSystem AMEDYSystem) {

        super(AMEDYSystem);

        this.userName = userName;
        this.password = password;

        this.Profiles = new ArrayList<>();
    }

    // Getters

    /***
     * Get user unique key.
     *
     * @return String represent user's key.
     */
    public String getKey() {return "" + this.getUserName();}

    public String getUserName()
    {
        return this.userName;
    }

    public ArrayList<Profile> getProfiles() {
        return Profiles;
    }

    /***
     * Get user's profile by profileType
     *
     * @param profileName profile type
     *
     * @return relevant profile.
     */
    public Profile getProfile(String profileName) {

        for(Profile profile: this.Profiles) {

            if(profile.ClassType.toLowerCase().equals(profileName.toLowerCase())) {
                return profile;
            }
        }

        return null;
    }

    public String getPassword() {
        return password;
    }

    public AMEDYSystem getAMEDYSystem() {
        return getSystem();
    }

    //Setters
    /***
     * Add profile to user.
     *
     * @param profile
     */
    public void addProfile(Profile profile) {
        if (profile != null) {
            profile.setUser(this);
            this.Profiles.add(profile);
        }
    }

    /***
     * Remove profile from user.
     *
     * @param profile
     */
    public void removeProfile(Profile profile) {
        if (profile != null) {
            profile.setUser(null);
            this.Profiles.remove(profile);
        }
    }

    @Override
    public boolean equals(Object obj) {

        if(obj != null && obj instanceof  User) {

            User user = (User)obj;

            return this.getKey().equals(user.getKey());
        }

        return false;
    }
}
