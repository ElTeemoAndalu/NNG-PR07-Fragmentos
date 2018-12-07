package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.userlist;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;

public class UserListFragmentViewModel extends ViewModel {
    private final Database database;
    private final LiveData<List<User>> users;
    private User returnedUser;

    public UserListFragmentViewModel(Database database) {
        this.database = database;
        this.users = database.getUsers();
    }

    LiveData<List<User>> getUsers() {
        return users;
    }

    void deleteUser(User user) {
        database.deleteUser(user);
    }


    public User getReturnedUser() {
        return returnedUser;
    }

    public void setReturnedUser(User returnedUser) {
        this.returnedUser = returnedUser;
    }
}
