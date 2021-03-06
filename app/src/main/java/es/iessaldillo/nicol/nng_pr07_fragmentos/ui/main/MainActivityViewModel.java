package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.Avatar;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;

public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<Boolean> launchProfile = new MutableLiveData<>();
    public User user;

    public MutableLiveData<Boolean> launchAvatar = new MutableLiveData<>();
    public MutableLiveData<Avatar> avatar = new MutableLiveData<>();

    MainActivityViewModel(){
        launchProfile.setValue(false);
        launchAvatar.setValue(false);
    }

    public void goToProfile(User user){
        this.user = user;
        launchProfile.setValue(true);
    }

    public void goToAvatar(Avatar avatar){
        this.avatar.setValue(avatar);
        launchAvatar.setValue(true);
    }

    public LiveData<Boolean> getLaunchProfile() {
        return launchProfile;
    }

    public void setLaunchProfile(boolean launchState) {
        launchProfile.setValue(launchState);
    }


    public LiveData<Boolean> getLaunchAvatar() {
        return launchAvatar;
    }

    public void setLaunchAvatar(boolean launchState) {
        launchAvatar.setValue(launchState);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LiveData<Avatar> getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.setValue(avatar);
    }

}
