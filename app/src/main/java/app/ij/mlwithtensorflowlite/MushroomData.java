package app.ij.mlwithtensorflowlite;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MushroomData implements Parcelable {

    private byte[] uploadedImage;

    private String name;

    private String otherName;
    private String description;
    private String height;
    private String diameter;
    private String colours;
    private String habit;
    private String nearbyTrees;
    private String sporePrint;
    private String poisonous;

    public MushroomData(String name, String otherName,String description, String height, String diameter, String colours, String habit, String nearbyTrees, String sporePrint, String poisonous) {
        this.name = name;
        this.otherName = otherName;
        this.description = description;
        this.height = height;
        this.diameter = diameter;
        this.colours = colours;
        this.habit = habit;
        this.nearbyTrees = nearbyTrees;
        this.sporePrint = sporePrint;
        this.poisonous = poisonous;
    }

    protected MushroomData(Parcel in) {
        name = in.readString();
        otherName = in.readString();
        description = in.readString();
        height = in.readString();
        diameter = in.readString();
        colours = in.readString();
        habit = in.readString();
        nearbyTrees = in.readString();
        sporePrint = in.readString();
        poisonous = in.readString();
    }

    public byte[] getUploadedImage() {
        return uploadedImage;
    }

    public void setUploadedImage(byte[] uploadedImage) {
        this.uploadedImage = uploadedImage;
    }

    public static final Creator<MushroomData> CREATOR = new Creator<MushroomData>() {
        @Override
        public MushroomData createFromParcel(Parcel in) {
            return new MushroomData(in);
        }

        @Override
        public MushroomData[] newArray(int size) {
            return new MushroomData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getColours() {
        return colours;
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getNearbyTrees() {
        return nearbyTrees;
    }

    public void setNearbyTrees(String nearbyTrees) {
        this.nearbyTrees = nearbyTrees;
    }

    public String getSporePrint() {
        return sporePrint;
    }

    public void setSporePrint(String sporePrint) {
        this.sporePrint = sporePrint;
    }

    public String getPoisonous() {
        return poisonous;
    }

    public void setPoisonous(String poisonous) {
        this.poisonous = poisonous;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.otherName);
        dest.writeString(this.description);
        dest.writeString(this.height);
        dest.writeString(this.diameter);
        dest.writeString(this.colours);
        dest.writeString(this.habit);
        dest.writeString(this.nearbyTrees);
        dest.writeString(this.sporePrint);
        dest.writeString(this.poisonous);
    }
}
