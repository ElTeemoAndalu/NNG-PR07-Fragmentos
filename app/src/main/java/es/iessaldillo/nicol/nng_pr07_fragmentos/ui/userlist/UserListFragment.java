package es.iessaldillo.nicol.nng_pr07_fragmentos.ui.userlist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.data.Database;
import es.iessaldillo.nicol.nng_pr07_fragmentos.model.User;
import es.iessaldillo.nicol.nng_pr07_fragmentos.databinding.FragmentUserListBinding;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.main.MainActivityViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class UserListFragment extends Fragment {

    private UserListFragmentViewModel vmUL;
    private MainActivityViewModel vmMain;
    private UserListFragmentAdapter ulistAdapter;
    private FragmentUserListBinding db;
    private RecyclerView lstUser;
    private TextView lblEmptyView;
    private FloatingActionButton floatingActionButton2;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmUL = ViewModelProviders.of(requireActivity(),
                new UserListFragmentViewModelFactory(Database.getInstance())).get(
                UserListFragmentViewModel.class);

        vmMain = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        Objects.requireNonNull(getView());
        setupViews(getView());
        observeUsers();
    }

    private void setupViews(View view) {
        setupToolbar();
        lblEmptyView = ViewCompat.requireViewById(view,R.id.lblEmptyView);
        floatingActionButton2 = ViewCompat.requireViewById(view,R.id.floatingActionButton2);
        lblEmptyView.setOnClickListener(v -> vmMain.goToProfile(null));
        floatingActionButton2.setOnClickListener(v -> vmMain.goToProfile(null));
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        lstUser = ViewCompat.requireViewById(view,R.id.lstUser);
        ulistAdapter = new UserListFragmentAdapter(
                position -> vmMain.goToProfile(ulistAdapter.getItem(position)),
                position2 -> vmUL.deleteUser(ulistAdapter.getItem(position2))
        );
        lstUser.setHasFixedSize(true);
        lstUser.setLayoutManager(new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.main_lstUser_colums)));
        lstUser.setItemAnimator(new DefaultItemAnimator());
        lstUser.setAdapter(ulistAdapter);
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.list_title));
        }
    }

    private void observeUsers() {

        vmUL.getUsers().observe(this, users -> {
            ulistAdapter.submitList(users);
            lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }




}
