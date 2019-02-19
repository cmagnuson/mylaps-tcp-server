package com.mtecresults.mylapstcpserver.controller;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Set;

public class MyLapsTCPServer {

    private static final Logger LOG = LoggerFactory.getLogger(MyLapsTCPServer.class);

    final NioSocketAcceptor acceptor;
    final ServerDataHandler handler;
    final TCPMinaHandler minaHandler;
    private final SessionTrackingListener sessionTrackingListener = new SessionTrackingListener();

    public MyLapsTCPServer(ServerDataHandler handler) throws IOException {
        this.handler = handler;
        minaHandler = new TCPMinaHandler(handler);
        LOG.info("Server startup for server: "+handler.getServerName()+" port: "+handler.getServerPort());

        acceptor = new NioSocketAcceptor();
        acceptor.setReuseAddress(true);
        //acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory( Charset.forName( "UTF-8" ));
        textLineCodecFactory.setDecoderMaxLineLength(64_000);
        textLineCodecFactory.setEncoderMaxLineLength(64_000);
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(textLineCodecFactory));
        acceptor.addListener(sessionTrackingListener);
        acceptor.setHandler(minaHandler);
        acceptor.bind( new InetSocketAddress(handler.getServerPort()) );
    }

    public void stopServer() {
        LOG.info("Server shutdown for server: "+handler.getServerName()+" port: "+handler.getServerPort());
        acceptor.dispose();
    }
    
    public ServerDataHandler getHandler() {
        return handler;
    }

    public Set<IoSession> getActiveSessions() {
        return sessionTrackingListener.getActiveSessions();
    }
}
