package sg.edu.np.mad.teampk.stufftrek;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsWithPathViewHolder extends RecyclerView.ViewHolder {
    public TextView itemsNameText;
    public TextView itemsCatText;
    public TextView itemsQtyText;
    public TextView itemsPathText;
    public ImageView itemsImage;
    public ConstraintLayout itemsContainer;

    public ItemsWithPathViewHolder(@NonNull View itemView) {
        super(itemView);

        itemsNameText = itemView.findViewById(R.id.itemNameTV);
        itemsCatText = itemView.findViewById(R.id.itemCatTV);
        itemsQtyText = itemView.findViewById(R.id.itemQtyTV);
        itemsPathText = itemView.findViewById(R.id.itemPathTV);
        itemsImage = itemView.findViewById(R.id.itemImgIV);
        itemsContainer = itemView.findViewById(R.id.itemsContainer);
    }
}
