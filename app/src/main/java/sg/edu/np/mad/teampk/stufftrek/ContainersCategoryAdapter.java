package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContainersCategoryAdapter extends RecyclerView.Adapter<ContainersCategoryViewHolder> {
    Context context;
    ArrayList<ContainerCategory> containerCategoriesList;
    int locationId;
    int roomId;

    public ContainersCategoryAdapter(Context c, ArrayList<ContainerCategory> ccList, int locId, int rmId)
    {
        context = c;
        containerCategoriesList = ccList;
        locationId = locId;
        roomId = rmId;
    }

    @NonNull
    @Override
    public ContainersCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_containers_cat, parent, false);
        return new ContainersCategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ContainersCategoryViewHolder holder, int position) {
        ContainerCategory containerCat = containerCategoriesList.get(position);
        holder.containerCatTV.setText(containerCat.Name);

        DBHandler db = new DBHandler(context, null, null, 1);
        ArrayList<Container> containerList = db.GetAllContainerFromContainerCategory(containerCat.getContainerCategoryID());
        db.close();

        if (containerList.size() == 0)
        {
            holder.noContainerTV.setText("There are no \"" + containerCat.Name + "\" containers.");
        }
        else
        {
            holder.noContainerTV.setVisibility(View.GONE);
            ContainersAdapter ca = new ContainersAdapter(context, containerList, locationId, roomId);
            LinearLayoutManager lm = new LinearLayoutManager(context);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.containerRV.setAdapter(ca);
            holder.containerRV.setLayoutManager(lm);
        }
    }

    @Override
    public int getItemCount() {
        return containerCategoriesList.size();
    }
}
