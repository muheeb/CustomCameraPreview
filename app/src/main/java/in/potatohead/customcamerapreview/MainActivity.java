package in.potatohead.customcamerapreview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Camera camera;
    private CameraPreview mPreview;
    public static Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkCameraHardware(this)) {
            camera = getCameraInstance();
        }
        else {
            Toast.makeText(this, "Your device doesn't have a camera", Toast.LENGTH_SHORT).show();
        }

        if (camera != null) {
            mPreview = new CameraPreview(this, camera);
            FrameLayout preview = findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }

        final Camera.PictureCallback mPicture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                startActivity(new Intent(getApplicationContext(), PictureActivity.class));
            }
        };

        final Button captureButton = findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, mPicture);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Camera getCameraInstance() {
        Camera camera = null;

        try {
            camera = Camera.open();
            camera.setDisplayOrientation(0);

            Camera.Parameters params = camera.getParameters();
            List<String> focusModes = params.getSupportedFocusModes();

            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                Log.i( "getCameraInstance: ", "Here");
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                camera.setParameters(params);
            }
        } catch (Exception e) {

        }
        return camera;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private void releaseCamera() {
        if(camera != null) {
            camera.release();
            camera = null;
        }
    }


}
