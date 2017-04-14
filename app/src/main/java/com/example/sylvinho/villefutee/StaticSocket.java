package com.example.sylvinho.villefutee;

/**
 * Created by sylvinho on 13/04/2017.
 */

import java.net.Socket;

public class StaticSocket {

    private static  Socket socket;
    private static int i=0;



    public static synchronized Socket getSocket() // static obligatoire car sinon on a besoin d'une instance pour appeler cette méthode
    {

        return socket;
    }

    public static synchronized void setSocket(Socket newsocket)
    {
        i=1;
        socket= newsocket;
    }

    public static int getI()
    {
        return i;
    }


}
