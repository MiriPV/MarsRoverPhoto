package nl.avans.nasamarsroverphotoapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.avans.nasamarsroverphotoapp.domain.RoverPhoto;
import nl.avans.nasamarsroverphotoapp.utils.NetworkUtils;

public class RoverPhotoTask extends AsyncTask<String, Void, List<RoverPhoto>> {
    private static final String TAG = RoverPhotoTask.class.getSimpleName();
    private RoverPhotoTaskListener roverPhotoTaskListener;

    public RoverPhotoTask(RoverPhotoTaskListener roverPhotoTaskListener) {
        this.roverPhotoTaskListener = roverPhotoTaskListener;
    }

    @Override
    protected List<RoverPhoto> doInBackground(String... strings) {
        Log.i(TAG, "doInBackground was called with these urls: " + Arrays.toString(strings));
        List<RoverPhoto> roverPhotos = new ArrayList<>();
        String response = "";
        String manifestResponse = "";

        String url = strings[0];
        String manifestUrl = strings[1];
        response = NetworkUtils.getResponseFromHttpUrl(url);
        manifestResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);

        try {
            if (response != null && manifestResponse != null) {
                JSONObject photosObject = new JSONObject(response);
                JSONArray photosArray = photosObject.getJSONArray("photos");

                JSONObject photoManifestObject = new JSONObject(manifestResponse);
                JSONObject photoManifest = photoManifestObject.getJSONObject("photo_manifest");

                for (int i = 0; i < photosArray.length(); i++) {
                    JSONObject photo = photosArray.getJSONObject(i);
                    JSONObject camera = photo.getJSONObject("camera");
                    JSONObject rover = photo.getJSONObject("rover");
                    RoverPhoto roverPhoto = new RoverPhoto(
                            photo.getString("id"),
                            photo.getString("img_src"),
                            rover.getString("name"),
                            photo.getString("earth_date"),
                            camera.getString("full_name"),
                            photoManifest.getString("total_photos"),
                            photo.getString("sol"));
                    roverPhotos.add(roverPhoto);
                }
            } else {
                Log.e(TAG, "Response is null");
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.getMessage());
        }
        return roverPhotos;
    }


    @Override
    protected void onPostExecute(List<RoverPhoto> roverPhotos) {
        Log.i(TAG, "onPostExecute was called");
        roverPhotoTaskListener.processResult(roverPhotos);
    }


    public interface RoverPhotoTaskListener {
        void processResult(List<RoverPhoto> roverPhotos);
    }
}

