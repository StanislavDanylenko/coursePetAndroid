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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.adapter.SmartDeviceRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.entity.Graft;
import stanisalv.danylenko.coursepet.entity.SmartDevice;
import stanisalv.danylenko.coursepet.entity.SmartDeviceDto;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraft;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraftCreateDto;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.GraftService;
import stanisalv.danylenko.coursepet.network.retrofit.SmartDeviceService;

public class SmartDeviceActivity extends AppCompatActivity {

    private PetApplication application;

    private Context context;

    private Animal animal;
    private List<SmartDevice> smartDevices;

    private SmartDeviceDto createDto;

    private SmartDeviceRecyclerViewAdapter rwAdapter;

    private EditText sdName;
    private EditText sdMac;
    private EditText sdBatteryLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_device);

        context = this;

        application = (PetApplication) getApplication();

//        animal = (Animal) getIntent().getSerializableExtra("Animal");
        animal = application.getAnimal();
        smartDevices = animal.getSmartDevices();

        // RW
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sd_recyclerview_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        rwAdapter = new SmartDeviceRecyclerViewAdapter(this, smartDevices, application);
        recyclerView.setAdapter(rwAdapter);

        // FAB
        FloatingActionButton fab = findViewById(R.id.add_sd_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddDialog();
            }
        });
    }

    public void getAddDialog() {

        createDto = new SmartDeviceDto();
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.add_sd_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle(getString(R.string.add_sd));

        sdName = promptView.findViewById(R.id.name);
        sdMac = promptView.findViewById(R.id.mac);
        sdBatteryLevel = promptView.findViewById(R.id.battery_level);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // todo add actions here
                        if(validateValuesFromUpdateInputs()) {
                            getValuesFromAddInputs(createDto);
                            addGraft(createDto);
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

    // TODO: 22.04.2019 Finish next 2 methods
    private boolean validateValuesFromUpdateInputs() {
        return true;
    }

    private void getValuesFromAddInputs(SmartDeviceDto dto) {
        dto.setAnimalId(animal.getId());
        dto.setName(sdName.getText().toString());
        dto.setMac(sdMac.getText().toString());
        dto.setBatteryLevel(Double.parseDouble(sdBatteryLevel.getText().toString()));
    }

    private void handleSuccessAdding(SmartDevice smartDevice){
        smartDevices.add(smartDevice);
        rwAdapter.notifyDataSetChanged();
    }

    private void handleFailedAdding(){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.error))
                .setContentText(getString(R.string.error_saving))
                .show();
    }

    private void addGraft(final SmartDeviceDto createDto) {

        RetrofitService retrofitService = application.getRetrofitService();
        SmartDeviceService service = retrofitService.getRetrofit().create(SmartDeviceService.class);

        service.addSmartDevice(application.getTOKEN(), createDto).enqueue(new Callback<SmartDevice>() {
            @Override
            public void onResponse(Call<SmartDevice> call, Response<SmartDevice> response) {
                if(response.isSuccessful()) {
                    SmartDevice sDevice = response.body();
                    handleSuccessAdding(sDevice);
                } else {
                    handleFailedAdding();
                }
            }

            @Override
            public void onFailure(Call<SmartDevice> call, Throwable throwable) {
                handleFailedAdding();
            }
        });

    }
}
