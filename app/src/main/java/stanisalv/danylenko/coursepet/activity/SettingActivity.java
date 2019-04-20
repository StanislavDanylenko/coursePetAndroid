package stanisalv.danylenko.coursepet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;

public class SettingActivity extends AppCompatActivity {

    private PetApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        application = (PetApplication) getApplication();
    }

    public void signOut(View view) {

        application.setStatistic(null);
        application.setBreeds(null);
        application.setCountries(null);
        application.setAnimals(null);
        application.setUser(null);
        application.setTOKEN(null);

        goToLoginActivity();

    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
