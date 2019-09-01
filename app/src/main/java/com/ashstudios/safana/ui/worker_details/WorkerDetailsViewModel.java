package com.ashstudios.safana.ui.worker_details;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class WorkerDetailsViewModel extends ViewModel {

    private ArrayList<String> imageUrls;
    private ArrayList<String> workerNames;
    private ArrayList<String> workerRoles;

    public WorkerDetailsViewModel() {
        imageUrls = new ArrayList<>();
        workerNames = new ArrayList<>();
        workerRoles = new ArrayList<>();
        initData();
    }

//    "https://i.imgur.com/[0-9a-zA-Z]*.(jpg|png)

    private void initData() {
        imageUrls.add("https://cdn.cnn.com/cnnnext/dam/assets/140929105949-michael-bass-profile-image-super-169.jpg");
        workerNames.add("Harsh Saglani");
        workerRoles.add("Developer");

        imageUrls.add("https://i.imgur.com/mdgMOnb.jpg");
        workerNames.add("Rohit Suthar");
        workerRoles.add("Developer");

        imageUrls.add("https://i.imgur.com/FFROZFr.jpg");
        workerNames.add("John Doe");
        workerRoles.add("Dev Head");

        imageUrls.add("https://i.imgur.com/rpLiwQv.jpg");
        workerNames.add("Akshay Kumar");
        workerRoles.add("Designer");

        imageUrls.add("https://i.imgur.com/QiGPpDO.png");
        workerNames.add("Carry Minati");
        workerRoles.add("Designer");

        imageUrls.add("https://i.imgur.com/PrvsefK.png");
        workerNames.add("Marques Brownlee");
        workerRoles.add("Video Maker");

        imageUrls.add("https://i.imgur.com/PrvsefK.png");
        workerNames.add("Dave Lee");
        workerRoles.add("Lead Architect");

        imageUrls.add("https://i.imgur.com/4m90i4W.png");
        workerNames.add("Technical Guruji");
        workerRoles.add("Architect");

        imageUrls.add("https://i.imgur.com/7adiQpr.png");
        workerNames.add("Unbox Therapy");
        workerRoles.add("Des Head");

        imageUrls.add("https://i.imgur.com/0mukaGf.png");
        workerNames.add("Jay Shetty");
        workerRoles.add("Des Head");

        imageUrls.add("https://i.imgur.com/Dxla907.png");
        workerNames.add("Tanmay Bhatt");
        workerRoles.add("Comedian");

        imageUrls.add("https://i.imgur.com/bAAc3Hz.png");
        workerNames.add("Zakir Khan");
        workerRoles.add("Comedian");

        imageUrls.add("https://i.imgur.com/wnKtRoZ.png");
        workerNames.add("Abhish Mathiew");
        workerRoles.add("Comedian");

        imageUrls.add("https://i.imgur.com/cubyS2T.png");
        workerNames.add("Ryan Reynolds");
        workerRoles.add("Actor");
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public ArrayList<String> getWorkerNames() {
        return workerNames;
    }

    public void setWorkerNames(ArrayList<String> workerNames) {
        this.workerNames = workerNames;
    }

    public ArrayList<String> getWorkerRoles() {
        return workerRoles;
    }

    public void setWorkerRoles(ArrayList<String> workerRoles) {
        this.workerRoles = workerRoles;
    }

    public void sort(Bundle b) {
        imageUrls.remove(0);
        workerNames.remove(0);
        workerRoles.remove(0);

        imageUrls.remove(1);
        workerNames.remove(1);
        workerRoles.remove(1);


        imageUrls.remove(2);
        workerNames.remove(2);
        workerRoles.remove(2);
    }
}