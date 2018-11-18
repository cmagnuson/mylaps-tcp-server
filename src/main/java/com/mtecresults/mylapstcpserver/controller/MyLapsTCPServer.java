package com.mtecresults.mylapstcpserver.controller;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MyLapsTCPServer {

    private static final Logger LOG = LoggerFactory.getLogger(MyLapsTCPServer.class);

    final IoAcceptor acceptor;
    final ServerDataHandler handler;

    public MyLapsTCPServer(ServerDataHandler handler) throws IOException {
        this.handler = handler;
        LOG.info("Server startup for server: "+handler.getServerName()+" port: "+handler.getServerPort());

        acceptor = new NioSocketAcceptor();
        //acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory( Charset.forName( "UTF-8" ));
        textLineCodecFactory.setDecoderMaxLineLength(64_000);
        textLineCodecFactory.setEncoderMaxLineLength(64_000);
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(textLineCodecFactory));
        acceptor.setHandler(  new TCPMinaHandler(handler));
        acceptor.bind( new InetSocketAddress(handler.getServerPort()) );
    }

    public void stopServer() {
        LOG.info("Server shutdown for server: "+handler.getServerName()+" port: "+handler.getServerPort());
        acceptor.dispose();
    }
}
