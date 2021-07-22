package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomAdapter  extends RecyclerView.Adapter<RoomViewHolder> {
    Context context;
    ArrayList<Room> data;
    Integer LocationID;

    public RoomAdapter(Context c, ArrayList<Room> d,Integer id){
        context=c;
        data=d;
        LocationID=id;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.vh_room, parent, false);

        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room r = data.get(position);
        holder.roomBtn.setText(r.Name);
        holder.roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RoomActivity.class);
                i.putExtra("RoomID", r.getRoomID());
                i.putExtra("RoomName", r.Name);
                i.putExtra("LocationID",LocationID);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
