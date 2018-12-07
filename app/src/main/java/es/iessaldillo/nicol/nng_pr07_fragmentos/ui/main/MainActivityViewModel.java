package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.Avatar;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;

public class MainActivityViewModel extends ViewModel {

    public Database database = Database.getInstance();

    public MutableLiveData<Boolean> launchProfile = new MutableLiveData<>();
    public User user;

    public MutableLiveData<Boolean> launchAvatar = new MutableLiveData<>();

    public Avatar avatarSentToProfile;

    public void goToProfile(User user){
        this.user = user;
        launchProfile.setValue(true);
    }

    public void goToAvatar(Avatar avatar){
        avatarSentToProfile = avatar;
        launchAvatar.setValue(true);
    }

    public LiveData<Boolean> getLaunchProfile() {
        return launchProfile;
    }

    public void setLaunchProfile(boolean state) {
        launchProfile.setValue(state);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatarSentToProfile() {
        return avatarSentToProfile;
    }

    public void setAvatarSentToProfile(Avatar avatarSentToProfile) {
        this.avatarSentToProfile = avatarSentToProfile;
    }

}
