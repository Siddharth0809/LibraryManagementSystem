package com.library.app;

import java.util.Scanner;
import com.library.dao.*;

public class LibraryApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        IssueDAO issueDAO = new IssueDAO();

        while (true) {

        	System.out.println("=================================");
            System.out.println("📚 LIBRARY MANAGEMENT SYSTEM");
        	System.out.println("=================================\n");
        	System.out.println("=>> Enter Your Choice Between this Options <<= \n");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add Member");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Search by Category");
            System.out.println("7. Search by Author");
            System.out.println("8. Top Issued Books");
            System.out.println("9. Low Stock Alert");
            System.out.println("10. View Members");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();

                    System.out.print("Enter Total Copies: ");
                    int copies = sc.nextInt();
                    sc.nextLine();

                    bookDAO.addBook(title, author, category, copies);
                    break;

                case 2:
                    bookDAO.viewBooks();
                    break;

                case 3:
                    System.out.print("Enter Member Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    memberDAO.addMember(name, email, phone);
                    break;

                case 4:
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Member ID: ");
                    int memberId = sc.nextInt();
                    sc.nextLine();

                    issueDAO.issueBook(bookId, memberId);
                    break;



                case 5:
                    System.out.print("Enter Issue ID: ");
                    int issueId = sc.nextInt();
                    sc.nextLine();

                    issueDAO.returnBook(issueId);
                    break;
                    
                case 6:
                    System.out.print("Enter Category: ");
                    bookDAO.searchByCategory(sc.nextLine());
                    break;

                case 7:
                    System.out.print("Enter Author: ");
                    bookDAO.searchByAuthor(sc.nextLine());
                    break;

                case 8:
                    issueDAO.topIssuedBooks();
                    break;

                case 9:
                    System.out.print("Enter Stock Threshold: ");
                    int t = sc.nextInt();
                    sc.nextLine();
                    bookDAO.lowStockAlert(t);
                    break;
                    
                case 10:
                    memberDAO.viewMembers();
                    break;

                case 0:
                    System.out.println("Thank you!");
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid Choice");
            }
        }
    }
}


