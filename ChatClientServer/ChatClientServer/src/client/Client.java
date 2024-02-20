package client;

import Identifiers.Identifiers;
import QueryStatus.QueryStatus;
import server.Server;


public class Client {
    private boolean isClientConnected = false;
    private final Identifiers identifiers = new Identifiers();
    private final ClientView clientView;
    private final Server server;

    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    private void setIdentifiers() {
        identifiers.setLogin(clientView.getLogin());
        identifiers.setPassword(clientView.getPassword());
    }

    public void getConnectionToServer(){
        if (isClientConnected) {
            clientView.showMessageDialog("Связь с сервером уже установлена!");
        }
        else {
            //TODO проверка на наличие логина и пароля должна быть на сервере?!
            setIdentifiers();
            if (!identifiers.getLogin().isEmpty() && !identifiers.getPassword().isEmpty()) {
                QueryStatus qs = server.getConnection(this, identifiers);
                if (qs.getStatus() >= 0) {
                    clientView.setLog(server.getLog());
                    isClientConnected = true;
                } else {
                    clientView.showMessageDialog(qs.getMsg());
                }
            }
            else {
                clientView.showMessageDialog("Логин и пароль должны быть указаны!!!");
            }
        }
    }

    public boolean sendMessageToServer(String msg){
        if (isClientConnected) {
            if (server.putMessage(msg, this)) {
                return true;
            }
            else {
                clientView.showMessageDialog("Что-то пошло не так!!!");
            }
        }
        else {
            clientView.showMessageDialog("Необходимо установить соединение с сервером!!!");
        }
        return false;
    }

    public void putMessage(String msg){
      clientView.putMessage(msg);
    }
}
