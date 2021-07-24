package sg.edu.np.mad.teampk.stufftrek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    // for layout
    Context context;
    // for the constructor parameter
    ArrayList<Location> locationList;

    // Constructor for LocationAdapter
    public LocationAdapter(Context ctx, ArrayList<Location> locList)
    {
        context = ctx;
        locationList = locList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_location, parent, false);
        return new LocationViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location loc = locationList.get(position);

        // Set the location TextView to the location's name.
        holder.locationName.setText(loc.Name);

        // hidden
        holder.locIdTV.setText("" + loc.getLocationID());

        // OnClickListener for the ViewHolder
        holder.locationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the LocationDetailsActivity
                Intent locationDetailsActivity = new Intent(context, LocationDetailsActivity.class);

                // Pass the following information along with the intent.
                Bundle locationInformation = new Bundle();
                locationInformation.putString("LocationName", loc.Name);
                locationInformation.putInt("LocationID", loc.getLocationID());
                locationDetailsActivity.putExtras(locationInformation);

                // Trigger Activity
                context.startActivity(locationDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


    // Nested Viewholder Class
    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView locationName;
        public TextView locIdTV;
        public ConstraintLayout locationContainer;
        int locationId;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationNameTV);
            locationContainer = itemView.findViewById(R.id.locationVH);
            locIdTV = itemView.findViewById(R.id.locationId);

            itemView.setOnCreateContextMenuListener(this);
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
                locationId = Integer.parseInt(String.valueOf(locIdTV.getText()));
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
                        createTitle.setText("Location Name: ");
                        dialogAddBtn.setText("Update Location");
                        createField.setText(locationName.getText());
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
                                String locationName = createField.getText().toString();

                                if(locationName.length() == 0)
                                {
                                    errorMsgText.setText("Location name cannot be empty!");
                                    errorMsgText.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    DBHandler db = new DBHandler(context, null, null, 1);
                                    int getLocationIndex = getArrayPosition(locationId);
                                    Location loc = locationList.get(getLocationIndex);
                                    loc.Name = locationName;
                                    db.UpdateLocation(loc);
                                    locationList.set(getLocationIndex, loc);
                                    // update list
                                    notifyItemChanged(getLocationIndex);
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
                                db.DeleteLocation(locationId);
                                locationList.remove(getArrayPosition(locationId));
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

        public int getArrayPosition(int locId)
        {
            int arrayPos = -1;

            for (int i = 0; i < locationList.size(); i++)
            {
                Location loc = locationList.get(i);
                if (loc.getLocationID() == locId)
                {
                    arrayPos = i;
                    break;
                }
            }
            return arrayPos;
        }
    }
}
