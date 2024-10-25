package mase.oop1.code1;

import java.util.ArrayList;

public class PhotoAlbum {
	
	public static void main(String[] args) {
		
		ArrayList<Photo> photoAlbum = new ArrayList<>();
		
		WildAnimal simba = new Lion("Simba");
		Photo photoOfSimba = Photo.createNewInstance("Serengeti National Park", simba);
		photoAlbum.add(photoOfSimba);
		
		WildAnimal koko = new Gorilla("Koko");
		Photo photoOfKoko = Photo.createNewInstance("Bwindi National Park", koko);
		photoAlbum.add(photoOfKoko);
		
		processPhotoAlbum(photoAlbum);
			
		}
	
	public static void processPhotoAlbum(ArrayList<Photo> photoAlbum) {
		for (Photo photo: photoAlbum) {
			System.out.print(photo);
			if (photo.getWildAnimal() instanceof ProtectedSpecies) {
				System.out.println(" - is a protected species");
			}
			System.out.println("\n");
		}
	}
	
	
		
		
}


