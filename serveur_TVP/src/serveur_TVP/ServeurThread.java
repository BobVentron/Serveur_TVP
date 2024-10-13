package serveur_TVP;
import java.net.* ;
import java.io.* ;
import java.util.Date ;
import java.io.File; 
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ServeurThread  extends Thread{

	private Socket service;

	public ServeurThread(Socket s) {
		this.setService(s);
	}

	public Socket getService() {
		return service;
	}

	public void setService(Socket service) {
		this.service = service;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (service.getInputStream ()));
			// out = flux du serveur vers le client permettant d'écrire des chaines 
			// (voir PrintStream)
			PrintStream out =  new PrintStream (service.getOutputStream(), true) ;;
			TVP_Daemon s1 = new TVP_Daemon();
			
			boolean quit = false;
			while (!quit )
			{
				
				// on attend une requête
				String requete = in.readLine();
				System.out.println(requete);
				String[] myArray = requete.split("\\s");
				
				// on regarde si la requete concerne la consultation d'un heure
				if (myArray[0].equals("ls")) out.println (s1.lsToString()) ;
				if (myArray[0].equals("view")) out.println (s1.viewToString(myArray[1])) ;
				if (myArray[0].equals("rename")) out.println (s1.rename(myArray[1], myArray[2])) ;
				if (myArray[0].equals("cp")) out.println (s1.cp(myArray[1], myArray[2])) ;
				if (myArray[0].equals("cd")) out.println (s1.cd(myArray[1])) ;
				if (myarray[0].equals(cc)) out.println(s1.countStackFrames());
				File logread = new File("C:\\\\Users\\\\basti\\\\OneDrive\\\\Bureau\\\\IUT\\\\Semestre 3\\\\R309 - Programmation événementielle\\\\serveur_tvp\\\\journal.dat");
				Scanner myReader = new Scanner(logread);
				String res = "";
			      while (myReader.hasNextLine()) {
			        String data = myReader.nextLine();
			        res += "\r\n" + data;
			      }
			    myReader.close();
				LocalDateTime myDateObj = LocalDateTime.now();
				DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				String formattedDate = myDateObj.format(myFormatObj);
				FileWriter log = new FileWriter("C:\\Users\\basti\\OneDrive\\Bureau\\IUT\\Semestre 3\\R309 - Programmation événementielle\\serveur_tvp\\journal.dat");
				log.write(res + "\r\n" +formattedDate + " : " + requete);
				log.close();
				
				// on regarde si la requete concerne la fin d'utilisation du serveur
				if (requete.equals("exit")) quit=true;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
