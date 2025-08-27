/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bw.ddosattacknodes;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Elcot
 */
public class NodeReceiver extends Thread{
    NodeFrame nf;
    int sid,port;
    static ArrayList fdet=new ArrayList();
    String fake;
    
    NodeReceiver(NodeFrame f,int id)
    {
        nf=f;
        sid=id;
        port=sid+6000;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(port);
            while(true)
            {
                byte data[]=new byte[10000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim();                
                System.out.println(str);
                String req[]=str.split("#");
                if(req[0].equals("FileDetails"))
                {
                    if(!(fdet.contains(req[1]+"#"+req[2])))
                    {
                        fdet.add(req[1]+"#"+req[2]);
                        nf.jComboBox1.addItem(req[1]);
                    }
                }
                if(req[0].equals("Response"))
                {
                    nf.jLabel9.setText(req[1]);
                    nf.jTextArea1.setText(req[3]);
                    nf.jTextField1.setText(""+(Integer.parseInt(nf.jTextField1.getText().trim())-5));
                    try
                    {
                        String ms="ACK#"+"Acknowledgement";
                        System.out.println(ms);
                        byte data1[]=ms.getBytes();
                        DatagramSocket ds1=new DatagramSocket();
                        DatagramPacket dp1=new DatagramPacket(data1,0,data1.length,InetAddress.getByName("127.0.0.1"),5000);
                        ds1.send(dp1);
                        System.out.println("port is "+5000);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                if(req[0].equals("AttackerResponse"))
                {
                    nf.jLabel9.setText(req[1]);
                    fake=fake+req[3]+"\n";
                    nf.jTextArea1.setText(fake);
                    nf.jTextField1.setText(""+(Integer.parseInt(nf.jTextField1.getText().trim())-15));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
