package stanisalv.danylenko.coursepet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.SmartDevice;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.GraftService;
import stanisalv.danylenko.coursepet.network.retrofit.SmartDeviceService;

public class SmartDeviceRecyclerViewAdapter extends RecyclerView.Adapter<SmartDeviceRecyclerViewAdapter.SmartDeviceViewHolder> {

    private PetApplication application;

    private Context mContext;
    private List<SmartDevice> mData;
    private SmartDeviceRecyclerViewAdapter thisAdapter;


    public SmartDeviceRecyclerViewAdapter(Context mContext, List<SmartDevice> mData, PetApplication application) {
        this.mContext = mContext;
        this.mData = mData;
        this.application = application;
        thisAdapter = this;
    }

    @Override
    public SmartDeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_sd, parent, false);
        return new SmartDeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SmartDeviceViewHolder holder, final int position) {

        holder.sd_name.setText(mData.get(position).getName());
        holder.sd_battery.setText(mData.get(position).getBatteryLevel().toString());
        holder.sd_mac.setText(mData.get(position).getMac());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(mContext.getString(R.string.are_you_sure))
                        .setContentText(mContext.getString(R.string.one_way))
                        .setConfirmText(mContext.getString(R.string.delete_agreement))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteSmartDevice(mData.get(position).getId(), position);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
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
                .setTitleText(mContext.getString(R.string.alert_suc_deleted))
                .setContentText(mContext.getString(R.string.alert_suc_deleted_sd))
                .show();
    }

    private void handleFailureDeleting() {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(mContext.getString(R.string.error))
                .setContentText(mContext.getString(R.string.error_delete_sd))
                .show();
    }

    public static class SmartDeviceViewHolder extends RecyclerView.ViewHolder {

        TextView sd_name;
        TextView sd_battery;
        TextView sd_mac;

        CardView cardView;

        public SmartDeviceViewHolder(View itemView) {
            super(itemView);

            sd_name = (TextView) itemView.findViewById(R.id.sd_name);
            sd_battery = (TextView) itemView.findViewById(R.id.sd_battery);
            sd_mac = (TextView) itemView.findViewById(R.id.sd_mac);

            cardView = (CardView) itemView.findViewById(R.id.sd_card_id);

        }
    }

}
