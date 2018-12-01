package kelvin.link.dontborderme_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.ExampleViewHolder> {
    private List<EventItem> eventItemList = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setEventItems(List<EventItem> eventItems){
        this.eventItemList = eventItems;
        notifyDataSetChanged();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        //public ImageView mImageView;
        private TextView textViewTitle;
        private TextView textViewAddress;
        private TextView textViewDescription;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewDescription = itemView.findViewById(R.id.text_viw_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onItemClick(postion);
                        }
                    }
                }
            });

            /*mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(postion);
                        }
                    }
                }
            });*/
        }
    }



    public EventItemAdapter(ArrayList<EventItem> eventItems){
        eventItemList = eventItems;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        //holder.mImageView.setImageResource(currentItem.getmImageResource());
        EventItem currentItem = eventItemList.get(position);
        holder.textViewTitle.setText(currentItem.getEvent().getEvent_title());
        holder.textViewDescription.setText(currentItem.getEvent().getDescription());
        holder.textViewAddress.setText(currentItem.getEvent().getAddress());
    }

    @Override
    public int getItemCount() {
        return eventItemList.size();
    }
}
