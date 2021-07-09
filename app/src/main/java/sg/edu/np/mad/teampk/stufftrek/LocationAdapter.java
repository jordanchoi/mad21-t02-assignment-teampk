package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    // for layout
    Context context;

    // for the constructor parameter
    ArrayList<Location> locationList;

    // Constructor for LocationAdapter
    public LocationAdapter(Context ctx, ArrayList<Location> locList)
    {
        context = ctx;
        locationList = locList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_location, parent, false);
        return new LocationViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location loc = locationList.get(position);

        // Set the location TextView to the location's name.
        holder.locationName.setText(loc.Name);

        // OnClickListener for the ViewHolder
        holder.locationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the LocationDetailsActivity
                Intent locationDetailsActivity = new Intent(context, LocationDetailsActivity.class);

                // Pass the following information along with the intent.
                Bundle locationInformation = new Bundle();
                locationInformation.putString("LocationName", loc.Name);
                locationInformation.putInt("LocationID", loc.getLocationID());
                locationDetailsActivity.putExtras(locationInformation);

                // Trigger Activity
                context.startActivity(locationDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
