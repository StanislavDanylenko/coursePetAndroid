package stanisalv.danylenko.coursepet.adapter;

import android.content.Context;
import android.content.Intent;
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
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.activity.AnimalViewActivity;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;

public class DiseaseRecyclerViewAdapter extends RecyclerView.Adapter<DiseaseRecyclerViewAdapter.DiseaseViewHolder> {

    private Context mContext ;
    private List<AnimalDisease> mData ;


    public DiseaseRecyclerViewAdapter(Context mContext, List<AnimalDisease> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_disease, parent,false);
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
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
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

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {

        TextView disease_name;
        TextView disease_start_date;
        TextView disease_end_date;
        TextView disease_treatment;

        CardView cardView ;

        public DiseaseViewHolder(View itemView) {
            super(itemView);

            disease_name = (TextView) itemView.findViewById(R.id.disease_name) ;
            disease_start_date = (TextView) itemView.findViewById(R.id.disease_start_date) ;
            disease_end_date = (TextView) itemView.findViewById(R.id.disease_end_date) ;
            disease_treatment = (TextView) itemView.findViewById(R.id.disease_treatment) ;
            cardView = (CardView) itemView.findViewById(R.id.disease_card_id);


        }
    }


}
