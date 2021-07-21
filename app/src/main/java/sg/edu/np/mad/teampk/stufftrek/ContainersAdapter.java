package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContainersAdapter extends RecyclerView.Adapter<ContainersViewHolder>{

    Context context;
    ArrayList<Container> containerList;

    public ContainersAdapter(Context c, ArrayList<Container> cList)
    {
        context = c;
        containerList = cList;
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
        if (container.Picture != null)
        {
            holder.containerBtn.setImageBitmap(BitmapFactory.decodeFile(container.Picture));
        }
        // code to change the container imagebutton image resource

        // Intent to respective containerActivity when imagebutton is clicked.
        holder.containerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent containerActivity = new Intent(context, ItemsActivity.class);
                containerActivity.putExtra("ContainerID", container.getContainerID());
                containerActivity.putExtra("ContainerName", container.Name);
                context.startActivity(containerActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return containerList.size();
    }
}
