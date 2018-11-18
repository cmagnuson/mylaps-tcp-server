package com.mtecresults.mylapstcpserver.domain;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PassingTest {

    @Test
    public void parseTimes() {
        String[] proto = "10KM@Passing@t=13:11:30.904|c=0000041|ct=UH|d=120606|l=13|dv=4|re=0|an=00001111|g=0|b=41|n=41@t=13:12:21.830|c=0000039|ct=UH|d=120606|l=30|dv=4|re=0|an=00001101|g=0|b=39|n=39@ 1016@$".split("@");
        List<Passing> passings = Passing.parseTimes("10KM", proto);

        assertEquals(2, passings.size());
        Passing p1 = passings.get(0);
        assertEquals("0000041", p1.getChipcode());
        assertEquals("10KM", p1.getLocationName());
        assertEquals("13:11:30.904", new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(p1.getTimeMillis())));
    }
}