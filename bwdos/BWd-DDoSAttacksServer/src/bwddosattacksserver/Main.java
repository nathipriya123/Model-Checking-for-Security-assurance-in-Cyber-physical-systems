/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bwddosattacksserver;

/**
 *
 * @author Elcot
 */
public class Main {
    public static void main(String args[])
    {
        ServerFrame sf=new ServerFrame();
        sf.setVisible(true);
        sf.setResizable(false);
        sf.setTitle("Server Frame");
        
        ServerReceiver sr=new ServerReceiver(sf);
        sr.start();
    }
}
