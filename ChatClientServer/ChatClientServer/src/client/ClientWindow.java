package client;

import Identifiers.Identifiers;
import QueryStatus.QueryStatus;
import server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;

public class ClientWindow extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.0");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField();
    private final JTextField tfPassword = new JTextField();
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private boolean isClientConnected;

    private Identifiers identifiers;


    public ClientWindow(ServerWindow sw) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat");
        setResizable(false);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

        isClientConnected = false;
        identifiers = new Identifiers();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isClientConnected) {
                    JOptionPane.showMessageDialog(ClientWindow.this, "Связь с сервером уже установлена!");
                }
                else {
                    identifiers.setLogin(tfLogin.getText());
                    identifiers.setPassword(tfPassword.getText());
                    if (!identifiers.getLogin().isEmpty() && !identifiers.getPassword().isEmpty()){
                        QueryStatus qs = sw.getConnection(ClientWindow.this, identifiers);
                        if (QueryStatus.getStatus() >= 0) {
                            log.setText(sw.getLog(ClientWindow.this));
                            isClientConnected = true;
                        }
                        else {
                            JOptionPane.showMessageDialog(ClientWindow.this, QueryStatus.getMsg());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(ClientWindow.this, "Логин и пароль должны быть заполнены!!!");
                    }

                }
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isClientConnected) {
                    if (sw.putMessage(tfMessage.getText(), ClientWindow.this)) {
                        putMsgToLog(tfMessage.getText(), true);
                        tfMessage.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(ClientWindow.this, "Что-то пошло не так!!!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(ClientWindow.this, "Необходимо установить соединение с сервером!!!");
                }
            }
        });

        tfMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (isClientConnected) {
                        if (sw.putMessage(tfMessage.getText(), ClientWindow.this)) {
                            putMsgToLog(tfMessage.getText(), true);
                            tfMessage.setText("");
                        }
                        else {
                            JOptionPane.showMessageDialog(ClientWindow.this, "Что-то пошло не так!!!");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(ClientWindow.this, "Необходимо установить соединение с сервером!!!");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

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

        tfLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        tfPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setVisible(true);
    }

    public boolean putMessage(String msg){
        putMsgToLog(msg, false);
        return true;
    }

    private void putMsgToLog(String msg, boolean needTimestamp){
        if (needTimestamp) {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            log.append(timeStamp + ":> " + msg + "\n");
        }
        else{
            log.append(msg + "\n");
        }

    }
}
