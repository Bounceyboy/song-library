package songlib;

public class Song {

	String song;
	String artist;
	String album;
	int year;
	public Song(String name, String Artist, String Album, String Year){
		song=name;
		artist=Artist;
		if(Album.equals("_")){
			album=null;
		}
		else{
			album=Album;
		}
		if(Year.equals("_")){
			year=0;
		}
		else{
			year=Integer.parseInt(Year);
		}
	}
}
