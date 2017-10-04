package application;

import java.util.*;
import java.io.*;
public class Library {

	public static List<Song> lib= new ArrayList<Song>(0);


	public Library(String file)throws IOException{
		Scanner sc = new Scanner(new File(file));
		if(sc.hasNextLine())
			sc.nextLine();
		while(sc.hasNextLine()){//scans in each song line by line
			String name = sc.nextLine();// first line is song
			String Artist = sc.nextLine();// second is artist
			String Album=sc.nextLine();// third line album optional, if no album is given input:"_"
			String Year=sc.nextLine();//fourth line year optional, if no year is given input "_"

			Song tmp= new Song(name, Artist, Album, Year);//create song object
			if(lib.size()==0){
				lib.add(tmp);
			}
			else{
			abcsong(tmp);
			}
			//lib.add(tmp);

		}
		sc.close();

	}

	private static int abcsong(Song a){ //sorts songs in order
		//returns 1 if added song, 0 otherwise
		for(int i=0;i<lib.size();i++){
			int comp=a.song.compareTo(lib.get(i).song);
			if(comp==0){//same song check artist
				return abcartist(a,i);
			}
			else if(comp<0){//song comes before current song
				lib.add(i, a);
				return 1;
			}
		}
		lib.add(a);//song comes last
		return 1;

	}

	private static int abcartist(Song a, int size){ //if two songs have the same name sorts by artist
		//returns 1 if added song, 0 otherwise

		while((a.song.compareTo(lib.get(size).song))==0){//while it is the same song: checks each artist in case of multiple duplicates
			int comp=a.artist.compareTo(lib.get(size).artist);
			if(comp==0){
				//Pop-up saying song already exist needed for Song add
				//same song and artist
				return 0;
			}
			else if(comp<0){
				lib.add(size, a);
				return 1;
			}
			size++;
			if(size>=lib.size()){
				size--;
				return 0;
			}
		}
		lib.add(size, a);
		return 1;
	}

	public void printlist(){

		for(int i=0;i<lib.size();i++){
			System.out.print(lib.get(i).song + "\t");
			System.out.print(lib.get(i).artist + "\t");
			System.out.print(lib.get(i).album + "\t");
			System.out.println(lib.get(i).year + "\t");
		}
	}

	public void addSong(String name, String artist, String album, String year) throws IOException{
		//adds the song to the arraylist and then to the end of the file if successfully added
		Song toAdd = new Song(name, artist, album, year);
		int z = abcsong(toAdd);

		if(z==1){
			BufferedWriter writer = null;
			FileWriter fstream = new FileWriter("Songs", true);
			writer = new BufferedWriter(fstream);
			writer.write("\n" + name + "\n");
			writer.write(artist + "\n");
			if(album.equals(""))
				writer.write("_\n");
			else
				writer.write(album + "\n");
			if(year.equals(""))
				writer.write("_");
			else
				writer.write(year);
			writer.close();
		}
	}

	public void editSongName(Song s, String NewName) throws IOException{
			int x;
			x = searchList(NewName, s.artist);			//check if new name song already exists
			if(x==-1){
				//popup that says we can't have duplicates
				return;
			}

			deleteSong(x, s.song, s.artist);
			s.song = NewName;
			addSong(s.song, s.artist, s.album, s.year);
	}

	public void editArtist(Song s, String NewArtist) throws IOException{
		int x;
		x = searchList(s.song, NewArtist);			//check if new name song already exists
		if(x==-1){
			//popup that says we can't have duplicates
			return;
		}
		deleteSong(x, s.song, s.artist);
		s.artist = NewArtist;
		abcsong(s);
	}

	public void editAlbum(Song s, String NewAlbum){
		s.album = NewAlbum;
	}

	public void editYear(Song s, String NewYear){
		s.year = NewYear;
	}

	public int searchList(String song, String artist){
		//returns -1 if found, otherwise returns the index where the song will be placed (to be used for edit functions)
		for(int i=0;i<lib.size();i++){
			int comp = song.compareTo(lib.get(i).song);

			if(comp==0){
				while(comp==0){	//if song matches, search if a song with that name has same album
					int compArtist = artist.compareTo(lib.get(i).artist);
					if(compArtist==0)
						return -1;
					else{
						i++;
						comp = song.compareTo(lib.get(i).song);
					}
				}
			}

			else if(comp<0){//song comes before current song
				return i;
			}

		}

		return lib.size();	//would go at end of list and isn't there

	}


	public void deleteSong(int x, String song, String artist) throws IOException{
			//deletes song at given index and deletes song in file
		//TODO fix delete in file
		File old = new File("Songs");
		String everythingElse = "";

		BufferedReader reader = new BufferedReader(new FileReader(old));

		String currentLine;

		while((currentLine = reader.readLine()) != null){
			if(currentLine.equals(song)){
				//if song name is found check for artist name that matches
				currentLine = reader.readLine();
				if(currentLine.equals(artist)){
					//skip next 2 lines
					currentLine = reader.readLine();
					currentLine = reader.readLine();
					continue;
				}
				else
					//song name matches but not artist
					everythingElse = everythingElse.concat(song + "\n");
			}
			everythingElse = everythingElse.concat(currentLine + "\n");
		}

		everythingElse = everythingElse.substring(0, everythingElse.length()-1);

		reader.close();

		old.delete();

		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter("Songs"));
			writer.write(everythingElse);
		}
		finally{
			writer.close();
		}


		writer.close();

		lib.remove(x);
	}
}

