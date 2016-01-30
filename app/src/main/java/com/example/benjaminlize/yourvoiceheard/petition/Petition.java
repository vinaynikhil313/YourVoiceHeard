package com.example.benjaminlize.yourvoiceheard.petition;

import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.SignUnsignPetition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 17-01-2016.
 * POJO for retrieving the petition from Firebase data.
 */
public class Petition implements Serializable{

    private String mCategory;
    private String mUniqueId;
    private String mTitle;
    private String mLongDescription;
    private String mShortDescription;
    private String mImageUrl;
    private String mVideoUrl;
    private int mSigns;
    private int mUnsigns;
    private Map<String, Integer> mUsers = new HashMap<String, Integer> ();

    public String getmCategory () {
        return mCategory;
    }

    public void setmCategory (String mCategory) {
        this.mCategory = mCategory;
    }

    public int getmSigns () {
        return mSigns;
    }

    public void setmSigns (int mSigns) {
        this.mSigns = mSigns;
    }

    public int getmUnsigns () {
        return mUnsigns;
    }

    public void setmUnsigns (int mUnsigns) {
        this.mUnsigns = mUnsigns;
    }

    public Map<String, Integer> getmUsers () {
        return mUsers;
    }

    public void setmUsers (Map<String, Integer> mUsers) {
        this.mUsers = mUsers;
    }

    public Petition (String mUniqueId, String mTitle, String mShortDescription, String mLongDescription, String mImageUrl, String mVideoUrl) {
        this.mUniqueId = mUniqueId;
        this.mTitle = mTitle;
        this.mShortDescription = mShortDescription;
        this.mLongDescription = mLongDescription;
        this.mImageUrl = mImageUrl;
        this.mVideoUrl = mVideoUrl;
    }

    public Petition(){}

    public String getmUniqueId () {
        return mUniqueId;
    }

    public void setmUniqueId (String mUniqueId) {
        this.mUniqueId = mUniqueId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLongDescription() {
        return mLongDescription;
    }

    public void setmLongDescription(String mLongDescription) {
        this.mLongDescription = mLongDescription;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public void newSignUnsign(int flag){

        if(flag == Constants.SIGN){
            mSigns++;
        }
        else{
            mUnsigns++;
        }

    }

    public void changeOfMind(int flag){
        if(flag == Constants.SIGN){
            mSigns++;
            mUnsigns--;
        }
        else{
            mUnsigns++;
            mSigns--;
        }
    }
}
