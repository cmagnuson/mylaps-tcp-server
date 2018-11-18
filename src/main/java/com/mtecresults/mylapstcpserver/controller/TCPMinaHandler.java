package com.mtecresults.mylapstcpserver.controller;

import com.mtecresults.mylapstcpserver.domain.Marker;
import com.mtecresults.mylapstcpserver.domain.Passing;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TCPMinaHandler extends IoHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TCPMinaHandler.class);
    private static final String NEWLINE = "\r\n";

    private final ServerDataHandler handler;

    public TCPMinaHandler(ServerDataHandler handler){
        super();
        this.handler = handler;
        LOG.debug("New TCPMinaHandler starting up");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause ) throws Exception {
        LOG.warn("Exception caught in session: "+session, cause);
    }

    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception {
        String line = message.toString();
        LOG.trace("Message received: "+line);

        String[] parts = line.split("@");
        String location = parts[0].trim();

        if(parts.length>1 && parts[1].equals("Pong")) {
            LOG.debug("Acknowledge pong");
            session.write(handler.getServerName() + "@AckPong@Version2.1@" + NEWLINE);
        }
        else if(parts.length>3 && parts[1].equals("AckAuth")){
            LOG.debug("Acknowledge auth");
            //For now don't do any auth, just allow any user/password sent
            //String username = parts[2];
            //String password = parts[3];
            session.write(handler.getServerName()+"@AuthOk@"+NEWLINE);
        }
        else if(parts.length>1 && parts[1].equals("Passing")){
            LOG.debug("V2 passings received");
            //we don't handle V1 passings "Store", just let them
            //fail/resend until protocol is upgraded to V2
            List<Passing> passings = Passing.parseTimes(location, parts);
            handler.handlePassings(passings);
            session.write(handler.getServerName()+"@AckPassing@"+parts[parts.length-2]+"@"+NEWLINE);
        }
        else if(parts.length>1 && parts[1].equals("Marker")){
            if(parts.length > 2 && !parts[2].contains("=")){
                //V1 marker record, don't acknowledge
                //wait for protocol upgrade and resend in V2
                LOG.debug("V1 markers received, ignoring until V2 upgrade");
                return;
            }

            LOG.debug("V2 markers received");
            List<Marker> markers = Marker.parseMarkers(location, parts);
            handler.handleMarkers(markers);
            session.write(handler.getServerName()+"@AckMarker@"+parts[parts.length-2]+"@"+NEWLINE);
        }
    }
}
