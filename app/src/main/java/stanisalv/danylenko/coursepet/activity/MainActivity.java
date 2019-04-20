package stanisalv.danylenko.coursepet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.adapter.RecyclerViewAdapter;
import stanisalv.danylenko.coursepet.entity.Animal;

public class MainActivity extends AppCompatActivity {

    private PetApplication application;
    private List<Animal> animals;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        application = (PetApplication) getApplication();
        animals = application.getAnimals();

        /*animals = new ArrayList<>();
        animals.add(new Book("The Vegitarian","Categorie Book","Description book",R.drawable.thevigitarian));
        animals.add(new Book("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        animals.add(new Book("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        animals.add(new Book("The Martian","Categorie Book","Description book",R.drawable.themartian));
        animals.add(new Book("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        animals.add(new Book("The Vegitarian","Categorie Book","Description book",R.drawable.thevigitarian));
        animals.add(new Book("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        animals.add(new Book("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        animals.add(new Book("The Martian","Categorie Book","Description book",R.drawable.themartian));
        animals.add(new Book("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        animals.add(new Book("The Vegitarian","Categorie Book","Description book",R.drawable.thevigitarian));
        animals.add(new Book("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        animals.add(new Book("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        animals.add(new Book("The Martian","Categorie Book","Description book",R.drawable.themartian));
        animals.add(new Book("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));*/



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, animals);
        recyclerView.setAdapter(myAdapter);



        // FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAnimalActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings :
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.save_settings:
                Intent intent1 = new Intent(this, StatisticActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAlertA(View view) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.sample_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Title");

        //final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, "from input text here", Toast.LENGTH_LONG).show();
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
