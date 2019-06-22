package stanisalv.danylenko.coursepet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.Graft;
import stanisalv.danylenko.coursepet.entity.IsAvailableCountry;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraft;
import stanisalv.danylenko.coursepet.entity.country.Country;
import stanisalv.danylenko.coursepet.entity.country.CountryWithGraft;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.AnimalService;
import stanisalv.danylenko.coursepet.network.retrofit.GraftService;

public class CountryActivity extends AppCompatActivity {

    private PetApplication application;

    private Animal animal;

    private List<String> countriesNames = new ArrayList<>();
    private List<CountryWithGraft> countries;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        application = (PetApplication) getApplication();
        countries = application.getCountries();
        getNameList();

        animal = application.getAnimal();
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_country);

        List<String> catList = countriesNames;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, catList);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void getNameList() {
        for(CountryWithGraft country : countries) {
            countriesNames.add(country.getCountry().getName());
        }
    }

    public void checkCountry(View view) {

        String countryName = autoCompleteTextView.getText().toString();

        int positionInTheList = countriesNames.indexOf(countryName);
        if(positionInTheList == -1) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.country_no_info))
                    .setContentText(getString(R.string.country_no_info))
                    .show();
        } else {
            Country country = getCountry(countryName);
            checkAvailability(country);
        }

    }

    private Country getCountry(String name) {
        for(CountryWithGraft country : countries) {
            if (country.getCountry().getName().equals(name)) {
                return country.getCountry();
            }
        }
        return null;
    }

    private void checkAvailability(Country country) {

        RetrofitService retrofitService = application.getRetrofitService();
        AnimalService service = retrofitService.getRetrofit().create(AnimalService.class);

        service.getAnimalIsAvailableCountryInfo(application.getTOKEN(), country.getId(), animal.getId()).enqueue(new Callback<IsAvailableCountry>() {
            @Override
            public void onResponse(Call<IsAvailableCountry> call, Response<IsAvailableCountry> response) {
                if(response.isSuccessful()) {
                    IsAvailableCountry availableCountry = response.body();
                    if(!availableCountry.getIsAvailable()) {
                        handleFailedAdding(availableCountry);
                    } else {
                        handleSuccessAdding();
                    }
                } else {
                    handleFailedRequest();
                }
            }

            @Override
            public void onFailure(Call<IsAvailableCountry> call, Throwable throwable) {
                handleFailedRequest();
            }
        });

    }

    private void handleSuccessAdding() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("OK")
                .setContentText(getString(R.string.country_ok))
                .show();
    }

    private void handleFailedAdding(IsAvailableCountry availableCountry) {

        StringBuilder stringBuilder = new StringBuilder();
        Set<Graft> grafts = availableCountry.getGrafts();

        for(Graft graft : grafts) {
            stringBuilder.append(graft.getName()).append('\n');
        }

        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.country_no))
                .setContentText(getString(R.string.country_no_grafts) + stringBuilder.toString())
                .show();
    }

    private void handleFailedRequest() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.country_no))
                .setContentText(getString(R.string.bad_request))
                .show();
    }

    // TODO: 22.04.2019 update list func 
    
}
