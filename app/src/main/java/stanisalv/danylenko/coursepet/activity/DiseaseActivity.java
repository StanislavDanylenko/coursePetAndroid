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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.adapter.DiseaseRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.entity.Disease;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDiseaseCreateDto;
import stanisalv.danylenko.coursepet.entity.animal.AnimalsBreed;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.DiseaseService;

public class DiseaseActivity extends AppCompatActivity {

    private PetApplication application;

    private Context context;

    private AnimalDiseaseCreateDto createDto;

    private Animal animal;
    private List<AnimalDisease> animalDiseases;
    private List<Disease> diseases;
    private Disease currentDisease;

    private DiseaseRecyclerViewAdapter rwAdapter;

    private Calendar startDateAndTime = Calendar.getInstance();
    private Calendar endDateAndTime = Calendar.getInstance();

    private TextView startDateTime;
    private TextView endDateTime;

    private EditText treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        context = this;

        application = (PetApplication) getApplication();
        animalDiseases = application.getAnimalDiseases();
        diseases = application.getDiseases();
//        animal = (Animal) getIntent().getSerializableExtra("Animal");
        animal = application.getAnimal();
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

        createDto = new AnimalDiseaseCreateDto();
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.add_disease_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle(getString(R.string.add_disease));

        startDateTime = (TextView) promptView.findViewById(R.id.date_of_disease_start_value);
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
        dropdownDisease.setSelection(0);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // todo add actions here
                        if(validateValuesFromUpdateInputs()) {
                            getValuesFromAddInputs(createDto, treatment);
                            addDisease(createDto);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel),
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
        new DatePickerDialog(DiseaseActivity.this, startDayListener,
                startDateAndTime.get(Calendar.YEAR),
                startDateAndTime.get(Calendar.MONTH),
                startDateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener startDayListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDateAndTime.set(Calendar.YEAR, year);
            startDateAndTime.set(Calendar.MONTH, monthOfYear);
            startDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialStartDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialStartDateTime() {
        startDateTime.setText(DateUtils.formatDateTime(this,
                startDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    ///////////////////


    public void selectEndDate(View view) {
        new DatePickerDialog(DiseaseActivity.this, endDateListener,
                endDateAndTime.get(Calendar.YEAR),
                endDateAndTime.get(Calendar.MONTH),
                endDateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDateAndTime.set(Calendar.YEAR, year);
            endDateAndTime.set(Calendar.MONTH, monthOfYear);
            endDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialEndDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialEndDateTime() {
        endDateTime.setText(DateUtils.formatDateTime(this,
                endDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void addDisease(final AnimalDiseaseCreateDto createDto) {

        RetrofitService retrofitService = application.getRetrofitService();
        DiseaseService service = retrofitService.getRetrofit().create(DiseaseService.class);

        service.addDisease(application.getTOKEN(), createDto).enqueue(new Callback<AnimalDisease>() {
            @Override
            public void onResponse(Call<AnimalDisease> call, Response<AnimalDisease> response) {
                if(response.isSuccessful()) {
                    AnimalDisease aDisease = response.body();
                    aDisease.setDisease(currentDisease);
                    handleSuccessAdding(aDisease);
                } else {
                    handleFailedAdding();
                }
            }

            @Override
            public void onFailure(Call<AnimalDisease> call, Throwable throwable) {
                handleFailedAdding();
            }
        });

    }

    //////////
    // TODO: 22.04.2019 Finish next 2 methods
    private boolean validateValuesFromUpdateInputs() {
        return true;
    }

    private void getValuesFromAddInputs(AnimalDiseaseCreateDto dto, EditText treatment) {
        dto.setAnimalId(animal.getId());
        dto.setTreatment(treatment.getText().toString());
        dto.setStartData(startDateAndTime.getTime());
        dto.setEndDate(endDateAndTime.getTime());
    }

    private void handleSuccessAdding(AnimalDisease animalDisease){
        animalDiseases.add(animalDisease);
        rwAdapter.notifyDataSetChanged();
    }

    private void handleFailedAdding(){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.error))
                .setContentText(getString(R.string.error_saving))
                .show();
    }

}
