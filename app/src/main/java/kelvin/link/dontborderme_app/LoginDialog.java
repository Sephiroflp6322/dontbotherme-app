package kelvin.link.dontborderme_app;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginDialog extends AppCompatDialogFragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private LoginDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Not thon
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();

                        listener.onResult(username, password);
                    }
                });

        editTextUsername = view.findViewById(R.id.edit_username);
        editTextPassword = view.findViewById(R.id.edit_password);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (LoginDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
            "Must implement ExampleDialogListener");
        }
    }

    public interface LoginDialogListener{
        void onResult(String username, String password);
    }
}
