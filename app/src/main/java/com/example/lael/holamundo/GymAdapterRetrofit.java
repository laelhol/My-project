package com.example.lael.holamundo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GymAdapterRetrofit extends RecyclerView.Adapter<GymAdapterRetrofit.GymViewHolder>{

    private List<GymPostRetrofit> GYMS;
    private LayoutInflater rowLayout;
    private Context context;

    public static class GymViewHolder extends RecyclerView.ViewHolder{
        TextView GymName;
        TextView GymDescription;
        TextView GymAddress;
        TextView GymPhone;
        TextView GymOpen;
        TextView GymClose;

        public GymViewHolder(View v) {
            super(v);
            GymName = (TextView)v.findViewById(R.id.TVname2);
            GymDescription = (TextView)v.findViewById(R.id.TVdescription2);
            GymAddress = (TextView)v.findViewById(R.id.TVaddress2);
            GymPhone = (TextView)v.findViewById(R.id.TVphone2);
            GymOpen = (TextView)v.findViewById(R.id.TVopen2);
            GymClose = (TextView)v.findViewById(R.id.TVclose2);
        }
    }

    public GymAdapterRetrofit(List<GymPostRetrofit> GYMS,  Context context)
    {
        this.GYMS = GYMS;
        this.rowLayout = LayoutInflater.from(context);

    }


    @Override
    public GymViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = rowLayout.inflate(R.layout.activity_gym_post, parent, false);
        return new GymViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GymViewHolder holder, int position) {
        GymPostRetrofit gyms = GYMS.get(position);
        holder.GymName.setText(gyms.getName());
        holder.GymDescription.setText(gyms.getDescription());
        holder.GymAddress.setText(gyms.getAddress());
        holder.GymPhone.setText(gyms.getPhone());
        holder.GymOpen.setText(gyms.getOpen());
        holder.GymClose.setText(gyms.getClose());
    }

    @Override
    public int getItemCount() {
        return GYMS.size();
    }
}
