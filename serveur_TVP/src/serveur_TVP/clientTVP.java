package serveur_TVP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class clientTVP {

	final int port = 10000 ;
	DatagramSocket socket = null ; DatagramPacket 	PacketRequete,PacketReponse ;
	InetAddress address = null ;

	byte [] reponse = new byte [30] ; 
	String requete = "heure";

	try {
		socket = new DatagramSocket () ;
	} 
	catch (SocketException e)
	{
		System.err.println ("Erreur socket " + e) ;
		System.exit (1) ;
	}
	try {
		address = InetAddress.getByName ("localhost") ;
	} 
	catch (IOException e)
	{
		System.err.println ("Host inconnu") ;
		System.exit (1) ;
	}

	PacketRequete = new DatagramPacket (requete.getBytes(),requete.length(),address,port);
	PacketReponse = new DatagramPacket (reponse,reponse.length) ;
	try {
		socket.send (PacketRequete) ;
		socket.receive(PacketReponse);
		System.out.println ( new String (PacketReponse.getData())) ;
	} 
	catch (IOException e)
	{
		System.err.println ("Erreur de lecture/ecriture " + e) ;
		System.exit (1) ;
	}
}


}
