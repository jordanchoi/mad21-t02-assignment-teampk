package sg.edu.np.mad.teampk.stufftrek;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LocationViewHolder extends RecyclerView.ViewHolder {
    public TextView locationName;
    public ConstraintLayout locationContainer;

    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);

        locationName = itemView.findViewById(R.id.locationNameTV);
        locationContainer = itemView.findViewById(R.id.locationVH);

    }
}
