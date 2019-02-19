package com.mtecresults.mylapstcpserver.controller;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.HashSet;
import java.util.Set;

public class SessionTrackingListener implements IoServiceListener {

    private final Set<IoSession> activeSessions = new HashSet<>();

    @Override
    public void serviceActivated(IoService service) throws Exception {

    }

    @Override
    public void serviceIdle(IoService service, IdleStatus idleStatus) throws Exception {

    }

    @Override
    public void serviceDeactivated(IoService service) throws Exception {

    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        synchronized(activeSessions){
            activeSessions.add(session);
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        synchronized (activeSessions){
            activeSessions.remove(session);
        }
    }

    @Override
    public void sessionDestroyed(IoSession session) throws Exception {

    }

    public Set<IoSession> getActiveSessions(){
        synchronized (activeSessions){
            return new HashSet<IoSession>(activeSessions);
        }
    }
}
