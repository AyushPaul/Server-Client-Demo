package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    Socket socket;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    String clientName;

    static ArrayList<ClientHandler> clientList = new ArrayList<>();
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(this.inputStreamReader);
            this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);
            this.clientName = bufferedReader.readLine();
            clientList.add(this);
            BroadCastMessage("SERVER: " + clientName + " has entered the chat!");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void BroadCastMessage(String msgToSend){
        try {
            if(clientList.size() == 1){
                this.bufferedWriter.write("SERVER : This chatroom is currently empty.");
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }else{
                for(ClientHandler clientHandler:clientList){
                    if(!clientHandler.clientName.equalsIgnoreCase(this.clientName)){
                        clientHandler.bufferedWriter.write(msgToSend);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            closeEverything(socket,inputStreamReader,outputStreamWriter,bufferedWriter,bufferedReader);
        }
    }

    public void closeEverything(Socket socket,InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        clientList.remove(this);
        BroadCastMessage("SERVER : " + this.clientName + " has left the chat!");
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
    @Override
    public void run() {
        String msgToSend;
        while (socket.isConnected()){
            try {
                msgToSend = bufferedReader.readLine();
                BroadCastMessage(msgToSend);
            }catch (Exception e){
                closeEverything(socket,inputStreamReader,outputStreamWriter,bufferedWriter,bufferedReader);
                e.printStackTrace();
            }
        }
    }
}
