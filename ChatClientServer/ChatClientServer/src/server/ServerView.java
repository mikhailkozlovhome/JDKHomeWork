package server;

public interface ServerView {
    void showMessageDialog(String msg);

    void putMsgToLog(String msg, boolean needTimestamp);
}
