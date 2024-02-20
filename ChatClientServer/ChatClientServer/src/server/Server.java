package server;

import Identifiers.Identifiers;
import QueryStatus.QueryStatus;
import client.Client;
import client.ClientView;

import javax.swing.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;

public class Server {

    private boolean isServerWorking = false;
    private final HashMap<Client, Identifiers> connections = new HashMap<Client, Identifiers>();
    private final String fileName = "./ChatClientServer/src/server/Log.txt";
    private final File file = new File(fileName);
    private final ServerView serverView;

    public Server(ServerView serverView) {
        this.serverView = serverView;
    }

    public void startServer(){
        if (!isServerWorking) {
            isServerWorking = true;
            serverView.putMsgToLog(readLogFromFile(), false);
            serverView.putMsgToLog("Сервер запущен!", true);
        }
        else {
            serverView.showMessageDialog("Server already started!");
        }
    };

    public void stopServer(){
        if (isServerWorking) {
            isServerWorking = false;
            connections.clear();
            serverView.putMsgToLog("Сервер остановлен!", true);
        }
        else {
            serverView.showMessageDialog("Server already stopped!");
        }
    };

    private String readLogFromFile() {
        StringBuilder str = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                str.append("%s\n".formatted(line));
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException ignored) {
        }
        catch (IOException e){
            serverView.showMessageDialog(e.getMessage());
        }
        return str.toString();
    }

    private void saveMsgToFile(String msg) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.append(msg).append("\n");
        } catch (IOException ex) {
            serverView.showMessageDialog(ex.getMessage());
        }
    }

    public QueryStatus getConnection(Client client, Identifiers identifiers){

        QueryStatus qs = new QueryStatus();

        if (isServerWorking) {
            if (connections.containsKey(client)) {
                qs.setStatus(QueryStatus.ok);
            }
            else {
                connections.put(client, identifiers);
                serverView.putMsgToLog("К нам пришел " + identifiers.getLogin(), true);
                qs.setStatus(QueryStatus.ok);
            }
        }
        else {
            qs.setStatus(QueryStatus.nok);
            qs.setMsg("Сервер не запущен!");
        }
        return qs;
    };

    public boolean putMessage(String msg, Client client){
        if (connections.containsKey(client)) {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            msg = connections.get(client).getLogin() + ": " + timeStamp + ":> " + msg;
            serverView.putMsgToLog(msg, false);
            sendMessage(msg, client);
            saveMsgToFile(msg);
            return true;
        }
        else {
            return false;
        }
    }

    private void sendMessage(String msg, Client client){
        for (Client connection : connections.keySet()) {
            if (connection != client) {
                connection.putMessage(msg);
            }
        }
    }

    public String getLog(){
        return readLogFromFile();
    }

}
