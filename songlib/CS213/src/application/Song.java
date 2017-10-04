package application;

public class Song {
	int select=0;
	String song;
	String artist;
	String album;
	String year;
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
			year=null;
		}
		else{
			year=Year;
		}
	}
	
	public String detail(){
		if(this.album==null && this.year==null){
			return "Song: " + this.song + "\nArtist: " + this.artist;
		}
		else if(this.album==null){
			return "Song: " + this.song + "\nArtist: " + this.artist + "\nYear: " + this.year;
		}
		else if(this.year==null){
			return "Song: " + this.song + "\nArtist: " + this.artist+ "\nAlbum: " + this.album;
		}
		
		return "Song: " + this.song + "\nArtist: " + this.artist+ "\nAlbum: "+ this.album +"\nYear: "+ this.year;
		
	}

	public String toString(){
		
		return "Song: " + this.song + "\tArtist: " + this.artist;
		
	}
	
	
}

