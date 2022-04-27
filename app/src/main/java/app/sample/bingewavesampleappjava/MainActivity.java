package app.sample.bingewavesampleappjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;

import app.sample.bingewavesampleappjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "[replace_with_widget_webview_url]";
    private static final String AUTH_TOKEN = "[replace_width_user_auth_token]";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WebView webView = binding.webView;

        checkPermissions();

        HashMap<String, String> mapHeader = new HashMap<String, String>();
        mapHeader.put("Authorization","Bearer "+AUTH_TOKEN);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(URL, mapHeader);



    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED || (ContextCompat.checkSelfPermission(
                MainActivity.this,
        Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED) ){
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }


    }

    @Override
    public void onBackPressed() {
        if(binding.webView.canGoBack()){
            binding.webView.goBack();
        }else{
            super.onBackPressed();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if ((grantResults.length != 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Log.i(MainActivity.class.getSimpleName(),"Permission Granted");
            }else{
                Toast.makeText(
                        MainActivity.this,
                "Permission is required to enable feature",
                        Toast.LENGTH_LONG
                    ).show();
            }
        }
    }
}