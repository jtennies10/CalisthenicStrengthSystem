package tenniescorp.fitness.calisthenicstrengthsystem;

/*Code adapted from
https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
*/
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/*
Defines the adapter class used to handle lists of Routines.
 */
public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder> {

    /*
    Inner class defining the view holder for Routines
     */
    class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView routineItemView;
        private RecyclerViewClickListener listListener;

        private RoutineViewHolder(View itemView, RecyclerViewClickListener listListener) {
            super(itemView);
            routineItemView = itemView.findViewById(R.id.textView);
            this.listListener = listListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listListener.onClick(view, getAdapterPosition());
        }
    }

    private final LayoutInflater inflater;
    private List<Routine> routineList; // Cached copy of words
    private RecyclerViewClickListener listListener;

    /*
    Constructor that assigns the inflater a LayoutInflater based on the passed in Context
     */
    RoutineListAdapter(Context context, RecyclerViewClickListener listListener) {
        inflater = LayoutInflater.from(context);
        this.listListener = listListener;
    }

    /*
    Inflates the itemView when the ViewHolder is created
     */
    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RoutineViewHolder(itemView, listListener);
    }

    /*
    Populates the items in the list with the routine names if the routineList is not null
     */
    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        if (routineList != null) {
            Routine current = routineList.get(position);
            holder.routineItemView.setText(current.getRoutineName());
        } else {
            // Covers the case of data not being ready yet.
            holder.routineItemView.setText("No routines");
        }
    }

    /*
    Sets the routines in routineList and updates the adapter
     */
    void setRoutines(List<Routine> routines){
        routineList = routines;
        notifyDataSetChanged();
    }

    /*
    Returns the number of items in routineList or 0 if routineList is null.
     */
    @Override
    public int getItemCount() {
        if (routineList != null)
            return routineList.size();
        else return 0;
    }
}