/*
 * Written by Jason Holley and Rituraj Kumar
 */

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
			int comp=a.song.compareToIgnoreCase(lib.get(i).song);
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

		while((a.song.compareToIgnoreCase(lib.get(size).song))==0){//while it is the same song: checks each artist in case of multiple duplicates
			int comp=a.artist.compareToIgnoreCase(lib.get(size).artist);
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

	public int addSong(String name, String artist, String album, String year) throws IOException{
		//adds the song to the arraylist and then to the end of the file if successfully added or -1 if not added
		//returns the index at which the song was added
		Song toAdd = new Song(name, artist, album, year);
		int g = searchList(name, artist);
		int z = abcsong(toAdd);

		if(z==1){
			BufferedWriter writer = null;
			FileWriter fstream = new FileWriter("Songs", true);
			writer = new BufferedWriter(fstream);
			writer.write("\n" + name + "\n");
			writer.write(artist + "\n");
			if(album.equalsIgnoreCase(""))
				writer.write("_\n");
			else
				writer.write(album + "\n");
			if(year.equalsIgnoreCase(""))
				writer.write("_");
			else
				writer.write(year);
			writer.close();
			return g;
		}
		return -1;


	}

	public int editSongNameAndArtistName(int index, Song s, String NewName, String NewArtist) throws IOException{
		//returns new index or -1 if can't edit
			int x;
			x = searchList(NewName, NewArtist);			//check if new name song already exists
			if(x==-1){
				//popup that says we can't have duplicates
				return x;
			}

			deleteSong(index, s.song, s.artist);
			s.song = NewName;
			s.artist = NewArtist;
			x = addSong(s.song, s.artist, s.album, s.year);
			return x;
	}

	public int editSongName(int index, Song s, String NewName) throws IOException{
		//returns new index or -1 if can't edit
			int x;
			x = searchList(NewName, s.artist);			//check if new name song already exists
			if(x==-1){
				//popup that says we can't have duplicates
				return x;
			}

			deleteSong(index, s.song, s.artist);
			s.song = NewName;
			x = addSong(s.song, s.artist, s.album, s.year);
			return x;
	}

	public int editArtist(int index, Song s, String NewArtist) throws IOException{
		//returns new index or -1 if can't edit
		int x;
		x = searchList(s.song, NewArtist);			//check if new name song already exists
		if(x==-1){
			//popup that says we can't have duplicates
			return x;
		}
		deleteSong(index, s.song, s.artist);
		s.artist = NewArtist;
		x = addSong(s.song, s.artist, s.album, s.year);
		return x;
	}

	public void editAlbum(int index, Song s, String NewAlbum) throws IOException{
		deleteSong(index, s.song, s.artist);
		addSong(s.song, s.artist, NewAlbum, s.year);
		s.album = NewAlbum;
	}

	public void editYear(int index, Song s, String NewYear) throws IOException{
		deleteSong(index, s.song, s.artist);
		addSong(s.song, s.artist, s.album, NewYear);
		s.year = NewYear;
	}

	public int searchList(String song, String artist){
		//returns -1 if found, otherwise returns the index where the song will be placed (to be used for edit functions)
		for(int i=0;i<lib.size();i++){
			int comp = song.compareToIgnoreCase(lib.get(i).song);

			if(comp==0){
				while(comp==0){	//if song matches, search if a song with that name has same album
					int compArtist = artist.compareToIgnoreCase(lib.get(i).artist);
					if(compArtist==0)
						return -1;
					else{
						i++;
						comp = song.compareToIgnoreCase(lib.get(i).song);
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
		File old = new File("Songs");
		String everythingElse = "";

		BufferedReader reader = new BufferedReader(new FileReader(old));

		String currentLine;

		while((currentLine = reader.readLine()) != null){
			if(currentLine.equalsIgnoreCase(song)){
				//if song name is found check for artist name that matches
				currentLine = reader.readLine();
				if(currentLine.equalsIgnoreCase(artist)){
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

