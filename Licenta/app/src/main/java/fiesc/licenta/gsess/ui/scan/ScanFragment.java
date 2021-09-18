package fiesc.licenta.gsess.ui.scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import fiesc.licenta.gsess.R;
import pl.droidsonroids.gif.GifImageView;

import static android.content.ContentValues.TAG;


public class ScanFragment extends Fragment {
    public GifImageView scan;
    private ScanViewModel scanViewModel;
    ElegantNumberButton elegantNumberButton;
    private TextView txtProdus,txtPret;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scanViewModel = ViewModelProviders.of(this).get(ScanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        txtProdus=(TextView)root.findViewById(R.id.txtProdName) ;
        txtPret=(TextView)root.findViewById(R.id.txtPret) ;
        elegantNumberButton = (ElegantNumberButton) root.findViewById(R.id.elegBtn);
        scan = root.findViewById(R.id.btnScan);
        final TextView textView = root.findViewById(R.id.txtCurrentStore);
        scanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                scan.setImageResource(R.drawable.scanner);
            }
        });
        elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = elegantNumberButton.getNumber();
            }
        });
        elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
        return root;
    }
    private void scanCode() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScanFragment.this);
//                new IntentIntegrator(getActivity());

        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.setBarcodeImageEnabled(false);
        txtProdus.setText(getActivity().toString());
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() != null) {
                txtProdus.setText(result.getContents());
            } else {
                txtProdus.setText("nu");
                Toast.makeText(getContext(), "This product isn't from our Store", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Nothing", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode,resultCode,data);
        }

    }


}