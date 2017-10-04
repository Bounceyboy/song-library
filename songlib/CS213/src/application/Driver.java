/*
 * Written by Jason Holley and Rituraj Kumar
 */

package application;

import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
		Library songlib = new Library("Songs");
		songlib.addSong("asong","aabrtist","","");
		songlib.addSong("bsong","aabrtist","","");
		songlib.addSong("csong","abbrtist","","");
		songlib.addSong("bsong","abbrtist","","");
		songlib.deleteSong(0, "asong", "aabrtist");
		songlib.printlist();
		//System.out.println("b".compareTo("a"));
	}

}
