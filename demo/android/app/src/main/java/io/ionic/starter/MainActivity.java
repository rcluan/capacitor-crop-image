package io.ionic.starter;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;

import image.crop.pinn.pinnpet.ImageCropPlugin;

public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPlugin(ImageCropPlugin.class);
    }
}
