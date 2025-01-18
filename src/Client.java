package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

public class Client {

    Socket socket;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    String clientName;
   public Client(Socket socket, String username){
       try{
           this.socket = socket;
           this.inputStreamReader = new InputStreamReader(socket.getInputStream());
           this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
           this.bufferedReader = new BufferedReader(inputStreamReader);
           this.bufferedWriter = new BufferedWriter(outputStreamWriter);
           this.clientName = username;
//           sendMessage();
       }catch (Exception e){
           e.printStackTrace();
           closeEverything(socket,inputStreamReader,outputStreamWriter,bufferedWriter,bufferedReader);
       }

   }

   public void sendMessage(){
       try{
           bufferedWriter.write(clientName);
           bufferedWriter.newLine();
           bufferedWriter.flush();
           Scanner scanner = new Scanner(System.in);
           while (socket.isConnected()){
               String msgToSend = scanner.nextLine();
               bufferedWriter.write(clientName+": "+ msgToSend);
               bufferedWriter.newLine();
               bufferedWriter.flush();
           }
       }catch (Exception e){
           e.printStackTrace();
           closeEverything(socket,inputStreamReader,outputStreamWriter,bufferedWriter,bufferedReader);
       }
   }

    public void closeEverything(Socket socket,InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if(socket != null) socket.close();
            if(inputStreamReader!= null) inputStreamReader.close();
            if(outputStreamWriter != null) outputStreamWriter.close();
            if(bufferedReader != null) bufferedReader.close();
            if(bufferedWriter != null) bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listenForMessage(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               String messageFromGroupChat;

               try {
                   while (socket.isConnected()){
                       messageFromGroupChat = bufferedReader.readLine();
                       System.out.println(messageFromGroupChat);
                   }
               }catch (Exception e){
                   e.printStackTrace();
                   closeEverything(socket,inputStreamReader,outputStreamWriter,bufferedWriter,bufferedReader);
               }
           }
       }).start();
    }

//   public void startClient(){
//       while(true){
//           String msgReceived =
//       }
//   }
    public static void main(String[] args) {
       try {
           Socket socket1 = new Socket("localhost",1234);
           System.out.println("Enter your UserName : ");
           Scanner scanner = new Scanner(System.in);
           String username = scanner.nextLine();
           Client client = new Client(socket1,username);
           client.listenForMessage();
           client.sendMessage();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
