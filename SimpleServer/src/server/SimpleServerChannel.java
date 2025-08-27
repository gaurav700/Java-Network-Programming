package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleServerChannel {
    public static void main(String[] args){

        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));

            System.out.println("Server is listening on port "+serverSocketChannel.socket().getLocalPort());

            while(true){
                SocketChannel clientChannel = serverSocketChannel.accept();
                System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());

                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                SocketChannel channel = clientChannel;

                int readBytes = channel.read(byteBuffer);
                if(readBytes>0){
                    byteBuffer.flip();
                    channel.write(ByteBuffer.wrap("Echo from server: ".getBytes()));
                    while(byteBuffer.hasRemaining()){
                        channel.write(byteBuffer);
                    }
                    byteBuffer.clear();
                }
                else if(readBytes == -1){
                    System.out.printf("Connection to %s lost%n", channel.socket().getRemoteSocketAddress());
                    channel.close();
                }
            }

        }catch (IOException e){
            System.out.println("Server exception "+e.getMessage());
        }

    }
}
