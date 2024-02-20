package server;

import Identifiers.Identifiers;
import QueryStatus.QueryStatus;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;

public class ServerGUI extends JFrame implements ServerView{

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;


    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private final JPanel panelBottom = new JPanel(new GridLayout(1, 2));

    private final Server server = new Server(this);


//    private boolean isServerWorking;
//    private final HashMap<Client, Identifiers> connections = new HashMap<Client, Identifiers>();
//    private final String fileName = "./ChatClientServer/src/server/Log.txt";
//    private final File file = new File(fileName);


    public Server getServer() {
        return server;
    }

    public ServerGUI() throws HeadlessException {

//        isServerWorking = false;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setTitle("ChatServer");
        setResizable(false);

        panelBottom.add(btnStart);
        panelBottom.add(btnStop);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

//        Server server = new Server(this);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!isServerWorking) {
//                    isServerWorking = true;
//                    putMsgToLog("Сервер запущен!", true);
//                }
//                else {
//                    JOptionPane.showMessageDialog(ServerGUI.this, "Server already started!");
//                }
                server.startServer();

            };
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (isServerWorking) {
//                    isServerWorking = false;
//                    connections.clear();
//                    putMsgToLog("Сервер остановлен!", true);
//                }
//                else {
//                    JOptionPane.showMessageDialog(ServerGUI.this, "Server already stopped!");
//                }
                server.stopServer();
            };
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
//                log.setText(readLogFromFile());
            }

            @Override
            public void windowClosing(WindowEvent e) {
//                if (isServerWorking){
//                    JOptionPane.showMessageDialog(ServerGUI.this, "Перед закрытием необходимо остановить сервер!");
//                }
//                else {
//                    setVisible(false);
//                    System.exit(0);
//                }
                server.stopServer();
                setVisible(false);
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setVisible(true);

    }


    @Override
    public void putMsgToLog(String msg, boolean needTimestamp){
        if (needTimestamp) {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            log.append(timeStamp + ":> " + msg + "\n");
        }
        else{
            log.append(msg + "\n");
        }

    }

//    private void saveMsgToFile(String msg) {
//        try (FileWriter fw = new FileWriter(file, true)) {
//            fw.append(msg).append("\n");
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(ServerGUI.this, ex.getMessage());
//
//        }
//    }

//    private String readLogFromFile() {
//        StringBuilder str = new StringBuilder();
//        try {
//            FileReader fr = new FileReader(file);
//            BufferedReader reader = new BufferedReader(fr);
//            String line = reader.readLine();
//            while (line != null) {
//
//                str.append("%s\n".formatted(line));
//                line = reader.readLine();
//
//            }
//
//        }
//        catch (FileNotFoundException ignored) {
//        }
//        catch (IOException e){
//            JOptionPane.showMessageDialog(ServerGUI.this, e.getMessage());
//        }
//
//        return str.toString();
//
//    }

//    public QueryStatus getConnection(Client cw, Identifiers identifiers){
//
//        QueryStatus qs = new QueryStatus();
//
//        if (isServerWorking) {
//            if (connections.containsKey(cw)) {
//                QueryStatus.setStatus(QueryStatus.ok);
//            }
//            else {
//                connections.put(cw, identifiers);
//                putMsgToLog("К нам пришел " + identifiers.getLogin(), true);
//                QueryStatus.setStatus(QueryStatus.ok);
//            }
//        }
//        else {
//            QueryStatus.setStatus(QueryStatus.nok);
//            QueryStatus.setMsg("Сервер не запущен!");
//        }
//        return qs;
//    };

//    public boolean putMessage(String msg, Client cwFrom){
//        if (connections.containsKey(cwFrom)) {
//            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//            msg = connections.get(cwFrom).getLogin() + ": " + timeStamp + ":> " + msg;
//            putMsgToLog(msg, false);
//            sendMessage(msg, cwFrom);
//            saveMsgToFile(msg);
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

//    private void sendMessage(String msg, Client cwFrom){
//        for (Client connection : connections.keySet()) {
//            if (connection != cwFrom) {
//                connection.putMessage(msg);
//            }
//        }
//    }

//    public String getLog(Client cwFrom){
//        return readLogFromFile();
//    }
//
    @Override
    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
