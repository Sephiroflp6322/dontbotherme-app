package kelvin.link.dontborderme_app;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;

public class QrCodeDialog extends AppCompatDialogFragment {
    private final static String logMessage = "QrCodeDialog";

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 520;
    public final static int HEIGHT = 520;

    private String message = new String();
    Bitmap bitmap = null;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.qr_dialog, null);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            message = bundle.getString("msg", "None");
        }

        ImageView imageViewQR = (ImageView)view.findViewById(R.id.qr_code);

        try {
            bitmap = encodeAsBitmap(message);
            imageViewQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        imageViewQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do nothing
            }
        });


        builder.setView(view)
                .setTitle("QR Code")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //Do nothing
                    }
                })
                .setNegativeButton("EXPORT TO DEVICE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Storing QR Code to device
                        String[] splitStr = message.trim().split("\\s+");
                        String uid = splitStr[0];
                        Integer event_id = Integer.parseInt(splitStr[1]);
                        saveImage(bitmap, uid+event_id);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "QR Code Exported", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog qrDialog = builder.create();
        qrDialog.show();

        final Button positiveButton = qrDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = qrDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        LinearLayout.LayoutParams negativeButtonLL = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        negativeButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        return qrDialog;
    }


    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()){
            file.delete();
        }
        Log.i(logMessage, "Storing Image to --> " + root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
