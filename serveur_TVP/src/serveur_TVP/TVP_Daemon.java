package serveur_TVP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TVP_Daemon  extends Thread{
	private String Path;
	
	public TVP_Daemon() {
		// TODO Auto-generated constructor stub
		this.Path = "C:\\Users\\basti\\OneDrive\\Bureau\\IUT\\Semestre 3\\R309 - Programmation événementielle\\serveur_tvp";
	}
	
	public String lsToString() {
		File myObj = new File(this.Path);
		String res = "";
		for(String obj: myObj.list()) {
			res+=obj + "\r\n";
		}
		return res;
	}
	
	public String viewToString(String momfichier) {
		String res = "";
		 try{
			 	File myObj = new File(this.Path);
				BufferedReader in = new BufferedReader(new FileReader(myObj +"\\" + momfichier));
				String s = new String();
				
				while((s = in.readLine()) != null)
					res+=s+"\r\n";
				in.close();

			 } catch(IOException e) {return "Le fichier xxxx n’est pas lisible";}
		 return res;
	}
	
	public String rename(String oldName, String newName) {
		File myObj = new File(this.Path);
		try {
			File Obj = new File(myObj +"\\" + oldName );
			File newObj = new File(myObj +"\\" + newName);
			Obj.renameTo(newObj);
		}catch(Exception e){return "pas possible";}
		return "cest bon";
	}

	public String cp(String source, String cible) {
		
		try {
			Path sourcePath = Paths.get(Path +"\\" + source );
			Path ciblePath = Paths.get(Path +"\\" + cible );
			Files.copy(sourcePath, ciblePath);
		}catch(Exception e){return "pas possible";}
		return "cest bon";
	}
	
	public String cd(String rep) {
		this.Path = this.Path + "\\" + rep;
		return this.Path;
	}
	
	public void  runServeur() throws IOException {
		
		int numPort = 10000;
		ServerSocket s = null;
		try { s = new ServerSocket (numPort) ;} 
		catch (IOException e)
		{
			System.err.println ("Erreur socket " + e) ;
			System.exit (1) ;
		}
		System.out.println("Serveur en écoute sur le port "+numPort);
		// on accepte la connexion
		Socket service;
		
		boolean quit = false;
		while(!quit) {
			service = s.accept ();
			System.out.println("Client connect� :"+service);

			(new ServeurThread(service)).start();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		TVP_Daemon s = new TVP_Daemon();
		s.runServeur();
		
	}

}
