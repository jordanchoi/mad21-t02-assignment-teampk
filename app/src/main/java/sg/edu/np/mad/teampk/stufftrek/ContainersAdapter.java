package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContainersAdapter extends RecyclerView.Adapter<ContainersViewHolder>{

    Context context;
    ArrayList<Container> containerList;
    int locationId;
    int roomId;

    public ContainersAdapter(Context c, ArrayList<Container> cList, int locId, int rmId)
    {
        context = c;
        containerList = cList;
        locationId = locId;
        roomId = rmId;
    }

    @NonNull
    @Override
    public ContainersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_containers, parent, false);

        return new ContainersViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ContainersViewHolder holder, int position) {
        Container container = containerList.get(position);
        holder.containerName.setText(container.Name);
        // code to change the container imagebutton image resource
        if (container.Picture != null)
        {
            holder.containerBtn.setImageBitmap(BitmapFactory.decodeFile(container.Picture));
        }
        // Intent to respective containerActivity when imagebutton is clicked.
        holder.containerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemsActivity = new Intent(context, ItemsActivity.class);
                Bundle fkInfo = new Bundle();
                fkInfo.putInt("ContainerID", container.getContainerID());
                fkInfo.putString("ContainerName", container.Name);
                fkInfo.putInt("LocationID", locationId);
                fkInfo.putInt("RoomID", roomId);
                fkInfo.putInt("ContainerCatID", container.getContainerCategoryID());
                itemsActivity.putExtras(fkInfo);
                context.startActivity(itemsActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return containerList.size();
    }
}
