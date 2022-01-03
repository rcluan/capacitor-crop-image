package image.crop.pinn.pinnpet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.AndroidProtocolHandler;
import com.getcapacitor.FileUtils;

import com.getcapacitor.annotation.ActivityCallback;
import com.yalantis.ucrop.UCrop;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(
    name = "ImageCrop"
)
public class ImageCropPlugin extends Plugin {

    @PluginMethod
    public void show(PluginCall call) {

        String source = call.getString("source");
        int width = call.getInt("width", 300);
        int height = call.getInt("height", 300);
        boolean lock = call.getBoolean("lock", false);

        int aspectRatioX = 1;
        int aspectRatioY = 1;

        JSObject aspectRatio = call.getObject("aspectRatio");

        try {
            if(aspectRatio != null) {
                aspectRatioX = ((int) aspectRatio.get("x"));
                aspectRatioY = ((int) aspectRatio.get("y"));
            }
        } catch (JSONException e) {
            Log.v(getLogTag(), "Error: " + e.getMessage());
            call.reject(e.getLocalizedMessage());
        }

        try {  
            File dest = new File(getActivity().getCacheDir().getAbsolutePath() + "/CAP_CROP.jpg");

            boolean isAppPath = false;

            if (source.contains("~")) {
                isAppPath = true;
                source = source.replace("~", "");
            }

            AndroidProtocolHandler protocolHandler = new AndroidProtocolHandler(getActivity().getApplicationContext());

            File tempSource;
            
            if (isAppPath) {
                File f = new File("file:///android_asset/public" + source);
                InputStream is = protocolHandler.openAsset("public" + source);
                tempSource = new File(getActivity().getCacheDir().getAbsolutePath() + f.getName());
                FileOutputStream os = new FileOutputStream(tempSource);
                IOUtils.copy(is, os);
                os.close();
            } else {
                if (source.startsWith("file:")) {
                    Uri uri = Uri.parse(source);
                    tempSource = new File(uri.getPath());
                } else {
                    tempSource = new File(source);
                }
            }

            UCrop crop = UCrop.of( Uri.fromFile(tempSource), Uri.fromFile(dest) );

            if(lock) {
                crop = crop.useSourceImageAspectRatio();
            }

            crop.withMaxResultSize(width, height)
            .withAspectRatio(aspectRatioX, aspectRatioY);

            Intent intent = crop.getIntent(getContext());
            
            startActivityForResult(call, intent, "handleCrop");

        } catch (IOException e) {
            Log.v(getLogTag(), "Error: " + e.getMessage());
            call.reject(e.getLocalizedMessage());
        }
    }

    @ActivityCallback
    private void handleCrop(PluginCall call, ActivityResult result) {
        
        int resultCode = result.getResultCode();
        Intent data = result.getData();
        Log.v(getLogTag(), "handle crop");
        if (resultCode == Activity.RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            JSObject object = new JSObject();
            object.put("value", FileUtils.getPortablePath(getContext(), bridge.getLocalUrl(), resultUri));
            call.resolve(object);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            call.reject(cropError.getLocalizedMessage());
        }
    }
}
