package kelvin.link.dontborderme_app;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailDialog extends AppCompatDialogFragment {
    private String logMessage = "EventDetailDialog";
    public static final String EXTRA_EVENT_ID= "EXTRA_EVENT_ID";
    public static final String EXTRA_EVENT_TITLE= "EXTRA_EVENT_TITLE";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_START_TS= "EXTRA_START_TS";

    public static final int EDIT_EVENT_REQUEST = 2;

    private ImageView mImageView;
    private ImageView mEditImage;
    private TextView textViewTitle;
    private TextView textViewAddress;
    private TextView textViewDescription;
    private TextView textViewStartTs;


    //private int icon;
    //private int editImage;
    private String event_title;
    private Integer event_id;
    private String address;
    private String description;
    private String start_ts;


    private EventDetailListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View itemView = inflater.inflate(R.layout.event_detail_dialog, null);

        //mImageView = itemView.findViewById(R.id.detail_dialog_icon);
        mEditImage = itemView.findViewById(R.id.detail_dialog_edit);
        textViewTitle = itemView.findViewById(R.id.detail_dialog_title);
        textViewAddress = itemView.findViewById(R.id.detail_dialog_address);
        textViewDescription = itemView.findViewById(R.id.detail_dialog_description);
        textViewStartTs = itemView.findViewById(R.id.detail_dialog_startTs);


        mEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SendAddEditEventActivity.class);
                intent.putExtra(SendAddEditEventActivity.EXTRA_EVENT_ID, event_id);
                intent.putExtra(SendAddEditEventActivity.EXTRA_EVENT_TITLE, event_title);
                intent.putExtra(SendAddEditEventActivity.EXTRA_ADDRESS, address);
                intent.putExtra(SendAddEditEventActivity.EXTRA_DESCRIPTION, description);
                intent.putExtra(SendAddEditEventActivity.EXTRA_START_TS, start_ts);
                startActivityForResult(intent, EDIT_EVENT_REQUEST);
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            event_title = bundle.getString("EXTRA_EVENT_TITLE", "null");
            event_id = bundle.getInt("EXTRA_EVENT_ID", -1);
            address = bundle.getString("EXTRA_ADDRESS", "null");
            description = bundle.getString("EXTRA_DESCRIPTION", "null");
            start_ts = bundle.getString("EXTRA_START_TS", "null");
        }



        textViewTitle.setText(event_title);
        textViewAddress.setText(address);
        textViewDescription.setText(description);
        textViewStartTs.setText(start_ts);


        builder.setView(itemView)
                .setTitle("Event Details")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Nothing
                        listener.onResult();
                    }
                });


        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (EventDetailListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " Must implement EventDetailListener");
        }
    }

    public interface EventDetailListener{
        void onResult();
    }
}
