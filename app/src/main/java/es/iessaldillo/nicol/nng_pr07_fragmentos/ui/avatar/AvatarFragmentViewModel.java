package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.avatar;

import java.util.List;

import androidx.lifecycle.ViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.Avatar;

public class AvatarFragmentViewModel extends ViewModel {
    private Avatar selectedAvatar;
    private Database database;
    private List<Avatar> avatarList;

    public List<Avatar> getAvatarList() {
        if (avatarList == null) {
            avatarList = getDatabase().queryAvatars();
        }
        return avatarList;
    }

    public Avatar getSelectedAvatar() {
        return selectedAvatar;
    }

    public void setSelectedAvatar(Avatar selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
    }

    public Database getDatabase() {
        if (database == null) {
            database = Database.getInstance();
        }
        return database;
    }
}
