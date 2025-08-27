/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bw.ddosattacknodes;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Elcot
 */
public class Main {
    public static void main(String args[])
    {
        String s=JOptionPane.showInputDialog(new JFrame(),"Enter the Node Id: ");
        
        NodeFrame nf=new NodeFrame(Integer.parseInt(s));
        nf.setVisible(true);
        nf.setTitle("Node-"+s);
        nf.setResizable(false);
        
        NodeReceiver nr=new NodeReceiver(nf,Integer.parseInt(s));
        nr.start();
    }
}
