package stanisalv.danylenko.coursepet.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.adapter.GraftRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.adapter.RecordRecyclerViewAdapter;
import stanisalv.danylenko.coursepet.entity.Record;
import stanisalv.danylenko.coursepet.entity.SmartDevice;
import stanisalv.danylenko.coursepet.entity.animal.Animal;

public class RecordActivity extends AppCompatActivity {

    private PetApplication application;

    private Context context;
    private Animal animal;
    private List<Record> records;

    private RecordRecyclerViewAdapter rwAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        context = this;

        application = (PetApplication) getApplication();

        animal = (Animal) getIntent().getSerializableExtra("Animal");
        records = getRecords(animal.getSmartDevices());

        // RW
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.record_recyclerview_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        rwAdapter = new RecordRecyclerViewAdapter(this, records, application);
        recyclerView.setAdapter(rwAdapter);

    }

    private List<Record> getRecords(List<SmartDevice> animalSmartDevices ) {

        List<Record> records = new ArrayList<>();

        for(SmartDevice smartDevice : animalSmartDevices) {
            records.addAll(smartDevice.getRecords());
        }

        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getCreationDate().compareTo(o2.getCreationDate());
            }
        });

        return records;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.change_order :
                Collections.reverse(records);
                rwAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
