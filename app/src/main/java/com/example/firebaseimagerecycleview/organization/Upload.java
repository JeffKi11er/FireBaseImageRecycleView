package com.example.firebaseimagerecycleview.organization;

public class Upload {
    private String mName ;
    private String ImageUrl;

    public Upload() {

    }

    public Upload(String mName, String imageUrl) {
        if (mName.trim().equals("")){
            mName = "No name";
        }
        this.mName = mName;
        ImageUrl = imageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
