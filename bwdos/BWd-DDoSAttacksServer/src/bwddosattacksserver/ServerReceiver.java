/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bwddosattacksserver;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elcot
 */
public class ServerReceiver extends Thread{
    ServerFrame sf;
    static ArrayList fidet=new ArrayList();
    
    ServerReceiver(ServerFrame f)
    {
        sf=f;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(5000);
            while(true)
            {
                byte data[]=new byte[10000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim();                
                System.out.println(str);
                String req[]=str.split("#");
                if(req[0].equals("Connect"))
                {
                    DefaultTableModel dm=(DefaultTableModel)sf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    dm.addRow(v);
                    
                    int d=Integer.parseInt(req[1])+6000;
                    for(int i=0;i<sf.jTable2.getRowCount();i++)
                    {
                        String s=sf.jTable2.getValueAt(i,0).toString();
                        String s1=sf.jTable2.getValueAt(i,1).toString();
                        String ms="FileDetails#"+s+"#"+s1;
                        System.out.println(ms);
                        byte data1[]=ms.getBytes();
                        DatagramSocket ds1=new DatagramSocket();
                        DatagramPacket dp1=new DatagramPacket(data1,0,data1.length,InetAddress.getByName("127.0.0.1"),d);
                        ds1.send(dp1);
                        System.out.println("port is "+d);
                    }
                }
                if(req[0].equals("Request"))
                {
                    if(!(fidet.contains(req[1]+"#"+req[2]+"#"+req[3])))
                    {
                        sf.jComboBox1.addItem(req[1]);
                        fidet.add(req[1]+"#"+req[2]+"#"+req[3]);
                    }
                }
                if(req[0].equals("ACK"))
                {
                    sf.jLabel8.setText("Acknowledgement Message Received!");
                }
                if(req[0].equals("Key"))
                {
                    try
                    {
                        byte[] encodedKey     = Base64.decode(req[1]);
                        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES"); 
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
