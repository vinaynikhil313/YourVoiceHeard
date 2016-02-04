package com.example.benjaminlize.yourvoiceheard.utils;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin.lize on 19/12/2015.
 * Not used
 */
public class Utilities {


    public static final String sDEVELOPER_KEY = "AIzaSyBHNrr2675jCzB03JCPO7aZSrISIU1eRRg";

    static Firebase firebase = new Firebase("https://yourvoiceheard.firebaseio.com");
    //http://youtu.be/<VIDEO_ID>
    public static final String sYOUTUBE_VIDEO_CODE = "M4zCOHFrLVY";
        //Dummy Data 1
    static String uniqueId1 = "P1";
    static String title1 = "Entrepreneurship thought at school";
    static String descriptionShort1 = "Place for Self Employed people is widely increasing in the UK. The government is highly encouraging this policy. Let education follow up wiwth our modern world";
    static String descriptionLong1 = "Place for Self Employed people is widely increasing in the UK. The government is highly encouraging this policy. Let education follow up wiwth our modern world";
    static String urlImage1 = "xIg4qRGFKKQ";
    static String urlVideo1 = "xIg4qRGFKKQ";

    //Dummy Data 2
    static String uniqueId2 = "P2";
    static String title2 = "Lets kick out this Ducky Trump";
    static String urlVideo2 = "YJERrD0rO9M";
    static String urlImage2 = "xIg4qRGFKKQ";
    static String descriptionShort2 = "To be honest he is quite racist and dumb";
    static String descriptionLong2 = "To be honest he is quite racist and dumb";

    //Dummy Data 3
    static String uniqueId3 = "P3";
    static String title3 = "Freedom for black people";
    static String descriptionShort3 = "black people work hard in this country and deserve to be treated equally";
    static String descriptionLong3 = "black people work hard in this country and deserve to be treated equally";
    static String urlImage3 = "xIg4qRGFKKQ";
    static String urlVideo3 = "vDWWy4CMhE";

    public static Petition createPetition1(){
        Petition petition1 = new Petition(uniqueId1, title1, descriptionShort1, descriptionLong1, urlImage1, urlVideo1);
        firebase.child (uniqueId1).setValue (petition1);
        return petition1;
    }
    public static Petition createPetition2(){
        Petition petition1 = new Petition(uniqueId2, title2, descriptionShort2, descriptionLong2, urlImage2, urlVideo2);
        firebase.child (uniqueId2).setValue (petition1);
        return petition1;
    }
    public static Petition createPetition3(){
        Petition petition1 = new Petition(uniqueId3, title3, descriptionShort3, descriptionLong3, urlImage3, urlVideo3);
        firebase.child (uniqueId3).setValue (petition1);
        return petition1;
    }

    public static void createData(){
        createPetition1 ();
        createPetition2 ();
        createPetition3 ();
    }

    public static String getTag(Object o){
        return o.getClass ().getSimpleName ();
    }

    public static void createCategories(){
        List<String> categories = new ArrayList<> ();
        categories.add ("Women");
        categories.add ("Children");
        categories.add ("Animals");
        categories.add ("Racism");
        categories.add ("Poverty");
        categories.add ("Corruption");
        firebase.child ("categories").setValue (categories);
    }

    public static void createPetition(String mCategory, String mImageUrl, String mLongDescription,
                                      String mShortDescription, String mTitle, String mUniqueId, String mVideoUrl){
        Petition p = new Petition (mCategory, mImageUrl, mLongDescription, mShortDescription, mTitle, mUniqueId, mVideoUrl);

        firebase.child ("petitions").child (mUniqueId).setValue (p);
    }

    public static void deleteEmail(){
        firebase.removeUser ("username@gmail.com", "password", new Firebase.ResultHandler () {
            @Override
            public void onSuccess () {

            }

            @Override
            public void onError (FirebaseError firebaseError) {

            }
        });
        firebase.removeUser ("username1@gmail.com", "password", new Firebase.ResultHandler () {
            @Override
            public void onSuccess () {

            }

            @Override
            public void onError (FirebaseError firebaseError) {

            }
        });
    }

}
