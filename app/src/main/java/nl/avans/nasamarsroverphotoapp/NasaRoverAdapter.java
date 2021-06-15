package nl.avans.nasamarsroverphotoapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

import nl.avans.nasamarsroverphotoapp.domain.RoverPhoto;

public class NasaRoverAdapter extends RecyclerView.Adapter<NasaRoverAdapter.ViewHolder>{
    private List<RoverPhoto> mRoverPhotos;
    private Context mContext;
    public static final String TAG = NasaRoverAdapter.class.getSimpleName();

    public NasaRoverAdapter(Context context, List<RoverPhoto> roverPhotos) {
        mContext = context;
        mRoverPhotos = roverPhotos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View NasaRoverPhotoView = inflater.inflate(R.layout.item_roverphoto, parent, false);
        ViewHolder viewHolder = new ViewHolder(NasaRoverPhotoView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RoverPhoto roverPhoto = mRoverPhotos.get(position);
        int orientation = mContext.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //In landscape mode
            TextView roverName = holder.roverName;
            TextView solDay = holder.solDay;
            TextView earthDay = holder.earthDay;
            TextView cameraName = holder.cameraName;

            roverName.setText(R.string.rover_name);
            roverName.append(roverPhoto.getRoverName());
            solDay.setText(R.string.sol_day);
            solDay.append(roverPhoto.getSolDay());
            earthDay.setText(R.string.earth_day);
            earthDay.append(roverPhoto.getEarthDate());
            cameraName.setText(R.string.camera_name);
            cameraName.append(roverPhoto.getFullCameraName());
        }
        //In portrait and landscape mode
        TextView imageId = holder.imageId;
        ImageView roverPhotoImage = holder.roverPhotoImage;

        imageId.setText(R.string.image_id);
        imageId.append(roverPhoto.getImageId());
        String imageUrl = roverPhoto.getImageUrl();
        String newImageUrl = changeImageUrl(imageUrl);
        Picasso.get().load(newImageUrl).into(roverPhotoImage);

        roverPhotoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick was called");
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("roverPhoto", roverPhoto);
                mContext.startActivity(intent);
            }
        });
    }

    public void setResults(List<RoverPhoto> roverPhotos) {
        mRoverPhotos = roverPhotos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRoverPhotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView imageId;
        public ImageView roverPhotoImage;
        public TextView roverName;
        public TextView solDay;
        public TextView earthDay;
        public TextView cameraName;

        public ViewHolder(View itemView) {
            super(itemView);

            imageId = itemView.findViewById(R.id.tv_image_id);
            roverPhotoImage = itemView.findViewById(R.id.iv_rover_photo);
            roverName = itemView.findViewById(R.id.tv_rover_name);
            solDay = itemView.findViewById(R.id.tv_sol_day);
            earthDay = itemView.findViewById(R.id.tv_earth_date);
            cameraName = itemView.findViewById(R.id.tv_full_camera_name);
        }
    }

    public static String changeImageUrl(String imageUrl) {
        //The "http" in the image urls is replaced with "https", because the pictures did not load.
        String[] image = imageUrl.split(":");
        String newImageUrl = "https:" + image[1];
        return newImageUrl;
    }
}
