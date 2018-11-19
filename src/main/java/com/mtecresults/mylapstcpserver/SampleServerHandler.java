package com.mtecresults.mylapstcpserver;

import com.mtecresults.mylapstcpserver.controller.MyLapsTCPServer;
import com.mtecresults.mylapstcpserver.controller.ServerDataHandler;
import com.mtecresults.mylapstcpserver.domain.Marker;
import com.mtecresults.mylapstcpserver.domain.Passing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class SampleServerHandler extends ServerDataHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SampleServerHandler.class);

    public static void main(String args[]) throws Exception {
        LOG.info("Sample server startup");
        new MyLapsTCPServer(new SampleServerHandler());
    }

    @Override
    public void handlePassings(Collection<Passing> passings) {
        LOG.info("Passings message received");
        for(Passing passing: passings){
            LOG.info("\t"+passing);
        }
    }

    @Override
    public void handleMarkers(Collection<Marker> markers) {
        LOG.info("Markers message received");
        for(Marker marker: markers){
            LOG.info("\t"+marker);
        }
    }

    @Override
    public String getServerName() {
        return "SampleServer";
    }

    @Override
    public int getServerPort() {
        return 3097;
    }

    @Override
    public boolean handleLogin(String username, String password) {
        LOG.info("Authorization message received: "+username+"/"+password);
        return true;
    }
}
