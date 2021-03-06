package stanisalv.danylenko.coursepet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.activity.AnimalViewActivity;
import stanisalv.danylenko.coursepet.entity.Disease;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.DiseaseService;

public class DiseaseRecyclerViewAdapter extends RecyclerView.Adapter<DiseaseRecyclerViewAdapter.DiseaseViewHolder> {

    private PetApplication application;

    private Context mContext;
    private List<AnimalDisease> mData;
    private DiseaseRecyclerViewAdapter thisAdapter;


    public DiseaseRecyclerViewAdapter(Context mContext, List<AnimalDisease> mData, PetApplication application) {
        this.mContext = mContext;
        this.mData = mData;
        this.application = application;
        thisAdapter = this;
    }

    @Override
    public DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_disease, parent, false);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiseaseViewHolder holder, final int position) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        holder.disease_name.setText(mData.get(position).getDisease().getName());
        holder.disease_start_date.setText(dateFormatter.format(mData.get(position).getStartData()));
        holder.disease_end_date.setText(dateFormatter.format(mData.get(position).getEndDate()));
        holder.disease_treatment.setText(mData.get(position).getTreatment());

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
                                deleteDisease(mData.get(position).getId(), position);
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

    private void deleteDisease(Long diseaseId, final int position) {

        RetrofitService retrofitService = application.getRetrofitService();
        DiseaseService service = retrofitService.getRetrofit().create(DiseaseService.class);

        service.deleteDisease(application.getTOKEN(), diseaseId).enqueue(new Callback<Void>() {
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
                .setContentText(mContext.getString(R.string.alert_suc_deleted_disease))
                .show();
    }

    private void handleFailureDeleting() {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(mContext.getString(R.string.error))
                .setContentText(mContext.getString(R.string.error_delete_disease))
                .show();
    }

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {

        TextView disease_name;
        TextView disease_start_date;
        TextView disease_end_date;
        TextView disease_treatment;

        CardView cardView;

        public DiseaseViewHolder(View itemView) {
            super(itemView);

            disease_name = (TextView) itemView.findViewById(R.id.disease_name);
            disease_start_date = (TextView) itemView.findViewById(R.id.disease_start_date);
            disease_end_date = (TextView) itemView.findViewById(R.id.disease_end_date);
            disease_treatment = (TextView) itemView.findViewById(R.id.disease_treatment);
            cardView = (CardView) itemView.findViewById(R.id.disease_card_id);

        }
    }

}
