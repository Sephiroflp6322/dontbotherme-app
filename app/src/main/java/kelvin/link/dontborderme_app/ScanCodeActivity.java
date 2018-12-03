package kelvin.link.dontborderme_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.converter.gson.GsonConverterFactory.create;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private String logMessage = "ScanCodeActivity";
    ZXingScannerView ScannerView;
    private DontBorderMeWebServiceAPI webServiceAPI;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);


        //Setting up user info
        user = UserManager.getInstance().getUser();

        //Setting up retrofit connection
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://59.149.35.12/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(DontBorderMeWebServiceAPI.class);



    }

    @Override
    public void handleResult(Result result) {
        //TODO Handling qr code scan result here
        String text = result.getText();
        String[] splitStr = text.trim().split("\\s+");
        String uid = UserManager.getInstance().getUser().getUid();
        Integer event_id = Integer.parseInt(splitStr[1]);
        subscribeEvent(uid, event_id);
        Log.i(logMessage, "uid: " + uid + "    event_id: " + String.valueOf(event_id));
        Toast.makeText(this, "Subscribe to event "+ String.valueOf(event_id), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


    @Override
    protected void onPause(){
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }


    //Arguments required: <uid>, <event_id>
    private void subscribeEvent(String uid, Integer event_id){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", uid);
        parameters.put("event_id", String.valueOf(event_id));
        Call<Void> call = webServiceAPI.subscribe_event(parameters);

        //Async call
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "_subscribeEvent(); Response code:" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t){
                Log.i(logMessage, "_subscribeEvent(); Fialure message: " + t.getMessage());
                //response.errorBody().string()
            }
        });
    }
}
