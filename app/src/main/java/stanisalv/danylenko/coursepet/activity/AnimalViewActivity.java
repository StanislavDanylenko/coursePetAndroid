package stanisalv.danylenko.coursepet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.Animal;

public class AnimalViewActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animal);

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
                        Toast.makeText(context, "GOOD", Toast.LENGTH_LONG).show();
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

    }
}
