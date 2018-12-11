package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.avatar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main.MainActivityViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.ResourcesUtils;


public class AvatarFragment extends Fragment {
    private AvatarFragmentViewModel vmAV;
    private MainActivityViewModel vmMain;
    private ArrayList<ImageView> imagesList;
    private ArrayList<TextView> namesList;
    private int avatarCount;

    public static Fragment newInstance() {
        return new AvatarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_avatar, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmAV = ViewModelProviders.of(this).get(AvatarFragmentViewModel.class);
        setHasOptionsMenu(true);
        vmMain = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        Objects.requireNonNull(getView());
        vmMain.setLaunchAvatar(false);
        setupToolbar();
        setupViews(getView(),savedInstanceState);
    }

    private void setupViews(View view, Bundle savedInstanceState) {
        ConstraintLayout constLayout = ViewCompat.requireViewById(view, R.id.constraintLayout);
        int LBL_IMGVIEWS_PER_PICTURE = 2;

        if(savedInstanceState == null){
            vmAV.setSelectedAvatar(vmMain.getAvatar().getValue());
        }

        imagesList = new ArrayList<>();
        namesList = new ArrayList<>();

        imagesList.add(ViewCompat.requireViewById(view, R.id.imgAvatar1));
        imagesList.add(ViewCompat.requireViewById(view, R.id.imgAvatar2));
        imagesList.add(ViewCompat.requireViewById(view, R.id.imgAvatar3));
        imagesList.add(ViewCompat.requireViewById(view, R.id.imgAvatar4));
        imagesList.add(ViewCompat.requireViewById(view, R.id.imgAvatar5));
        imagesList.add(ViewCompat.requireViewById(view, R.id.imgAvatar6));

        namesList.add(ViewCompat.requireViewById(view, R.id.lblAvatar1));
        namesList.add(ViewCompat.requireViewById(view, R.id.lblAvatar2));
        namesList.add(ViewCompat.requireViewById(view, R.id.lblAvatar3));
        namesList.add(ViewCompat.requireViewById(view, R.id.lblAvatar4));
        namesList.add(ViewCompat.requireViewById(view, R.id.lblAvatar5));
        namesList.add(ViewCompat.requireViewById(view, R.id.lblAvatar6));


        avatarCount = constLayout.getChildCount() / LBL_IMGVIEWS_PER_PICTURE;

        setAvatars();

        tintSelectedAvatar();

        for (int i = 0; i < avatarCount; i++) {
            imagesList.get(i).setOnClickListener(this::getSelectedAvatar);
            namesList.get(i).setOnClickListener(this::getSelectedAvatar);
        }
    }

    private void tintSelectedAvatar() {

        for (int i = 0; i < avatarCount; i++) {
            if (imagesList.get(i).getAlpha() == ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)) {
                deselectImageView(imagesList.get(i));
            }
        }
        for (int i = 0; i < avatarCount; i++) {
            if (vmAV.getSelectedAvatar().getImageResId() == vmAV.getDatabase().queryAvatar((long) i + 1).getImageResId()) {
                selectImageView(imagesList.get(i));
            }
        }

    }

    private void getSelectedAvatar(View v) {
        long selectedAvatarID;
        for (int i = 0; i < avatarCount; i++) {
            if (v.getId() == namesList.get(i).getId() || v.getId() == imagesList.get(i).getId()) {
                selectedAvatarID = vmAV.getAvatarList().get(i).getId();
                vmAV.setSelectedAvatar(vmAV.getDatabase().queryAvatar(selectedAvatarID));
                tintSelectedAvatar();
            }
        }

    }

    private void setAvatars() {
        String name;
        int imgID;

        for (int i = 0; i < avatarCount; i++) {
            name = vmAV.getAvatarList().get(i).getName();
            imgID = vmAV.getAvatarList().get(i).getImageResId();
            imagesList.get(i).setImageResource(imgID);
            namesList.get(i).setText(name);
        }
    }

    private void selectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
    }

    private void deselectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle(getString(R.string.avatar_title));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.activity_avatar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            vmMain.setAvatar(vmAV.getSelectedAvatar());
            getFragmentManager().popBackStack();
            vmMain.setLaunchAvatar(false);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
