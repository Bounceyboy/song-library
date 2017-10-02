package songlib;

import java.util.*;
import java.io.*;
public class Library {

	public static List<Song> lib= new ArrayList<Song>(0);


	public Library(String file)throws IOException{
		Scanner sc = new Scanner(new File(file));
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

	private static void abcsong(Song a){ //sorts songs in order
		for(int i=0;i<lib.size();i++){
			int comp=a.song.compareTo(lib.get(i).song);
			if(comp==0){//same song check artist
				abcartist(a,i);
				return;
			}
			else if(comp<0){//song comes before current song
				lib.add(i, a);
				return;
			}
		}
		lib.add(a);//song comes last

	}

	private static void abcartist(Song a, int size){ //if two songs have the same name sorts by artist

		while((a.song.compareTo(lib.get(size).song))==0){//while it is the same song: checks each artist incase of multiple duplicates
			int comp=a.artist.compareTo(lib.get(size).artist);
			if(comp==0){
				//Pop-up saying song already exist needed for Song add
				//same song and artist
				return;
			}
			else if(comp<0){
				lib.add(size, a);
				return;
			}
			size++;
			if(size>=lib.size()){
				size--;
				break;
			}
		}
		lib.add(size, a);
	}

	public void printlist(){

		for(int i=0;i<lib.size();i++){
			System.out.print(lib.get(i).song + "\t");
			System.out.print(lib.get(i).artist + "\t");
			System.out.print(lib.get(i).album + "\t");
			System.out.println(lib.get(i).year + "\t");
		}
	}

	public void addSong(String name, String artist, String album, String year){
		Song toAdd = new Song(name, artist, album, year);
		abcsong(toAdd);
	}

	public void editSongName(Song s, String NewName){
			int x;
			x = searchList(NewName, s.artist);			//check if new name song already exists
			if(x==-1){
				//popup that says we can't have duplicates
				return;
			}
			deleteSong(x);
			s.song = NewName;
			abcsong(s);
	}

	public void editArtist(Song s, String NewArtist){
		int x;
		x = searchList(s.song, NewArtist);			//check if new name song already exists
		if(x==-1){
			//popup that says we can't have duplicates
			return;
		}
		deleteSong(x);
		s.artist = NewArtist;
		abcsong(s);
	}

	public void editAlbum(Song s, String NewAlbum){
		s.album = NewAlbum;
	}

	public void editYear(Song s, int NewYear){
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


	public void deleteSong(int x){
			//deletes song at given index
		lib.remove(x);
	}
}

