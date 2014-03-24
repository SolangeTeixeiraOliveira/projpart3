package projpart3;

import java.util.Scanner;

public class Librarian extends Book {
	static Scanner adminInput = new Scanner(System.in);

	// generate a report with all the book
	public void createBook() {
		System.out.println("Insert Book's Title: ");
		title = adminInput.nextLine();

		System.out.println("Insert Book's Author: ");
		mainAuthor = adminInput.nextLine();

		System.out.println("Publisher of the Book: ");
		publisher = adminInput.nextLine();

		System.out.println("Publication of the book: ");
		year = adminInput.nextLine();

		status = "Available";
		borrower = "empty";
		borrowDate = "empty";
		returnDate = "empty";

	}

	public void addBook() {
		Book newBook = new Book();
		// newBook.createBook;
		BookList.add(newBook);
	}

	public void bookList() {
		// verify if list is empty
		int i;
		if (BookList.size() == 0) {
			System.out.println("There are no Book");
			// Display to main menu.
		} else {
			for (i = 0; i < BookList.size(); i++) {
				System.out.printf("\n Books \n", i + 1);
				System.out.println("List of all books:");
				System.out.println(BookList.get(i).informationBook());

			}
		}
	}

	public void main() {
		System.out.println("Our fantastic Library");

	}

}
