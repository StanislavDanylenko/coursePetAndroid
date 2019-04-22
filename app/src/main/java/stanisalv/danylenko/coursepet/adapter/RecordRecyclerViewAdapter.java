package stanisalv.danylenko.coursepet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.Record;
import stanisalv.danylenko.coursepet.entity.SmartDevice;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.SmartDeviceService;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.RecordViewHolder> {

    private PetApplication application;

    private Context mContext;
    private List<Record> mData;
    private RecordRecyclerViewAdapter thisAdapter;


    public RecordRecyclerViewAdapter(Context mContext, List<Record> mData, PetApplication application) {
        this.mContext = mContext;
        this.mData = mData;
        this.application = application;
        thisAdapter = this;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, final int position) {

        holder.record_animalState.setText("STATE: " + mData.get(position).getAnimalState().toString());
        holder.record_longitude.setText("Longitude: " + mData.get(position).getLongitude().toString());
        holder.record_latitude.setText("Latitude: " + mData.get(position).getLatitude().toString());
        holder.record_pulse.setText("Pulse: " + mData.get(position).getPulse().toString());
        holder.record_temperature.setText("Temperature: " + mData.get(position).getTemperature().toString());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        holder.record_creationDate.setText("Creation date: " + dateFormatter.format(mData.get(position).getCreationDate()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteSmartDevice(mData.get(position).getId(), position);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void deleteSmartDevice(Long sdId, final int position) {

        RetrofitService retrofitService = application.getRetrofitService();
        SmartDeviceService service = retrofitService.getRetrofit().create(SmartDeviceService.class);

        service.deleteSmartDevice(application.getTOKEN(), sdId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mData.remove(position);
                    handleSuccessDeleting();
                    thisAdapter.notifyDataSetChanged();
                } else {
                    handleFailureDeleting();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                handleFailureDeleting();
            }
        });

    }

    private void handleSuccessDeleting() {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Disease was deleted!")
                .show();
    }

    private void handleFailureDeleting() {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText("Cannot delete disease!")
                .show();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView record_animalState;
        TextView record_creationDate;
        TextView record_longitude;
        TextView record_latitude;
        TextView record_pulse;
        TextView record_temperature;

        CardView cardView;

        public RecordViewHolder(View itemView) {
            super(itemView);

            record_animalState = (TextView) itemView.findViewById(R.id.record_animalState);
            record_creationDate = (TextView) itemView.findViewById(R.id.record_creationDate);
            record_longitude = (TextView) itemView.findViewById(R.id.record_longitude);
            record_latitude = (TextView) itemView.findViewById(R.id.record_latitude);
            record_pulse = (TextView) itemView.findViewById(R.id.record_pulse);
            record_temperature = (TextView) itemView.findViewById(R.id.record_temperature);

            cardView = (CardView) itemView.findViewById(R.id.record_card_id);

        }
    }

}
