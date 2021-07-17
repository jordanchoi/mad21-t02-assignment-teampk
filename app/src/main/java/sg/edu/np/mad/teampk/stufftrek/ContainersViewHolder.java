package sg.edu.np.mad.teampk.stufftrek;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContainersViewHolder extends RecyclerView.ViewHolder {

    TextView containerName;
    ImageButton containerBtn;

    public ContainersViewHolder(@NonNull View itemView) {
        super(itemView);

        containerName = itemView.findViewById(R.id.containerNameTV);
        containerBtn = itemView.findViewById(R.id.containerIB);
    }
}
