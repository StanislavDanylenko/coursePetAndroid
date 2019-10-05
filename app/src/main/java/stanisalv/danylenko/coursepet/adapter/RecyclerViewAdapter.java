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

import com.squareup.picasso.Picasso;

import java.util.List;

import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.activity.AnimalViewActivity;
import stanisalv.danylenko.coursepet.entity.animal.Animal;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Animal> mData ;
    private PetApplication application;


    public RecyclerViewAdapter(Context mContext, List<Animal> mData, PetApplication application) {
        this.mContext = mContext;
        this.mData = mData;
        this.application = application;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_animal, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.animal_title.setText(mData.get(position).getName());

        String photoURL = mData.get(position).getPhotoURL();

        if(photoURL != null) {
            Picasso.get().load(photoURL).into(holder.img_animal_thumbnail);
        } else {
            holder.img_animal_thumbnail.setImageResource(R.drawable.logo);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AnimalViewActivity.class);
                // passing data to the book activity
                //intent.putExtra("Animal", mData.get(position));
                application.setAnimal(mData.get(position));
                // start the activity
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView animal_title;
        ImageView img_animal_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            animal_title = (TextView) itemView.findViewById(R.id.animal_name_id) ;
            img_animal_thumbnail = (ImageView) itemView.findViewById(R.id.animal_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}
