package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.profile;

import androidx.lifecycle.ViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.Avatar;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;

public class ProfileFragmentViewModel extends ViewModel {

    private Database database;
    private boolean firstLaunch = false;

    private boolean addUser = true;
    private User profileUser;

    public User getProfileUser() {
        return profileUser;
    }

    public void setProfileUser(User profileUser) {
        this.profileUser = profileUser;
    }

    public Avatar getProfileAvatar() {
        return profileUser.getAvatar();
    }

    public void setProfileAvatar(Avatar avatar) {
        profileUser.setAvatar(avatar);
    }

    private Database getDatabase() {
        if (database == null) {
            database = Database.getInstance();
        }
        return database;
    }

    //Sets the avatar to the default one (One time use)
    public void setDefaultAvatar() {
        if (!firstLaunch) {
            setProfileAvatar(getDatabase().getDefaultAvatar());
            firstLaunch = true;
        }


    }

    public boolean isAddUser() {
        return addUser;
    }

    public void setAddUser(boolean addUser) {
        this.addUser = addUser;
    }

    void editUser(User user) {
        database.editUser(user);
    }

    void addUser(User user) {
        database.addUser(user);
    }

}
