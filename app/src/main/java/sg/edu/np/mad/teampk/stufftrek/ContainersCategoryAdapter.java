package sg.edu.np.mad.teampk.stufftrek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ContainersCategoryAdapter extends RecyclerView.Adapter<ContainersCategoryAdapter.ContainersCategoryViewHolder> {
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
        // hidden for manipulation from contextual menu
        holder.containerCategoryId.setText("" + containerCat.getContainerCategoryID());

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

    public class ContainersCategoryViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{
        TextView containerCatTV;
        RecyclerView containerRV;
        TextView noContainerTV;
        TextView containerCategoryId;
        int ccId;

        public ContainersCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            containerCatTV = itemView.findViewById(R.id.containerCategoryTV);
            containerRV = itemView.findViewById(R.id.containersRV);
            noContainerTV = itemView.findViewById(R.id.noContainerTV);
            containerCategoryId= itemView.findViewById(R.id.containerCategoryId);

            containerCatTV.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        //ADD AN ONMENUITEM LISTENER TO EXECUTE COMMANDS ONCLICK OF CONTEXT MENU TASK
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ccId = Integer.parseInt(String.valueOf(containerCategoryId.getText()));
                switch (item.getItemId()) {
                    case 1:
                        //Do stuff
                        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
                        dialog.setContentView(R.layout.dialog_create);


                        // Get the respective items in the view
                        TextView createTitle = dialog.findViewById(R.id.dialogTitleTV);
                        EditText createField = dialog.findViewById(R.id.dialogFieldET);
                        TextView errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                        ImageButton dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                        Button dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                        // Set the respective texts of the items in the view
                        createTitle.setText("Container Category Name: ");
                        dialogAddBtn.setText("Update Container Category");
                        createField.setText(containerCatTV.getText());
                        createField.setSelectAllOnFocus(true);

                        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });

                        dialogAddBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String containerCategoryName = createField.getText().toString();

                                if(containerCategoryName.length() == 0)
                                {
                                    errorMsgText.setText("Container Category name cannot be empty!");
                                    errorMsgText.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    DBHandler db = new DBHandler(context, null, null, 1);
                                    int containerCategoryIndex = getArrayPosition(ccId);
                                    ContainerCategory cc = containerCategoriesList.get(containerCategoryIndex);
                                    cc.Name=containerCategoryName;
                                    db.UpdateContainerCategory(cc);
                                    containerCategoriesList.set(containerCategoryIndex,cc);
                                    // update list
                                    notifyItemChanged(containerCategoryIndex);
                                    dialog.cancel();
                                }
                            }
                        });
                        Window window = dialog.getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        dialog.show();
                        break;

                    case 2:
                        //Do stuff
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm Delete").setMessage("You are about to delete a top-level location!\nAll rooms, containers, containers location will be deleted.\nAny items within this location will be unassigned from its location.").setCancelable(false).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBHandler db = new DBHandler(context, null, null, 1);
                                db.DeleteContainerCategory(ccId);
                                containerCategoriesList.remove(getArrayPosition(ccId));
                                notifyDataSetChanged();
                                db.close();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // cancels the dialog
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                }
                return true;
            }
        };

        public int getArrayPosition(int containerCategoryId)
        {
            int arrayPos = -1;

            for (int i = 0; i < containerCategoriesList.size(); i++)
            {
                ContainerCategory cc = containerCategoriesList.get(i);
                if (cc.getContainerCategoryID() == containerCategoryId)
                {
                    arrayPos = i;
                    break;
                }
            }
            return arrayPos;
        }
    }

}
