package src;


import java.net.*;

public class Server {

    ServerSocket serverSocket;

    public Server(ServerSocket socket){
        this.serverSocket = socket;
    }

    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has joined the chat !");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket1 = new ServerSocket(1234);
            Server server = new Server(serverSocket1);
            server.startServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
