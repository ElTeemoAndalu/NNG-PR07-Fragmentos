package es.iessaldillo.nicol.nng_pr07_fragmentos.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.Avatar;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;

// DO NOT TOUCH

public class Database {

    private static Database instance;
    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private final ArrayList<Avatar> avatars = new ArrayList<>();
    private final Random random = new Random(1);
    private long count;

    private final ArrayList<User> users = new ArrayList<>(Arrays.asList(
            new User(1, new Avatar(R.drawable.cat1, "Tom"), "Baldo", "baldo@mero.com", "666666666", "Calle Falsa 123", "http://www.baldomeromola.biz"),
            new User(2, new Avatar(R.drawable.cat2, "Luna"), "German", "german@mero.com", "776666666", "Calle Falsa 234", "http://www.baldomenoromola.biz"),
            new User(3, new Avatar(R.drawable.cat3, "Simba"), "Dolores Fuertes De Barriga", "dolores@barriga.de", "776666666", "Calle Verdadera 24", "http://www.webajenaabaldomero.biz")
    ));

    private Database() {
        insertAvatar(new Avatar(R.drawable.cat1, "Tom"));
        insertAvatar(new Avatar(R.drawable.cat2, "Luna"));
        insertAvatar(new Avatar(R.drawable.cat3, "Simba"));
        insertAvatar(new Avatar(R.drawable.cat4, "Kitty"));
        insertAvatar(new Avatar(R.drawable.cat5, "Felix"));
        insertAvatar(new Avatar(R.drawable.cat6, "Nina"));
        updateUsersLiveData();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    //AVATAR METHODS
    @VisibleForTesting()
    public void insertAvatar(Avatar avatar) {
        long id = ++count;
        avatar.setId(id);
        avatars.add(avatar);
    }

    public Avatar getRandomAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(random.nextInt(avatars.size()));
    }

    public Avatar getDefaultAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(0);
    }

    public List<Avatar> queryAvatars() {
        return new ArrayList<>(avatars);
    }

    public Avatar queryAvatar(long id) {
        for (Avatar avatar : avatars) {
            if (avatar.getId() == id) {
                return avatar;
            }
        }
        return null;
    }

    @VisibleForTesting
    public void setAvatars(List<Avatar> list) {
        count = 0;
        avatars.clear();
        avatars.addAll(list);
    }


    //USER METHODS
    private void updateUsersLiveData() {
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void deleteUser(User user) {
        users.remove(user);
        updateUsersLiveData();
    }

    public void addUser(User user) {
        users.add(user);
        updateUsersLiveData();
    }

    public void editUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
        updateUsersLiveData();
    }

}
