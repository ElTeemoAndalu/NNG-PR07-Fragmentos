package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.profile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main.MainActivityViewModel;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.FieldEnabler;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.KeyboardUtils;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.SnackBarUtils;
import es.iessaldillo.nicol.nng_pr07_fragmentos.utils.ValidationUtils;

public class ProfileFragment extends Fragment {
    private ImageView imgAvatar;
    private TextView lblAvatar;
    private final int EDITTEXT_QUANTITY = 5;
    private final int NAME = 0;
    private final int EMAIL = 1;
    private final int PHONE = 2;
    private final int ADDRESS = 3;
    private final int WEB = 4;
    private final EditText[] txtFields = new EditText[EDITTEXT_QUANTITY];
    private final TextView[] lblFields = new TextView[EDITTEXT_QUANTITY];
    private String errorMsg;
    public static final String EXTRA_PROFILE = "EXTRA_PROFILE";
    private ImageView imgEmail,imgPhone,imgAddress,imgWeb;
    private ProfileFragmentViewModel vmPR;
    private MainActivityViewModel vmMain;

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmPR = ViewModelProviders.of(this).get(ProfileFragmentViewModel.class);
        setHasOptionsMenu(true);
        vmMain = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        Objects.requireNonNull(getView());
        setupToolbar();
        setupViews(getView(),savedInstanceState);
    }

    private void setupViews(View view,Bundle savedInstanceState) {
        //Initializations
        errorMsg = getString(R.string.main_invalid_data);
        imgAvatar = ViewCompat.requireViewById(view, R.id.imgAvatar);
        lblAvatar = ViewCompat.requireViewById(view, R.id.lblAvatar);

        txtFields[NAME] = ViewCompat.requireViewById(view, R.id.txtName);
        txtFields[EMAIL] = ViewCompat.requireViewById(view, R.id.txtEmail);
        txtFields[PHONE] = ViewCompat.requireViewById(view, R.id.txtPhonenumber);
        txtFields[ADDRESS] = ViewCompat.requireViewById(view, R.id.txtAddress);
        txtFields[WEB] = ViewCompat.requireViewById(view, R.id.txtWeb);

        lblFields[NAME] = ViewCompat.requireViewById(view, R.id.lblName);
        lblFields[EMAIL] = ViewCompat.requireViewById(view, R.id.lblEmail);
        lblFields[PHONE] = ViewCompat.requireViewById(view, R.id.lblPhonenumber);
        lblFields[ADDRESS] = ViewCompat.requireViewById(view, R.id.lblAddress);
        lblFields[WEB] = ViewCompat.requireViewById(view, R.id.lblWeb);

        imgEmail = ViewCompat.requireViewById(view, R.id.imgEmail);
        imgPhone = ViewCompat.requireViewById(view, R.id.imgPhonenumber);
        imgAddress = ViewCompat.requireViewById(view, R.id.imgAddress);
        imgWeb = ViewCompat.requireViewById(view, R.id.imgWeb);

        vmPR.setProfileUser(vmMain.user);

        if(savedInstanceState == null){

        }
        if (vmPR.getProfileUser() == null) {
            vmPR.setProfileUser(new User());
            vmPR.setAddUser(true);
            vmPR.setDefaultAvatar();
        } else {
            vmPR.setAddUser(false);
            showUser();
        }

        configAvatarProfile();

        //Listeners
        imgAvatar.setOnClickListener(v -> vmMain.goToAvatar(vmPR.getProfileAvatar()));

        txtFields[WEB].setOnEditorActionListener((v, actionId, event) -> {
            save();
            return false;
        });


        for (EditText txt : txtFields) {
            txt.setOnFocusChangeListener(this::changeLblStyle);
        }

        imgEmail.setOnClickListener(v -> sendEmail());
        imgPhone.setOnClickListener(v -> callPhone());
        imgAddress.setOnClickListener(v -> showAddress());
        imgWeb.setOnClickListener(v -> searchURL());

        //These listeners are split due to them not working on a separate class
        txtFields[NAME].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FieldEnabler.enableOrDisableFieldState(TextUtils.isEmpty(s), txtFields[NAME], null, lblFields[NAME], errorMsg);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtFields[EMAIL].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FieldEnabler.enableOrDisableFieldState(ValidationUtils.isValidEmail(s.toString()), txtFields[EMAIL], imgEmail, lblFields[EMAIL], errorMsg);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtFields[PHONE].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FieldEnabler.enableOrDisableFieldState(ValidationUtils.isValidPhone(s.toString()), txtFields[PHONE], imgPhone, lblFields[PHONE], errorMsg);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtFields[ADDRESS].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FieldEnabler.enableOrDisableFieldState(TextUtils.isEmpty(s), txtFields[ADDRESS], imgAddress, lblFields[ADDRESS], errorMsg);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtFields[WEB].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FieldEnabler.enableOrDisableFieldState(ValidationUtils.isValidUrl(s.toString()), txtFields[WEB], imgWeb, lblFields[WEB], errorMsg);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configAvatarProfile() {
        imgAvatar.setTag(vmPR.getProfileAvatar().getImageResId());
        imgAvatar.setImageResource(vmPR.getProfileAvatar().getImageResId());
        lblAvatar.setText(vmPR.getProfileAvatar().getName());
    }

    private void searchURL() {
        String url = txtFields[WEB].getText().toString();
        if (!url.startsWith("http://")) {
            url = String.format("http://%s", url);
        }
        Intent lookForAddress = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startImplicitIntent(lookForAddress);
    }

    private void showAddress() {
        String address = String.format("geo:0,0?q=%s", txtFields[ADDRESS].getText().toString());
        Intent showMap = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
        startImplicitIntent(showMap);
    }

    private void callPhone() {
        String phoneWritten = txtFields[PHONE].getText().toString();
        Intent callThisPhone = new Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", phoneWritten)));
        startImplicitIntent(callThisPhone);
    }

    private void sendEmail() {
        String emailWritten = txtFields[EMAIL].getText().toString();
        Intent sendToEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("mailto:%s", emailWritten)));
        startImplicitIntent(sendToEmail);
    }

    private void startImplicitIntent(Intent implicitIntent) {
        try {
            startActivity(implicitIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    //This method changes the font style of the TextViews associated to the EditTexts, from default to bold and vice versa.
    //It depends on its current state.
    private void changeLblStyle(View v, boolean hasFocus) {
        if (hasFocus) {
            for (int i = 0; i < txtFields.length; i++) {
                if (txtFields[i].getId() == v.getId()) {
                    lblFields[i].setTypeface(Typeface.DEFAULT_BOLD);
                }
            }
        } else {
            for (int i = 0; i < txtFields.length; i++) {
                if (txtFields[i].getId() == v.getId()) {
                    lblFields[i].setTypeface(Typeface.DEFAULT);
                }
            }
        }
    }

    //Save checks all EditText in the activity, if these fufill the patterns required, the user is shown a snackbar
    //showing a success message, if not, the views that fail the requirements are show an error and a snakbar is also shown
    //with a message of failure
    private void save() {
        KeyboardUtils.hideSoftKeyboard(getActivity());
        if (validateAll()) {
            saveUser();
            if(vmPR.isAddUser()){
                vmPR.addUser(vmPR.getProfileUser());
            }else{
                vmPR.editUser(vmPR.getProfileUser());
            }
            getFragmentManager().popBackStack();
        } else {
            SnackBarUtils.showSnackBar(lblFields[NAME], getString(R.string.main_error_saving));
        }


    }

    private void saveUser() {
        vmPR.getProfileUser().setName(txtFields[NAME].getText().toString());
        vmPR.getProfileUser().setEmail(txtFields[EMAIL].getText().toString());
        vmPR.getProfileUser().setPhone(txtFields[PHONE].getText().toString());
        vmPR.getProfileUser().setAddress(txtFields[ADDRESS].getText().toString());
        vmPR.getProfileUser().setWeb(txtFields[WEB].getText().toString());
    }

    //It checks if all the edittexts pass the requirements and shows errors if they do not
    private boolean validateAll() {
        boolean checkName = TextUtils.isEmpty(txtFields[NAME].getText().toString());
        boolean checkEmail = ValidationUtils.isValidEmail(txtFields[EMAIL].getText().toString());
        boolean checkPhone = ValidationUtils.isValidPhone(txtFields[PHONE].getText().toString());
        boolean checkAddress = TextUtils.isEmpty(txtFields[ADDRESS].getText().toString());
        boolean checkeb = ValidationUtils.isValidUrl(txtFields[WEB].getText().toString());

        FieldEnabler.enableOrDisableFieldState(checkName, txtFields[NAME], null, lblFields[NAME], errorMsg);
        FieldEnabler.enableOrDisableFieldState(checkEmail, txtFields[EMAIL], imgEmail, lblFields[EMAIL], errorMsg);
        FieldEnabler.enableOrDisableFieldState(checkPhone, txtFields[PHONE], imgPhone, lblFields[PHONE], errorMsg);
        FieldEnabler.enableOrDisableFieldState(checkAddress, txtFields[ADDRESS], imgAddress, lblFields[ADDRESS], errorMsg);
        FieldEnabler.enableOrDisableFieldState(checkeb, txtFields[WEB], imgWeb, lblFields[WEB], errorMsg);

        for (int i = 0; i < lblFields[0].length(); i++) {
            if (!lblFields[i].isEnabled()) {
                return false;
            }
        }
        return true;
    }


    private void showUser() {
        txtFields[NAME].setText(vmPR.getProfileUser().getName());
        txtFields[EMAIL].setText(vmPR.getProfileUser().getEmail());
        txtFields[PHONE].setText(vmPR.getProfileUser().getPhone());
        txtFields[ADDRESS].setText(vmPR.getProfileUser().getAddress());
        txtFields[WEB].setText(vmPR.getProfileUser().getWeb());

        configAvatarProfile();
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle(getString(R.string.profile_title));
        }
    }
}
