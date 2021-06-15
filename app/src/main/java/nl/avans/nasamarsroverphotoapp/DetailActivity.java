package nl.avans.nasamarsroverphotoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import nl.avans.nasamarsroverphotoapp.domain.RoverPhoto;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = DetailActivity.class.getSimpleName();

    private RoverPhoto mRoverPhoto;
    ImageView roverPicture;
    TextView cameraName;
    TextView roverName;
    TextView solDay;
    TextView earthDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate was called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if(intent.hasExtra("roverPhoto")) {
            mRoverPhoto = (RoverPhoto) intent.getParcelableExtra("roverPhoto");
        }

        cameraName = findViewById(R.id.tv_camera_name);
        roverPicture = findViewById(R.id.iv_rover_picture);
        roverName = findViewById(R.id.tv_rover_name_land);
        solDay = findViewById(R.id.tv_sol_day_land);
        earthDay = findViewById(R.id.tv_earth_date_land);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //In landscape mode
            roverName.setText(R.string.rover_name);
            roverName.append(mRoverPhoto.getRoverName());
            solDay.setText(R.string.sol_day);
            solDay.append(mRoverPhoto.getSolDay());
            earthDay.setText(R.string.earth_day);
            earthDay.append(mRoverPhoto.getEarthDate());
        }

        //In landscape and portrait mode
        cameraName.setText(mRoverPhoto.getFullCameraName());
        String imageUrl = mRoverPhoto.getImageUrl();
        String mNewImageUrl = NasaRoverAdapter.changeImageUrl(imageUrl);
        Picasso.get().load(mNewImageUrl).into(roverPicture);
    }
}