package src;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    public DatagramSocket datagramSocket;
    public InetAddress inetAddress;
    public byte[] buffer = new byte[256];

    public Server(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(){
        while (true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                String messageFromClient = new String(datagramPacket.getData(),0, datagramPacket.getLength());
                inetAddress = datagramPacket.getAddress();
                System.out.println("Message from Client " + inetAddress.getHostAddress() + ":" + datagramPacket.getPort() + " : " +  messageFromClient);
                datagramPacket = new DatagramPacket(buffer,buffer.length,inetAddress, datagramPacket.getPort());
                datagramSocket.send(datagramPacket);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket1 = new DatagramSocket(1234);
        Server server = new Server(datagramSocket1);
        server.receiveThenSend();
    }
}
