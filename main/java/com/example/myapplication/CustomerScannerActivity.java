package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomerScannerActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_customer_scanner);

        barcodeView = findViewById(R.id.zxing_barcode_scanner);

        // Enforce portrait mode for the camera preview
        barcodeView.resume();
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Intent intent = new Intent();
                intent.putExtra("SCAN_RESULT", result.getText());
                setResult(RESULT_OK, intent);
                finish(); // Close activity after scan
            }

            @Override
            public void possibleResultPoints(java.util.List<com.google.zxing.ResultPoint> resultPoints) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    // Override to ensure camera respects portrait mode
    @Override
    public void onBackPressed() {
        barcodeView.pause();
        super.onBackPressed();
    }
}
