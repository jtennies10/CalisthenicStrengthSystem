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

public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder> {

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

    RoutineListAdapter(Context context, RecyclerViewClickListener listListener) {
        inflater = LayoutInflater.from(context);
        this.listListener = listListener;
    }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RoutineViewHolder(itemView, listListener);
    }

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

    void setRoutines(List<Routine> routines){
        routineList = routines;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // routineList has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (routineList != null)
            return routineList.size();
        else return 0;
    }
}