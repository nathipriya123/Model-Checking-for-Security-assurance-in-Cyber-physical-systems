/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bwddosattacksserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttackingAgentReceiver extends Thread{
    AttackingAgentFrame af; 
    int sid,port;
    
    AttackingAgentReceiver(AttackingAgentFrame f,int id)
    {
        af=f;
        sid=id;
        port=sid+9000;
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
                if(req[0].equals("Response"))
                {                                        
                    String fake="";
                    int rd=(int)(Math.random()*30);
                    while(rd==0)
                    {
                        rd=(int)(Math.random()*30);
                    }
                    for(int i=0;i<rd;i++)
                    {
                        int rd1=(int)(Math.random()*10);
                        while(rd1==0)
                        {
                            rd1=(int)(Math.random()*10);
                        }
                        try 
                        {
                            fake=fake+(FakeStringGenerator.generateFakeString(10,FakeStringGenerator.Mode.ALPHA))+" ";
                        } 
                        catch (Exception ex)
                        {
                            Logger.getLogger(AttackerFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if((i%5)==0)
                        {
                            fake=fake+"\n";
                        }            
                    }
                    int request = req[3].getBytes("US-ASCII").length;            //for DNS Amplification Attacks
                    int response = fake.getBytes("US-ASCII").length;
                    System.out.println("Spoofed Request is "+request+" bytes");
                    System.out.println("Large Response is "+response+" bytes");
                    fake=req[3]+"\n"+fake;
                    String ms="AttackerResponse#"+req[1]+"#"+req[2]+"#"+fake+"#"+req[4];
                    try
                    {            
                        System.out.println(ms);
                        byte data1[]=ms.getBytes();
                        DatagramSocket ds1=new DatagramSocket();
                        DatagramPacket dp1=new DatagramPacket(data1,0,data1.length,InetAddress.getByName("127.0.0.1"),8000);
                        ds1.send(dp1);
                        System.out.println("port is "+8000);
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

    private static class FakeStringGenerator {

        public static enum Mode {
	    ALPHA, ALPHANUMERIC, NUMERIC 
	}
	
	public static String generateFakeString(int length, FakeStringGenerator.Mode mode) throws Exception {

		StringBuffer buffer = new StringBuffer();
		String characters = "";

		switch(mode){
		
		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		
		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;
	
		case NUMERIC:
			characters = "1234567890";
		    break;
		}
		
		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}
    }
}
