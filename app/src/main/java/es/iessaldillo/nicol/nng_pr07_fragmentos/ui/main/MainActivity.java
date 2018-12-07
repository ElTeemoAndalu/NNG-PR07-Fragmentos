package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.profile.ProfileFragment;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.userlist.UserListFragment;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.FragmentUtils;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";
    private MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    UserListFragment.newInstance(), UserListFragment.class.getSimpleName());
        }
        vm = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        observeProfileCall();
        observeAvatarCall();
    }

    private void observeProfileCall() {
        vm.getLaunchProfile().observe(this, profileSwitch -> {
            goToProfile();
        });
    }

    private void observeAvatarCall() {
        vm.getLaunchProfile().observe(this, profileSwitch -> {
            goToAvatar();
        });
    }

    private void goToProfile() {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName(),PROFILE_FRAGMENT_TAG,FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    private void goToAvatar() {
//        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
//                AvatarFragment.newInstance(), AvatarFragment.class.getSimpleName(),PROFILE_FRAGMENT_TAG,FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

//    private void goToAvatar(User user) {
//        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
//                AvatarFragment.newInstance(), AvatarFragment.class.getSimpleName(),PROFILE_FRAGMENT_TAG,FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
