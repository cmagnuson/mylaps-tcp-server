package com.mtecresults.mylapstcpserver.controller;

import com.mtecresults.mylapstcpserver.domain.Marker;
import com.mtecresults.mylapstcpserver.domain.Passing;

import java.util.Collection;

public abstract class ServerDataHandler {

    public void handlePassings(Collection<Passing> passings){}

    public void handleMarkers(Collection<Marker> markers){}

    //return true to accept credentials, false to reject
    public boolean handleLogin(String username, String password){
        return true;
    }

    public abstract String getServerName();

    public abstract int getServerPort();
}
