package songlib;

import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
		Library songlib = new Library("Songs");
		songlib.printlist();
		//System.out.println("b".compareTo("a"));
	}

}
