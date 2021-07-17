package sg.edu.np.mad.teampk.stufftrek;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContainersCategoryViewHolder extends RecyclerView.ViewHolder {
    TextView containerCatTV;
    RecyclerView containerRV;
    TextView noContainerTV;

    public ContainersCategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        containerCatTV = itemView.findViewById(R.id.containerCategoryTV);
        containerRV = itemView.findViewById(R.id.containersRV);
        noContainerTV = itemView.findViewById(R.id.noContainerTV);
    }
}
