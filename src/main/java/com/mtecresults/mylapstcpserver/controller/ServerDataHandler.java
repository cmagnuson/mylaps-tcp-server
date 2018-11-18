package com.mtecresults.mylapstcpserver.controller;

import com.mtecresults.mylapstcpserver.domain.Marker;
import com.mtecresults.mylapstcpserver.domain.Passing;

import java.util.Collection;

public interface ServerDataHandler {

    default void handlePassings(Collection<Passing> passings){}

    default void handleMarkers(Collection<Marker> markers){}

    String getServerName();

    int getServerPort();
}
