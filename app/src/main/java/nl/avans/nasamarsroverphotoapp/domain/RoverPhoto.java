package nl.avans.nasamarsroverphotoapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class RoverPhoto implements Parcelable {
    private String mImageId;
    private String mImageUrl;
    private String mRoverName;
    private String mEarthDate;
    private String mFullCameraName;
    private String mAmountOfPhotosThisRover;
    private String mSolDay;

    public RoverPhoto(String imageId, String imageUrl, String roverName, String earthDate, String fullCameraName, String amountOfPhotos, String solDay) {
        mImageId = imageId;
        mImageUrl = imageUrl;
        mRoverName = roverName;
        mEarthDate = earthDate;
        mFullCameraName = fullCameraName;
        mAmountOfPhotosThisRover = amountOfPhotos;
        mSolDay = solDay;
    }

    protected RoverPhoto(Parcel in) {
        mImageId = in.readString();
        mImageUrl = in.readString();
        mRoverName = in.readString();
        mEarthDate = in.readString();
        mFullCameraName = in.readString();
        mAmountOfPhotosThisRover = in.readString();
        mSolDay = in.readString();
    }

    public static final Creator<RoverPhoto> CREATOR = new Creator<RoverPhoto>() {
        @Override
        public RoverPhoto createFromParcel(Parcel in) {
            return new RoverPhoto(in);
        }

        @Override
        public RoverPhoto[] newArray(int size) {
            return new RoverPhoto[size];
        }
    };

    public String getImageId() {
        return mImageId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getRoverName() {
        return mRoverName;
    }

    public String getEarthDate() {
        return mEarthDate;
    }

    public String getFullCameraName() {
        return mFullCameraName;
    }

    public String getAmountOfPhotos() {
        return mAmountOfPhotosThisRover;
    }

    public String getSolDay() {
        return mSolDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageId);
        dest.writeString(mImageUrl);
        dest.writeString(mRoverName);
        dest.writeString(mEarthDate);
        dest.writeString(mFullCameraName);
        dest.writeString(mAmountOfPhotosThisRover);
        dest.writeString(mSolDay);
    }
}

