package in.potatohead.customcamerapreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureActivity extends AppCompatActivity {

    ImageView imgPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imgPreview = findViewById(R.id.img_preview);
        imgPreview.setImageBitmap(MainActivity.bitmap);
    }
}
