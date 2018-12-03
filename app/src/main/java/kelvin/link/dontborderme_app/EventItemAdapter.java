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
        void onIconClick(int position);
        void onTitleClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setEventItems(List<EventItem> eventItems){
        this.eventItemList = eventItems;
        notifyDataSetChanged();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private ImageView mEditImage;
        private TextView textViewTitle;
        private TextView textViewAddress;
        private TextView textViewDescription;
        private TextView textViewStartTs;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.subscribe_icon);
            mEditImage = itemView.findViewById(R.id.subscribe_edit);
            textViewTitle = itemView.findViewById(R.id.subscribe_title);
            textViewAddress = itemView.findViewById(R.id.subscribe_address);
            textViewDescription = itemView.findViewById(R.id.subscribe_description);
            textViewStartTs = itemView.findViewById(R.id.subscribe_startTs);



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

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onTitleClick(postion);
                        }
                    }
                }
            });

            mEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onEditClick(postion);
                        }
                    }
                }
            });


            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onIconClick(postion);
                        }
                    }
                }
            });

        }
    }



    public EventItemAdapter(ArrayList<EventItem> eventItems){
        eventItemList = eventItems;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO EventItemAdapter always use subscribe_event_item as inflate layout, fix it later
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_event_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        EventItem currentItem = eventItemList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.textViewTitle.setText(currentItem.getEvent().getEvent_title());
        holder.textViewDescription.setText(currentItem.getEvent().getDescription());
        holder.textViewAddress.setText(currentItem.getEvent().getAddress());
        holder.textViewStartTs.setText(currentItem.getEvent().getStart_ts());
    }

    @Override
    public int getItemCount() {
        return eventItemList.size();
    }
}
