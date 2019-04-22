package stanisalv.danylenko.coursepet.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.adapter.DiseaseRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.adapter.GraftRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.entity.Disease;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDiseaseCreateDto;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraft;

public class GraftActivity extends AppCompatActivity {

    private PetApplication application;

    private Context context;

    private List<AnimalGraft> animalGrafts;

    private GraftRecyclerViewAdapter rwAdapter;

    private Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graft);

        context = this;

        application = (PetApplication) getApplication();
        animalGrafts = application.getAnimalGrafts();

        // RW
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.graft_recyclerview_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        rwAdapter = new GraftRecyclerViewAdapter(this, animalGrafts, application);
        recyclerView.setAdapter(rwAdapter);

        // FAB
        FloatingActionButton fab = findViewById(R.id.add_graft_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddDialog();
            }
        });
    }

    public void getAddDialog() {

//        createDto = new AnimalDiseaseCreateDto();
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.add_disease_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Add disease");

        /*startDateTime = (TextView) promptView.findViewById(R.id.date_of_disease_start_value);
        endDateTime = (TextView) promptView.findViewById(R.id.date_of_disease_end_value);

        treatment = (EditText) promptView.findViewById(R.id.treatment);

        setInitialStartDateTime();
        setInitialEndDateTime();

        final Spinner dropdownDisease = (Spinner) promptView.findViewById(R.id.animal_disease);
        final ArrayAdapter<Disease> adapterDisease = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, diseases);
        dropdownDisease.setAdapter(adapterDisease);
        dropdownDisease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Disease disease = (Disease) dropdownDisease.getSelectedItem();
                createDto.setDiseaseId(disease.getId());
                currentDisease = disease;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dropdownDisease.setSelection(0);*/

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*// todo add actions here
                        if(validateValuesFromUpdateInputs()) {
                            getValuesFromAddInputs(createDto, treatment);
                            addDisease(createDto);
                        }*/
                        dialog.dismiss();
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


    public void selectStartDate(View view) {
        new DatePickerDialog(GraftActivity.this, startDayListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener startDayListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialStartDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialStartDateTime() {
        /*dateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));*/
    }

    // TODO: 22.04.2019 Finish next 2 methods
    private boolean validateValuesFromUpdateInputs() {
        return true;
    }

    private void getValuesFromAddInputs() {

    }

    private void handleSuccessAdding(AnimalDisease animalDisease){
       /* animalDiseases.add(animalDisease);
        rwAdapter.notifyDataSetChanged();*/
    }

    private void handleFailedAdding(){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText("Error while saving, try later!")
                .show();
    }
}