package src;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        Socket socket = null; // A socket is an endpoint for communication between two systems. It contains the info about the IP network adresses and TCP port (if TCP is used for comm) for the particular systems.
        // In Java, a character stream class will end with Reader or writer (e.g InputStreamReader). A byte stream will end with Stream (e.g Input Stream)
        InputStreamReader inputStreamReader = null;
        // OutputStreamWriter and InputStreamReader are both bridges from byteStreams to character streams (since we wont understand byte)
        // The underlying byte streams from the socket are OutputStream and InputStream respectively.
        OutputStreamWriter outputStreamWriter = null;
        //BufferedReader and BufferedWriter are used to improve the efficiency of the OutputStreamWriter which instead of writing one character at a time will write to the socket a block of characters.
        //Flushing a stream forces any buffered bytes to be written out (in the case of an output stream) or read in (in the case of input stream).
        //A buffer will only flush if full unless forced to throgh code.

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        // A Server Socket waits for requests to come in over the network. Our client we made is trying to connect to port 1234. Thus, we want our server socket to be waiting for a connection on port 1234.
        ServerSocket serverSocket = new ServerSocket(1234);
        // The first while loop is to ensure the server is constantly running. The second while loop is to ensure that,once the client is connected, the server is constantly interacting with the client until the client disconnects
        while (true){
            try {
                // The accept() method of the ServerSocket calss waits for a client connection (it is blocking call,i.e, the program wont advance until a client has connected). Once connected, a Socket object is returned that can be used to communicate with the client.
                socket = serverSocket.accept();
                System.out.println("New Client Connected.");
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true){
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: " + msgFromClient);
                    bufferedWriter.write("MSG Received.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(msgFromClient.equalsIgnoreCase("BYE")){
                        System.out.println("Client Disconnected.");
                        break;
                    }
                }
                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedWriter.close();
                bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
