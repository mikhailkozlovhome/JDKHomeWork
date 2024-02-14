import client.ClientWindow;
import server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow ServerWindow = new ServerWindow();
        new ClientWindow(ServerWindow);
        new ClientWindow(ServerWindow);
    }
}