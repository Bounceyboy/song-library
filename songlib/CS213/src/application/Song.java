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
			year="0";
		}
		else{
			year=Year;
		}
	}

	public String detail(){
		if(this.album==null && this.year=="0"){
			return "Song: " + this.song + "\tArtist: " + this.artist;
		}
		else if(this.album==null){
			return "Song: " + this.song + "\tArtist: " + this.artist + "\tYear: " + this.year;
		}
		else if(this.year=="0"){
			return "Song: " + this.song + "\tArtist: " + this.artist+ "\tAlbum: " + this.album;
		}

		return "Song: " + this.song + "\tArtist: " + this.artist+ "\tAlbum: "+ this.album +"\tYear: "+ this.year;

	}

	public String toString(){

		return "Song: " + this.song + "\tArtist: " + this.artist;

	}


}

