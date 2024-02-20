import client.ClientGUI;
import server.ServerGUI;

public class Main {
    public static void main(String[] args) {
        ServerGUI ServerWindow = new ServerGUI();
        new ClientGUI(ServerWindow.getServer());
        new ClientGUI(ServerWindow.getServer());
    }
}