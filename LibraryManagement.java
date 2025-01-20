/*[[[[[[[[[[[[[[[[[[[[[[[the java project]]]]]]]]]]]]]]]]]]]]]]*/

import java.util.*;

class LibraryManagement {
    static class Book {
        String title;
        String author;
        double price;

        Book(String title, String author, double price) {
            this.title = title;
            this.author = author;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        Map<String, List<Book>> categories = new LinkedHashMap<>();
        categories.put("Fiction", Arrays.asList(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", 150.00),
                new Book("1984", "George Orwell", 135.00)
        ));
        categories.put("Science", Arrays.asList(
                new Book("A Brief History of Time", "Stephen Hawking", 122.00),
                new Book("The Selfish Gene", "Richard Dawkins", 180.75)
        ));
        categories.put("Technology", Arrays.asList(
                new Book("Clean Code", "Robert C. Martin", 135.99),
                new Book("The Pragmatic Programmer", "Andrew Hunt", 440.50)
        ));

        List<Book> cart = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Display Categories");
            System.out.println("2. Display Books in a Category");
            System.out.println("3. Add Book to Cart");
            System.out.println("4. Generate Bill");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Categories:");
                    for (String category : categories.keySet()) {
                        System.out.println("- " + category);
                    }
                    break;

                case 2:
                    System.out.print("\nEnter category name: ");
                    String category = scanner.nextLine();
                    if (categories.containsKey(category)) {
                        System.out.println("\nBooks in " + category + " category:");
                        List<Book> books = categories.get(category);
                        for (int i = 0; i < books.size(); i++) {
                            Book book = books.get(i);
                            System.out.println((i + 1) + ". " + book.title + " by " + book.author + " - $" + book.price);
                        }
                    } else {
                        System.out.println("Invalid category!");
                    }
                    break;

                case 3:
                    System.out.print("\nEnter category name: ");
                    String cartCategory = scanner.nextLine();
                    if (categories.containsKey(cartCategory)) {
                        System.out.println("Books in " + cartCategory + " category:");
                        List<Book> cartBooks = categories.get(cartCategory);
                        for (int i = 0; i < cartBooks.size(); i++) {
                            Book book = cartBooks.get(i);
                            System.out.println((i + 1) + ". " + book.title + " by " + book.author + " - $" + book.price);
                        }
                        System.out.print("Enter book number to add to cart: ");
                        int bookChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (bookChoice > 0 && bookChoice <= cartBooks.size()) {
                            cart.add(cartBooks.get(bookChoice - 1));
                            System.out.println("Book added to cart!");
                        } else {
                            System.out.println("Invalid book number!");
                        }
                    } else {
                        System.out.println("Invalid category!");
                    }
                    break;

                case 4:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty!");
                    } else {
                        double total = 0;
                        System.out.println("\n--- Bill ---");
                        for (Book book : cart) {
                            System.out.println(book.title + " - $" + book.price);
                            total += book.price;
                        }
                        System.out.println("Total: $" + total);
                        cart.clear();
                    }
                    break;

                case 5:
                    exit = true;
                    System.out.println("Exiting... Thank you for using the Library Management System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
}