package com.omegadevs.todo;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by manasvi on 19-05-2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemsViewHolder>{


    public static class ItemsViewHolder extends RecyclerView.ViewHolder  {

        CardView cv;
        TextView task_name;
        TextView task_address;
        TextView task_date;
        TextView task_time;

        ItemsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setUseCompatPadding(true);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            task_name = (TextView)itemView.findViewById(R.id.taskname);
            task_address = (TextView)itemView.findViewById(R.id.taskaddress);
            task_date = (TextView) itemView.findViewById(R.id.taskdate);
            task_time = (TextView)itemView.findViewById(R.id.tasktime);

        }

    }

    List<Tasks> taskDetails;

    RecyclerAdapter(List<Tasks> taskDetails){

        this.taskDetails = taskDetails;

    }



    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ItemsViewHolder ivh = new ItemsViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {

        holder.task_name.setText(taskDetails.get(position).task_Name);
        holder.task_address.setText(taskDetails.get(position).task_Address);
        holder.task_date.setText(taskDetails.get(position).task_Date);
        holder.task_time.setText(taskDetails.get(position).task_Time);


    }

    @Override
    public int getItemCount() {
        return taskDetails.size();
    }


}
