package client;

public interface ClientView {
    void putMessage(String msg);
    String getLogin();
    String getPassword();

    void setLog(String text);

    void showMessageDialog(String msg);
}
