package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

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

        try {
            socket = new Socket("localhost",1234);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            while (true){
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("Server: " + bufferedReader.readLine());
                if(msgToSend.equalsIgnoreCase("BYE")){
                    break;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(socket != null){
                    socket.close();
                }
                if(inputStreamReader != null){
                    inputStreamReader.close();
                }
                if(outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
