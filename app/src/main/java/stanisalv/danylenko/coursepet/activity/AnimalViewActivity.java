package stanisalv.danylenko.coursepet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.Animal;
import stanisalv.danylenko.coursepet.entity.AnimalUpdateDto;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.AnimalService;

public class AnimalViewActivity extends AppCompatActivity {

    private PetApplication application;

    private ImageView img;

    private TextView name;
    private TextView gender;
    private TextView smartCardId;
    private TextView breed;
    private TextView birthDate;
    private TextView height;
    private TextView weight;
    private TextView length;

    private EditText editLength;
    private EditText editWeight;
    private EditText editHeigth;

    private Context context;
    private Animal animal;
    private List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animal);

        application = (PetApplication) getApplication();
        animals = application.getAnimals();

        img = (ImageView) findViewById(R.id.animal_image);

        name = (TextView) findViewById(R.id.animal_name);
        gender = (TextView) findViewById(R.id.animal_gender);
        smartCardId = (TextView) findViewById(R.id.animal_smart_c_id);
        breed = (TextView) findViewById(R.id.animal_breed);
        birthDate = (TextView) findViewById(R.id.animal_birth_date);
        height = (TextView) findViewById(R.id.animal_height);
        length = (TextView) findViewById(R.id.animal_length);
        weight = (TextView) findViewById(R.id.animal_weight);

        // Recieve data
        Intent intent = getIntent();
        animal = (Animal)intent.getSerializableExtra("Animal");


        // Setting values
        img.setImageResource(R.drawable.logo);

        name.setText(animal.getName());
        gender.setText(animal.getGender().name());
        smartCardId.setText("Smart card ID: " + animal.getSmartCardId());
        breed.setText("Breed: " + animal.getAnimalsBreed().getName());
        height.setText("Height: " + animal.getHeight().toString());
        length.setText("Length: " + animal.getLength().toString());
        weight.setText("Weight: " + animal.getWeight().toString());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        birthDate.setText(dateFormatter.format(animal.getBirthDate()));

        context = this;
    }

    public void getAlert(View view) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.edit_animal_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Edit " + animal.getName());

        editLength = (EditText) promptView.findViewById(R.id.edit_length);
        editWeight = (EditText) promptView.findViewById(R.id.edit_weight);
        editHeigth = (EditText) promptView.findViewById(R.id.edit_height);

        editHeigth.setText(animal.getHeight().toString());
        editWeight.setText(animal.getWeight().toString());
        editLength.setText(animal.getLength().toString());

        //final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(validateValuesFromUpdateInputs(editHeigth, editLength, editWeight)) {
                            try {
                                AnimalUpdateDto dto = getValuesFromUpdateInputs();
                                updateAnimal(animal.getId(), dto);
                            } catch (Exception e) {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Invalid data", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void deleteAnimal(View view) {

        RetrofitService retrofitService = application.getRetrofitService();
        AnimalService service = retrofitService.getRetrofit().create(AnimalService.class);

        service.deleteAnimal(application.getTOKEN(), animal.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                deleteAnimalFromList(animal.getId());
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Cannot delete animal, try later", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void deleteAnimalFromList(Long animalId) {
        for(Animal animal : animals) {
            if(animal.getId().equals(animalId)) {
                animals.remove(animal);
                return;
            }
        }
    }

    private void updateAnimal(Long animalId, AnimalUpdateDto dto) {
        RetrofitService retrofitService = application.getRetrofitService();
        AnimalService service = retrofitService.getRetrofit().create(AnimalService.class);

        service.updateAnimal(application.getTOKEN(), animalId, dto).enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    Animal updatedAnimal = response.body();
                    animal = updatedAnimal;
                    handleSuccessUpdating();
                } else {
                    handleFailedUpdating();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                t.printStackTrace();
                handleFailedUpdating();
            }
        });
    }

    private AnimalUpdateDto getValuesFromUpdateInputs() {

        AnimalUpdateDto dto = new AnimalUpdateDto();

        dto.setHeight(Double.parseDouble(editHeigth.getText().toString()));
        dto.setWeight(Double.parseDouble(editWeight.getText().toString()));
        dto.setLength(Double.parseDouble(editLength.getText().toString()));

        return dto;
    }

    private boolean validateValuesFromUpdateInputs(EditText... views) {
        for(EditText textView : views) {
            if("".equals(textView.getText())) {
                textView.setError("This field is required");
                return false;
            }
        }

        return true;
    }

    private void handleFailedUpdating() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Cannot update animal, try later", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void handleSuccessUpdating() {
        height.setText("Height: " + animal.getHeight().toString());
        length.setText("Length: " + animal.getLength().toString());
        weight.setText("Weight: " + animal.getWeight().toString());
    }
}
