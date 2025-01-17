package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

public class Client {

    public DatagramSocket datagramSocket;
    public InetAddress inetAddress;
    public byte[] buffer = new byte[256];

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress){
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive(){
        Scanner scanner = new Scanner(System.in);
        try{
            while (true){
                String msgToSend = scanner.nextLine();
                buffer = msgToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length,inetAddress,1234);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String msgFromServer = new String(datagramPacket.getData(),0, datagramPacket.getLength());
                System.out.println("Server on "+ datagramPacket.getAddress().getHostAddress()+":" + datagramPacket.getPort() + " : " + msgFromServer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket1 = new DatagramSocket();
        InetAddress inetAddress1 = InetAddress.getByName("localhost");
        Client client = new Client(datagramSocket1,inetAddress1);
        System.out.println("Send Datagram packets to Server");
        client.sendThenReceive();
    }
}
