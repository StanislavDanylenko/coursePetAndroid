package stanisalv.danylenko.coursepet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.CountByBreedInCountryStatistic;

public class StatisticActivity extends AppCompatActivity {

    private PetApplication application;

    private List<CountByBreedInCountryStatistic> statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        application = (PetApplication)getApplication();
        statistic = application.getStatistic();

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        List<String> breedName = new ArrayList<>();
        List<BarEntry> countInCountry = new ArrayList<>();

        int xindex = 0;
        for(CountByBreedInCountryStatistic item : statistic) {
            breedName.add(item.getBreed());
            countInCountry.add(new BarEntry((float) item.getCount(), xindex++));
        }

        BarDataSet bardataset = new BarDataSet(countInCountry, getString(R.string.count_in_country));
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(breedName, bardataset);
        barChart.setData(data); // set the data and list of labels into chart

    }
}
