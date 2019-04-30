package stanisalv.danylenko.coursepet.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.CacheModel;
import stanisalv.danylenko.coursepet.entity.CountByBreedInCountryStatistic;
import stanisalv.danylenko.coursepet.entity.country.CountryWithGraft;
import stanisalv.danylenko.coursepet.entity.user.User;
import stanisalv.danylenko.coursepet.entity.user.UserDto;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.StatisticService;
import stanisalv.danylenko.coursepet.network.retrofit.UserService;

public class SettingActivity extends AppCompatActivity {

    private PetApplication application;

    private TextView currentCountry;

    private List<CountryWithGraft> countries;
    private User user;

    private UserDto userDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        application = (PetApplication) getApplication();
        countries = application.getCountries();
        user = application.getUser();

        userDto = new UserDto();

        currentCountry = (TextView) findViewById(R.id.current_country);
        currentCountry.setText("Current country: " + user.getCountry().getName());

        final Spinner dropdownBreed = (Spinner) findViewById(R.id.countries);
        final ArrayAdapter<CountryWithGraft> adapterCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        dropdownBreed.setAdapter(adapterCountry);
        dropdownBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryWithGraft countryWithGraft = (CountryWithGraft) dropdownBreed.getSelectedItem();
                userDto.setCountryId(countryWithGraft.getCountry().getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dropdownBreed.setSelection(0);
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

    public void updateProfile(View view) {

        RetrofitService retrofitService = application.getRetrofitService();
        UserService service = retrofitService.getRetrofit().create(UserService.class);
        service.updateUser(application.getTOKEN(), user.getId(), userDto).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User updatedUser = response.body();
                    if (!user.getCountry().equals(updatedUser.getCountry())) {
                        user = updatedUser;
                        currentCountry.setText(getString(R.string.settings_current_country) + user.getCountry().getName());
                        updateStatistic(updatedUser.getCountry().getId());
                    }
                    handleSuccessUpdating();
                } else {
                    handleFailedUpdating();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                handleFailedUpdating();
            }
        });

    }

    public void updateCacheData(View view) {

        RetrofitService retrofitService = application.getRetrofitService();
        UserService userService = retrofitService.getRetrofit().create(UserService.class);
        userService.getCache(application.getTOKEN(), user.getId()).enqueue(new Callback<CacheModel>() {
            @Override
            public void onResponse(Call<CacheModel> call, Response<CacheModel> response) {
                if (response.isSuccessful()) {
                    CacheModel cacheModel = response.body();

                    application.setUser(cacheModel.getUser());
                    application.setAnimals(cacheModel.getAnimals());
                    application.setCountries(cacheModel.getCountries());
                    application.setBreeds(cacheModel.getBreeds());
                    application.setStatistic(cacheModel.getStatistic());
                    application.setDiseases(cacheModel.getDiseases());
                    application.setGrafts(cacheModel.getGrafts());

                    currentCountry.setText(getString(R.string.settings_current_country) + user.getCountry().getName());

                    handleSuccessUpdating();
                } else {
                    handleFailedUpdating();
                }
            }

            @Override
            public void onFailure(Call<CacheModel> call, Throwable throwable) {
                handleFailedUpdating();
            }
        });

    }

    public void updateStatistic(Long countryId) {

        RetrofitService retrofitService = application.getRetrofitService();
        StatisticService userService = retrofitService.getRetrofit().create(StatisticService.class);
        userService.getStatisticInCountry(application.getTOKEN(),countryId).enqueue(new Callback<List<CountByBreedInCountryStatistic>>() {
            @Override
            public void onResponse(Call<List<CountByBreedInCountryStatistic>> call, Response<List<CountByBreedInCountryStatistic>> response) {
                if(response.isSuccessful()) {
                    List<CountByBreedInCountryStatistic> list = response.body();
                    application.setStatistic(list);
                }
            }

            @Override
            public void onFailure(Call<List<CountByBreedInCountryStatistic>> call, Throwable throwable) {

            }
        });

    }

    private void handleFailedUpdating() {
        Snackbar.make(getWindow().getDecorView().getRootView(), getString(R.string.error_update_aprofile), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void handleSuccessUpdating() {
        Snackbar.make(getWindow().getDecorView().getRootView(), getString(R.string.alert_suc_updaated), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
