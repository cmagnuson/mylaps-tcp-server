package com.mtecresults.mylapstcpserver.controller;

import com.mtecresults.mylapstcpserver.domain.Marker;
import com.mtecresults.mylapstcpserver.domain.Passing;
import org.apache.mina.core.session.DummySession;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class TCPMinaHandlerTest extends ServerDataHandler {

    Collection<Marker> markersReceived;
    Collection<Passing> passingsReceived;

    TCPMinaHandler handler;

    @Before
    public void setUp() throws Exception {
        markersReceived = null;
        passingsReceived = null;
        handler = new TCPMinaHandler(this);
    }

    @Test
    public void messageReceived() throws Exception {
        handler.messageReceived(new DummySession(), "Finish@Marker@t=2017-10-25 11:03:40.347|mt=gun|n=Gunshot 1@4@$\n");
        handler.messageReceived(new DummySession(), "10KM@Passing@t=13:11:30.904|c=0000041|ct=UH|d=120606|l=13|dv=4|re=0|an=00001111|g=0|b=41|n=41@t=13:12:21.830|c=0000039|ct=UH|d=120606|l=30|dv=4|re=0|an=00001101|g=0|b=39|n=39@ 1016@$");
        assertEquals(2, passingsReceived.size());
        assertEquals(1, markersReceived.size());
    }

    @Override
    public void handlePassings(Collection<Passing> passings) {
        passingsReceived = passings;
    }

    @Override
    public void handleMarkers(Collection<Marker> markers) {
        markersReceived = markers;
    }

    @Override
    public String getServerName() {
        return "A";
    }

    @Override
    public int getServerPort() {
        return 0;
    }
}