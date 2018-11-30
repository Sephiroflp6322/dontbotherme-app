package kelvin.link.dontborderme_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private List<EventRoom> events = new ArrayList<>();
    private OnItemClickListener listener;

    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        EventRoom currentEvent = events.get(position);
        holder.textViewTitle.setText(currentEvent.getEvent_title());
        holder.textViewDescription.setText(currentEvent.getDescription());
        holder.textViewAddress.setText(currentEvent.getAddress());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<EventRoom> events){
        this.events = events;
        notifyDataSetChanged();
    }


    public EventRoom getEventAt(int position){
        return events.get(position);
    }


    class EventHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewAddress;
        private TextView textViewDescription;


        public EventHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewDescription = itemView.findViewById(R.id.text_viw_description);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(events.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EventRoom event);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
