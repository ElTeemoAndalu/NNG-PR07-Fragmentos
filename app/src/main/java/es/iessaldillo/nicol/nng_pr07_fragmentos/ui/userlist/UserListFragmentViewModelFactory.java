package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.userlist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;

public class UserListFragmentViewModelFactory implements ViewModelProvider.Factory {
    private final Database database;

    UserListFragmentViewModelFactory(Database database){this.database = database;}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserListFragmentViewModel(database);
    }

}
