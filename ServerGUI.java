import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUI extends JFrame implements ActionListener
{
    JTextField tf;
    static JTextArea ta;
    JButton b;
    static DataInputStream din;
    DataOutputStream dout;
    String msgout;
    static String msgin;
    ServerGUI(Socket s)
    {
        try {
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ta = new JTextArea();
        tf = new JTextField("Server");
        b = new JButton("Send");

        setLayout(null);
        setSize(510,480);
        setVisible(true);
        setTitle("Server");

        ta.setBounds(50,30,400,300);
        ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ta.setEditable(false);
        ta.setFont(new Font("Verdana", Font.BOLD, 11));

        tf.setBounds(50,350,300,30);
        tf.setFont(new Font("Cendara", Font.BOLD, 11));
        tf.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        b.setBounds(370,350,80,30);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(ta);
        add(tf);
        add(b);

        b.addActionListener(this);

//        while(true)
//        {
//            try
//            {
//                msgin=din.readUTF();
//                ta.append("Client : "+msgin+"\n");
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
    }

    public void actionPerformed(ActionEvent e)
    {
        msgout = tf.getText();
        try
        {
            ta.append(msgout+"\n");
            dout.writeUTF(msgout);
            dout.flush();
            tf.setText("");
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    public static void main(String []args)
    {
        try
        {
            System.out.println("Waiting for client...");
            ServerSocket ss = new ServerSocket(1400);
            Socket s =  ss.accept();
            System.out.println("Client Satrted");
            ServerGUI sgui = new ServerGUI(s);
            while(true)
            {
                try
                {
                    msgin=din.readUTF();
//                    if(msgin.equals("end"))
//                        exit();
                    ta.append("Client : "+msgin+"\n");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
