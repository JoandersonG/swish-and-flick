package com.example.joanderson.swishflick.interfaces;

public interface FragmentComunicator {

    void fragmentChange(String operation, String data);
    Object activityChange(String operation);

}
