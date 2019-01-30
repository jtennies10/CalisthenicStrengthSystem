package tenniescorp.fitness.calisthenicstrengthsystem;

/*Code adapted from
https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder> {

    class RoutineViewHolder extends RecyclerView.ViewHolder {
        private final TextView routineItemView;

        private RoutineViewHolder(View itemView) {
            super(itemView);
            routineItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater inflater;
    private List<Routine> routineList; // Cached copy of words

    RoutineListAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RoutineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoutineViewHolder holder, int position) {
        if (routineList != null) {
            Routine current = routineList.get(position);
            holder.routineItemView.setText(current.getRoutineName());
        } else {
            // Covers the case of data not being ready yet.
            holder.routineItemView.setText("No Word");
        }
    }

    void setRoutines(List<Routine> words){
        routineList = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (routineList != null)
            return routineList.size();
        else return 0;
    }
}