package projpart3;

import java.util.ArrayList;
import java.util.Scanner;

public class Book {
	public String callNumber;
	public String isbn;
	public String title;
	public String mainAuthor;
	public String publisher; 
	public String publicationYear;
	
	public String status;
	public String borrower;
	public String borrowDate;
	public String returnDate;
	
	public String status1 = "Borrowed";
	public String status2 = "Avaliable";
	
	public ArrayList<Book> BookList = new ArrayList<Book>();
	public Scanner adminInput = new Scanner(System.in);
	
	public String informationBook(){
		String InfoBook = "\nTitle                   "+ title+
						   "\nAuthor                 "+ mainAuthor+
						   "\nPublisher              "+ publisher+
						   "\nPublicationYear        "+ publicationYear+
						   "\nStatus                 "+ status+
						   "\borrower                "+ borrower+
						   "\borrowDate              "+ borrowDate+
						   "\returnDate              "+ returnDate+ 
						   "\n----------------------------";
		return InfoBook;				  
						   
	}
		

}
