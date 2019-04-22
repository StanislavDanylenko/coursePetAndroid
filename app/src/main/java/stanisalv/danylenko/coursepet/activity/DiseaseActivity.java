package stanisalv.danylenko.coursepet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.adapter.DiseaseRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;

public class DiseaseActivity extends AppCompatActivity {

    private PetApplication application;

    private Context context;

    private List<AnimalDisease> animalDiseases;

    private DiseaseRecyclerViewAdapter rwAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        context = this;

        application = (PetApplication) getApplication();
        animalDiseases = application.getAnimalDiseases();


        // RW
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.disease_recyclerview_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        rwAdapter = new DiseaseRecyclerViewAdapter(this, animalDiseases, application);
        recyclerView.setAdapter(rwAdapter);


        // FAB
        FloatingActionButton fab = findViewById(R.id.add_disease_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getAddDialog();
            }
        });

    }

    public void getAddDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.add_disease_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Add disease");

        /*editLength = (EditText) promptView.findViewById(R.id.edit_length);
        editWeight = (EditText) promptView.findViewById(R.id.edit_weight);
        editHeight = (EditText) promptView.findViewById(R.id.edit_height);

        editHeight.setText(animal.getHeight().toString());
        editWeight.setText(animal.getWeight().toString());
        editLength.setText(animal.getLength().toString());*/

        //final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*if(validateValuesFromUpdateInputs(editHeight, editLength, editWeight)) {
                            try {
                                AnimalUpdateDto dto = getValuesFromUpdateInputs();
                                updateAnimal(animal.getId(), dto);
                            } catch (Exception e) {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Invalid data", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }*/
                        dialog.cancel();
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


}
