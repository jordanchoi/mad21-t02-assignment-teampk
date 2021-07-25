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

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
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
        // set text for roomId for manipulation from context menu
        holder.roomId.setText("" + r.getRoomID());
        holder.roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RoomActivity.class);
                Bundle roomInformation = new Bundle();
                roomInformation.putInt("RoomID", r.getRoomID());
                roomInformation.putInt("LocationID", LocationID);
                roomInformation.putString("RoomName", r.Name);
                i.putExtras(roomInformation);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    // Nested Class - RoomViewHolder within the Adapter

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public Button roomBtn;
        public ConstraintLayout roomList;
        public View view;
        public TextView roomId;
        int rmId;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomBtn = itemView.findViewById(R.id.roomBtn);
            roomList = itemView.findViewById(R.id.roomList);
            roomId = itemView.findViewById(R.id.itemId);
            view = itemView;

            roomBtn.setOnCreateContextMenuListener(this);
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
                rmId = Integer.parseInt(String.valueOf(roomId.getText()));
                switch (item.getItemId()) {
                    case 1:
                        // Edit
                        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
                        dialog.setContentView(R.layout.dialog_create);

                        // Get the respective items in the view
                        TextView createTitle = dialog.findViewById(R.id.dialogTitleTV);
                        EditText createField = dialog.findViewById(R.id.dialogFieldET);
                        TextView errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                        ImageButton dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                        Button dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                        // Set the respective texts of the items in the view
                        createTitle.setText("Room Name: ");
                        dialogAddBtn.setText("Update Room");
                        createField.setText(roomBtn.getText());
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
                                String newRoomName = createField.getText().toString();

                                if(newRoomName.length() == 0)
                                {
                                    errorMsgText.setText("Room name cannot be empty!");
                                    errorMsgText.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    DBHandler db = new DBHandler(context, null, null, 1);
                                    int getRoomIndex = getArrayPosition(rmId);
                                    Room room = data.get(getRoomIndex);
                                    room.Name = newRoomName;
                                    db.UpdateRoom(room);
                                    data.set(getRoomIndex, room);
                                    // update list
                                    notifyItemChanged(getRoomIndex);
                                    dialog.cancel();
                                }
                            }
                        });
                        Window window = dialog.getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        dialog.show();
                        break;

                    case 2:
                        // Delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm Delete").setMessage("You are about to delete a room which may contain containers and items!\n\nAll containers & containers category within will be deleted.\n\nAny items within this location will be unassigned from its location.").setCancelable(false).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBHandler db = new DBHandler(context, null, null, 1);
                                db.DeleteRoom(rmId);
                                data.remove(getArrayPosition(rmId));
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

        public int getArrayPosition(int rmId)
        {
            int arrayPos = -1;
            for (int i = 0; i < data.size(); i++)
            {
                Room rm = data.get(i);
                if (rm.getRoomID() == rmId)
                {
                    arrayPos = i;
                    break;
                }
            }
            return arrayPos;
        }
    }
}
