package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    // for layout
    Context context;

    // for the constructor parameter
    ArrayList<Category> categoryList;

    DBHandler db = null;

    // Constructor for CategoryAdapter
    public CategoryAdapter(Context ctx, ArrayList<Category> catList)
    {
        context = ctx;
        categoryList = catList;
        db = new DBHandler(context, null, null, 1);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_category, parent, false);
        return new CategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cat = categoryList.get(position);

        // Set the category TextView to the category's name.
        holder.categoryName.setText(cat.Name);
        DBHandler db = new DBHandler(context, null, null, 1);
        int count = db.GetCategoryCount(cat.getCategoryID());
        cat.setCount(count);
        holder.categoryCount.setText(String.valueOf(count));

        // OnClickListener for the ViewHolder
        holder.categoryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,CategoryItemsActivity.class);
                i.putExtra("catName",cat.Name);
                i.putExtra("catID",cat.getCategoryID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void removeItem(int position) {
        //db.DeleteCategory(categoryList.get(position).getCategoryID());
        categoryList.remove(position);
    }

    public void restoreItem(Category item) {
        categoryList.add(item);
        db.AddCategory(item);
    }
}
