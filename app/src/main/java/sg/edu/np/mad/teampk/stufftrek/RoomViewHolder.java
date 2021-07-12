package sg.edu.np.mad.teampk.stufftrek;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RoomViewHolder extends RecyclerView.ViewHolder{
    public Button roomBtn;
    public ConstraintLayout roomList;
    public View view;
    public RoomViewHolder(@NonNull View itemView) {
        super(itemView);
        roomBtn = itemView.findViewById(R.id.roomBtn);
        roomList = itemView.findViewById(R.id.roomList);
        view = itemView;
    }
}
