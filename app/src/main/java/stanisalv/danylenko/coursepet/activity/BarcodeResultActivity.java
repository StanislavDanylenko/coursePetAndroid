package stanisalv.danylenko.coursepet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import stanisalv.danylenko.coursepet.R;

public class BarcodeResultActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_result);

        textView = (TextView) findViewById(R.id.result);
        String res = getIntent().getStringExtra("res");

        textView.setText(res);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, BarcodeActivity.class);
        startActivity(intent);
    }

}
