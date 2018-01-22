package emotionapi.emotionapi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ScanActivity  extends AppCompatActivity implements ZBarScannerView.ResultHandler {
private ZBarScannerView mScannerView;

//camera permission is needed.

@Override
public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        }

@Override
public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        }

@Override
public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
        }

@Override
public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        // Do something with the result here
        Log.v("kkkk", result.getContents()); // Prints scan results
        Log.v("uuuu", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        //TestActivity.result.setText(result.getContents());
        //val intent = new Intent(this, PlayersActivity.class).apply {
                //putExtra(EXTRA_MESSAGE, message)
        //}
        Intent intent = new Intent(this, PlayersActivity.class);
        intent.putExtra("qrCode", result.getContents());
        startActivity(intent);
        onBackPressed();

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        }
        }