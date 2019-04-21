package stanisalv.danylenko.coursepet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.country.Country;
import stanisalv.danylenko.coursepet.entity.country.CountryWithGraft;

public class CountryActivity extends AppCompatActivity {

    private PetApplication application;

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
                    .setTitleText("No info")
                    .setContentText("We can't say you about this country")
                    .show();
        } else {
            Country country = getCountry(countryName);
            if(checkAvailability(country)) {
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("OK")
                        .setContentText("Yes you can!")
                        .show();
            } else {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("No")
                        .setContentText("No you can't!")
                        .show();
            }
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

    private boolean checkAvailability(Country country) {
        // TODO: 22.04.2019 if list of graft is empy get it 
        return false;
    }

    // TODO: 22.04.2019 update list func 
    
}
