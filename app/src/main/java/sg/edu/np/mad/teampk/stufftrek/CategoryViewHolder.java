package sg.edu.np.mad.teampk.stufftrek;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryName;
    public TextView categoryCount;
    public ConstraintLayout categoryContainer;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.categoryNameTV);
        categoryCount = itemView.findViewById(R.id.categoryCountTV);
        categoryContainer = itemView.findViewById(R.id.categoryVH);

    }
}
