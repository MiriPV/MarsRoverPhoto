package nl.avans.nasamarsroverphotoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import nl.avans.nasamarsroverphotoapp.domain.RoverPhoto;
import nl.avans.nasamarsroverphotoapp.utils.NetworkUtils;


public class MainActivity extends AppCompatActivity implements RoverPhotoTask.RoverPhotoTaskListener{
    public static final String TAG = MainActivity.class.getSimpleName();
    private NasaRoverAdapter mAdapter;
    private ArrayList<RoverPhoto> mSavedRoverPhotos;
    private RoverPhotoTask mRoverPhotoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate was called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSavedRoverPhotos = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rv_nasa_rover);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new NasaRoverAdapter(this, new ArrayList<RoverPhoto>());

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null) {
            mSavedRoverPhotos = savedInstanceState.getParcelableArrayList("SAVED_ARRAY_LIST");
            mAdapter.setResults(mSavedRoverPhotos);
        } else {
            startExecute("curiosity");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rover_selection, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.curiosity:
                startExecute("curiosity");
                return true;
            case R.id.opportunity:
                startExecute("opportunity");
                return true;
            case R.id.spirit:
                startExecute("spirit");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState was called");
        outState.putParcelableArrayList("SAVED_ARRAY_LIST", mSavedRoverPhotos);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void processResult(List<RoverPhoto> roverPhotos) {
        Log.i(TAG, "processResult was called");
        mSavedRoverPhotos.clear();
        mSavedRoverPhotos.addAll(roverPhotos);
        mAdapter.setResults(roverPhotos);

        String roverName = roverPhotos.get(0).getRoverName();
        String totalPhotos = roverPhotos.get(0).getAmountOfPhotos();
        Toast.makeText(this, mAdapter.getItemCount() + getString(R.string.photos_loaded) +
                roverName + getString(R.string.has_made) + totalPhotos + getString(R.string.total_photos), Toast.LENGTH_LONG).show();
    }

    public void startExecute(String rover) {
        Log.i(TAG, "startExecute was called for this rover: " + rover);
        String UrlString = NetworkUtils.buildUrl(rover);
        String UrlStringManifest = NetworkUtils.buildManifestUrl(rover);
        // A new instance of RoverPhotoTask is created, because a task can only be executed once.
        mRoverPhotoTask = new RoverPhotoTask( this);
        mRoverPhotoTask.execute(UrlString, UrlStringManifest);
    }
}