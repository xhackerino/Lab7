package server;

import commands.base.CommandResponseContainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Base64;

public final class RequestWriter implements Runnable {

    private final CommandResponseContainer commandResponseContainer;
    private final DatagramChannel datagramChannel;
    private final SocketAddress from;

    public RequestWriter(CommandResponseContainer commandResponseContainer, DatagramChannel datagramChannel, SocketAddress from) {
        this.commandResponseContainer = commandResponseContainer;
        this.datagramChannel = datagramChannel;
        this.from = from;
    }
    @Override
    public void run() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
            objectOutputStream.writeObject(commandResponseContainer);
            datagramChannel.send(ByteBuffer.wrap(Base64.getMimeEncoder().encode(baos.toByteArray())), from);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
