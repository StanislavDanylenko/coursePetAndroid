package stanisalv.danylenko.coursepet.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.Animal;
import stanisalv.danylenko.coursepet.entity.AnimalsBreed;
import stanisalv.danylenko.coursepet.entity.enumeration.Gender;

public class AddAnimalActivity extends AppCompatActivity {

    private PetApplication application;

    private Animal animal;
    private RadioButton male;
    private List<AnimalsBreed> breeds;

    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        application = (PetApplication) getApplication();
        breeds = application.getBreeds();

        male = findViewById(R.id.gender_male);
        male.setChecked(true);

        animal = new Animal();
        animal.setGender(Gender.MALE);

        final Spinner dropdownBreed = (Spinner) findViewById(R.id.animal_breed);
        final ArrayAdapter<AnimalsBreed> adapterCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, breeds);
        dropdownBreed.setAdapter(adapterCountry);
        dropdownBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AnimalsBreed breed = (AnimalsBreed) dropdownBreed.getSelectedItem();
                animal.setAnimalsBreed(breed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dropdownBreed.setSelection(0);

        currentDateTime = (TextView) findViewById(R.id.currentDateTime);
        setInitialDateTime();
    }

    public void onRadioButtonClicked(View view) {
        RadioButton rb = (RadioButton) view;
        switch (rb.getId()) {
            case R.id.gender_male:
                animal.setGender(Gender.MALE);
                break;
            case R.id.gender_female:
                animal.setGender(Gender.FEMALE);
                break;
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(AddAnimalActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void addAnimal(View view) {
    }
}
