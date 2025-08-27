/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bwddosattacksserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RouterReceiver extends Thread{
    RouterFrame rf;
    static String forward,forw;
    
    RouterReceiver(RouterFrame f)
    {
        rf=f;        
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(8000);
            while(true)
            {
                byte data[]=new byte[10000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim();                
                System.out.println(str);                
                String req[]=str.split("#");
                if(req[0].equals("Response"))
                {
                    forward=str;
                }
                if(req[0].equals("AttackerResponse"))
                {
                    forward=str;
                }
                if(req[0].equals("RoutResponse"))
                {
                    forw="Response#"+req[1]+"#"+req[2]+"#"+req[3]+"#"+req[4];
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

