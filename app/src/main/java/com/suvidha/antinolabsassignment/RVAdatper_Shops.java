package com.suvidha.antinolabsassignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RVAdatper_Shops extends RecyclerView.Adapter<RVAdatper_Shops.RVView>{
    Context context;
    JSONArray jsonArray;

    public RVAdatper_Shops(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public RVView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_restaurants, parent, false);
        return new RVView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RVView holder, int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            JSONObject o = jsonObject.getJSONObject("restaurant");
            String rName = o.getString("name");
            holder.textView.setText(rName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class RVView extends RecyclerView.ViewHolder
    {

        TextView textView;
        public RVView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rv_item_r_name);
        }
    }
}
