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
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraft;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.DiseaseService;
import stanisalv.danylenko.coursepet.network.retrofit.GraftService;

public class GraftRecyclerViewAdapter extends RecyclerView.Adapter<GraftRecyclerViewAdapter.GraftViewHolder> {

    private PetApplication application;

    private Context mContext;
    private List<AnimalGraft> mData;
    private GraftRecyclerViewAdapter thisAdapter;


    public GraftRecyclerViewAdapter(Context mContext, List<AnimalGraft> mData, PetApplication application) {
        this.mContext = mContext;
        this.mData = mData;
        this.application = application;
        thisAdapter = this;
    }

    @Override
    public GraftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_graft, parent, false);
        return new GraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GraftViewHolder holder, final int position) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        holder.graft_name.setText(mData.get(position).getGraft().getName());
        holder.graft_date.setText(dateFormatter.format(mData.get(position).getDate()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteGraft(mData.get(position).getId(), position);
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

    private void deleteGraft(Long diseaseId, final int position) {

        RetrofitService retrofitService = application.getRetrofitService();
        GraftService service = retrofitService.getRetrofit().create(GraftService.class);

        service.deleteGraft(application.getTOKEN(), diseaseId).enqueue(new Callback<Void>() {
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

    public static class GraftViewHolder extends RecyclerView.ViewHolder {

        TextView graft_name;
        TextView graft_date;

        CardView cardView;

        public GraftViewHolder(View itemView) {
            super(itemView);

            graft_name = (TextView) itemView.findViewById(R.id.graft_name);
            graft_date = (TextView) itemView.findViewById(R.id.graft_date);
            cardView = (CardView) itemView.findViewById(R.id.graft_card_id);

        }
    }

}
