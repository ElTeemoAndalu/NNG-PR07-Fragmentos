package es.iessaldillo.nicol.nng_pr07_fragmentos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.iessaldillo.nicol.nng_pr07_fragmentos.R;
import es.iessaldillo.nicol.nng_pr07_fragmentos.ui.userlist.UserList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, UserList.newInstance())
                    .commitNow();
        }
    }
}
