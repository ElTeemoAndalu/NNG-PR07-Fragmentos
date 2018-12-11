package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.avatar.AvatarFragment;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.profile.ProfileFragment;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.userlist.UserListFragment;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.FragmentUtils;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";
    private MainActivityViewModel vm;
    private String AVATAR_FRAGMENT_TAG = "AVATAR_FRAGMENT_TAG";

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
            if(profileSwitch){
                goToProfile();
            }
        });
    }

    private void observeAvatarCall() {
        vm.getLaunchAvatar().observe(this, avatarSwitch -> {
            if(avatarSwitch){
                goToAvatar();
            }
        });
    }

    private void goToProfile() {
        if(getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName()) == null){
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName(),PROFILE_FRAGMENT_TAG,FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        }

    }

    private void goToAvatar() {
        if(getSupportFragmentManager().findFragmentByTag(AvatarFragment.class.getSimpleName()) == null){
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    AvatarFragment.newInstance(), AvatarFragment.class.getSimpleName(),AVATAR_FRAGMENT_TAG,FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
