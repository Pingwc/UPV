package practice5;

import java.util.Scanner;

public class MusicManager {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner kb= new Scanner(System.in);
		ListSongLink listSongs = new ListSongLink();
		int select, counter=0;
		
		//Main menu selection
		do {
			System.out.println("***************Main Menu***************");
			System.out.println("1. Register new song.");
			System.out.println("2. Delete song.");
			System.out.println("3. Search song.");
			System.out.println("4. To show all information of all songs.");
			System.out.println("5. Total number of the songs.");
			System.out.println("0. End.");
			System.out.println("***************************************\n");	
			System.out.print("Select an option: ");
			select=kb.nextInt();
			
			String title, release_date;
			int runtime_minutes, positionRemove;
			boolean favourite;
	
			//Switch case for option menu.
			switch(select) {
				case 1:
					int optionSong;
					//Loop if the users want to add another song
					do {
						System.out.println("***Add song***");
						System.out.println("Title: ");
						title=kb.next();
						System.out.println("Release date: ");
						release_date=kb.next();
						System.out.println("Runtime (minutes): ");
						runtime_minutes=kb.nextInt();
						System.out.println("Favourite (true or false): ");
						favourite=kb.nextBoolean();
						listSongs.insert(counter, title, release_date, runtime_minutes, favourite);
						System.out.println("Do you want to add another song (1=yes || 0=no)?");
						optionSong=kb.nextInt();
						counter++;
					}while(optionSong!=0);
					
					break;
				case 2:	
					System.out.println("Insert a position to remove a song: ");
					positionRemove=kb.nextInt();
					
					listSongs.remove(positionRemove);
					break;
				case 3:
					System.out.println("Insert the title:");
					title=kb.next();
					int found = listSongs.search(title);
					if(found == 1) {
						System.out.println("The song "+title+" is in the list.");
					}else {
						System.out.println("The song "+title+" is not in the list");
					}
					break;
				case 4:
					listSongs.show();
					break;
				case 5:
					//Count both arrays elements
					System.out.println(listSongs.size());
					break;
			}
			
		}while(select!=0); 
		
		//End of operation
		System.out.println("End of operation.");
		kb.close();
	}

}
