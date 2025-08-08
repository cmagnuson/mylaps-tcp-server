package com.mtecresults.mylapstcpserver.domain;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MarkerTest {

    @Test
    public void parseMarkers() {
        String[] protocolLine = "Finish@Marker@t=2017-10-25 11:03:40.347|mt=gun|n=Gunshot 1@4@$\n".split("@");
        List<Marker> markers = Marker.parseMarkers("Finish", protocolLine);
        assertEquals(1, markers.size());

        Marker m = markers.get(0);
        assertTrue(m.type().isGunshot);
        assertEquals(Marker.MarkerType.GUNSHOT, m.type());
        assertEquals("Finish", m.locationName());
        assertEquals("Gunshot 1", m.label());
        assertEquals("11:03:40.347", new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(m.timeMillis())));
    }
}