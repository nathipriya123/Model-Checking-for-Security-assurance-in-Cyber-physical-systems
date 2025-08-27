/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bwddosattacksserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AttackerReceiver extends Thread{
    AttackerFrame af;    
    static String forward;
    
    AttackerReceiver(AttackerFrame f)
    {
        af=f;        
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(7000);
            System.out.println("-------------");
            while(true)
            {
                byte data[]=new byte[10000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim();                                
                forward=str;
                System.out.println("Forward is "+forward);
                String req[]=str.split("#");
                if(req[0].equals("Response"))
                {                    
                    
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
