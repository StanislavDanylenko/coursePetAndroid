package stanisalv.danylenko.coursepet.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalCreateDto;
import stanisalv.danylenko.coursepet.entity.animal.AnimalsBreed;
import stanisalv.danylenko.coursepet.entity.user.User;
import stanisalv.danylenko.coursepet.entity.enumeration.Gender;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.AnimalService;

public class AddAnimalActivity extends AppCompatActivity {

    private PetApplication application;

    private AnimalCreateDto animal;
    private RadioButton male;
    private List<AnimalsBreed> breeds;
    private List<Animal> animals;
    private User user;

    private Calendar dateAndTime = Calendar.getInstance();

    private TextView currentDateTime;

    private EditText animalName;
    private EditText animalWeight;
    private EditText animalHeight;
    private EditText animalLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        application = (PetApplication) getApplication();
        breeds = application.getBreeds();
        animals = application.getAnimals();
        user = application.getUser();

        male = findViewById(R.id.gender_male);
        male.setChecked(true);

        animal = new AnimalCreateDto();
        animal.setGender(Gender.MALE);

        final Spinner dropdownBreed = (Spinner) findViewById(R.id.animal_breed);
        final ArrayAdapter<AnimalsBreed> adapterCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, breeds);
        dropdownBreed.setAdapter(adapterCountry);
        dropdownBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AnimalsBreed breed = (AnimalsBreed) dropdownBreed.getSelectedItem();
                animal.setAnimalsBreedId(breed.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dropdownBreed.setSelection(0);

        currentDateTime = (TextView) findViewById(R.id.currentDateTime);
        animalName = (EditText) findViewById(R.id.name);
        animalWeight = (EditText) findViewById(R.id.weight);
        animalHeight = (EditText) findViewById(R.id.height);
        animalLength = (EditText) findViewById(R.id.length);

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
        RetrofitService retrofitService = application.getRetrofitService();
        AnimalService service = retrofitService.getRetrofit().create(AnimalService.class);

        if(!validateInputs(animalName, animalHeight, animalLength, animalWeight) || !getDataFromInputs()) {
            return;
        }

        service.addAnimal(application.getTOKEN(), animal).enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    Animal animal = response.body();
                    handleSuccessAdding(animal);
                } else {
                    handleFailedAdding();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                t.printStackTrace();
                handleFailedAdding();
            }
        });
    }

    private void handleSuccessAdding(Animal animal) {
        animals.add(animal);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void handleFailedAdding() {
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.error_add_animal, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private boolean validateInputs(EditText... views) {

        for(TextView textView : views) {
            if("".equals(textView.getText())) {
                textView.setError(getResources().getString(R.string.error_field_required));
                return false;
            }
        }

        return true;
    }

    private boolean getDataFromInputs() {

        animal.setUserId(user.getId());

        animal.setBirthDate(new Date(dateAndTime.getTimeInMillis()));

        animal.setName(animalName.getText().toString());

        try {
            animal.setHeight(Double.parseDouble(animalHeight.getText().toString()));
            animal.setLength(Double.parseDouble(animalLength.getText().toString()));
            animal.setWeight(Double.parseDouble(animalWeight.getText().toString()));
        } catch (NumberFormatException e) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "Invalid number params", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }
}
