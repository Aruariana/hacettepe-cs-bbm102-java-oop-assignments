import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// This is the manager class that handles the necessary operations for the system to work
public class Manager {

    private String outputStr = "";
    private List<Book> bookList = new ArrayList<>();
    private List<LibraryMember> memberList = new ArrayList<>();

    /**
     * Executes the given command
     * @param line String that has the command
     */
    public void executeCommand(String line) {
        // Splits the line, matches the actual command with the necessary method and gives the proper arguments
        String[] args = line.split("\t");
        String command = args[0];
        switch (command) {
            case "addBook":
                addBook(args[1]);
                break;
            case "addMember":
                addMember(args[1]);
                break;
            case "borrowBook":
                borrowBook(args[1], args[2], args[3]);
                break;
            case "returnBook":
                returnBook(args[1], args[2], args[3]);
                break;
            case "extendBook":
                extendBook(args[1], args[2], args[3]);
                break;
            case "readInLibrary":
                readInLibrary(args[1], args[2], args[3]);
                break;
            case "getTheHistory":
                getTheHistory();
                break;
        }
    }
    // Since ids are one larges than indices, necessary adjustments made for the methods to work

    private void addBook(String bookType) {
        int id = getBookList().size()+1;
        addBookList(new Book(bookType, id));
        addOutputStr(String.format("Created new book: %s [id: %d]\n",
                (bookType.equals("P") ? "Printed" : "Handwritten"), id));
    }

    private void addMember(String memberType) {
        int id = getMemberList().size()+1;
        if (memberType.equals("S")) {
            addMemberList(new Student(id));
        }
        else if (memberType.equals("A")) {
            addMemberList((new Academic(id)));
        }
        addOutputStr(String.format("Created new member: %s [id: %d]\n",
                (memberType.equals("S") ? "Student" : "Academic"), id));
    }

    private void borrowBook(String bookId, String memberId, String date) {
        LibraryMember member = getMemberList().get(Integer.parseInt(memberId)-1);
        Book book = getBookList().get(Integer.parseInt(bookId)-1);
        LocalDate borrowDate = LocalDate.parse(date);
        if (book.getBookType().equals("H")) {
            addOutputStr("You cannot borrow this book\n");
        }
        else if (member.getBookLimit() == 0) {
            addOutputStr("You have exceeded the borrowing limit!\n");
        }
        else {
            book.borrow(member, borrowDate);
            addOutputStr(String.format("The book [%d] was borrowed by member [%d] at %s\n",
                    book.getId(), member.getId(), borrowDate));
        }
    }

    private void returnBook(String bookId, String memberId, String date) {
        LibraryMember member = getMemberList().get(Integer.parseInt(memberId)-1);
        Book book = getBookList().get(Integer.parseInt(bookId)-1);
        LocalDate returnDate = LocalDate.parse(date);
        // To calculate Fee, a ternary operator is used
        // If the return date is before or equal to the max return date it will return 0
        // Else it calculates the days between and returns that
        addOutputStr(String.format("The book [%d] was returned by member [%d] at %s Fee: %d\n",
                book.getId(), member.getId(), returnDate,
                ((book.getReturnDate().isAfter(returnDate) || book.getReturnDate().isEqual(returnDate))
                        ? 0 : book.getReturnDate().until(returnDate, ChronoUnit.DAYS))));
        book._return(member);
    }

    private void extendBook(String bookId, String memberId, String date) {
        LibraryMember member = getMemberList().get(Integer.parseInt(memberId)-1);
        Book book = getBookList().get(Integer.parseInt(bookId)-1);
        LocalDate currentDate = LocalDate.parse(date);
        if (book.isExtendable()) {
            book.extend(member);
            addOutputStr(String.format("The deadline of book [%d] was extended by member [%d] at %s\n",
                    book.getId(), member.getId(), currentDate));
            addOutputStr(String.format("New deadline of book [%d] is %s\n",
                    book.getId(), book.getReturnDate()));
        }
        else {
            addOutputStr("You cannot extend the deadline!\n");
        }
    }

    private void readInLibrary(String bookId, String memberId, String date) {
        LibraryMember member = getMemberList().get(Integer.parseInt(memberId)-1);
        Book book = getBookList().get(Integer.parseInt(bookId)-1);
        LocalDate currentDate = LocalDate.parse(date);
        if (member instanceof Student && book.getBookType().equals("H")) {
            addOutputStr("Students can not read handwritten books!\n");
        }
        else if (book.isBorrowed()) {
            addOutputStr("You can not read this book!\n");
        }
        else {
            book.read(member, currentDate);
            addOutputStr(String.format("The book [%d] was read in library by member [%d] at %s\n",
                    book.getId(), member.getId(), currentDate));
        }
    }

    private void getTheHistory() {
        // Defining and filling the necessary lists
        List<LibraryMember> students = new ArrayList<>();
        List<LibraryMember> academics = new ArrayList<>();
        List<Book> printedBooks = new ArrayList<>();
        List<Book> handwrittenBooks = new ArrayList<>();
        List<Book> borrowedBooks = new ArrayList<>();
        List<Book> readBooks = new ArrayList<>();

        for (LibraryMember member : getMemberList()) {
            if (member instanceof Student) {
                students.add(member);
            }
            else {
                academics.add(member);
            }
        }

        for (Book book : getBookList()) {
            if (book.getBookType().equals("P")) {
                printedBooks.add(book);
            }
            else {
                handwrittenBooks.add(book);
            }

            if (book.isBorrowed() && !book.isBeingRead()) {
                borrowedBooks.add(book);
            }

            if (book.isBeingRead()) {
                readBooks.add(book);
            }
        }

        // Writing to the file in specified format
        addOutputStr("History of library:\n\n");
        addOutputStr(String.format("Number of students: %d\n", students.size()));
        for (LibraryMember member : students) {
            addOutputStr(String.format("Student [id: %d]\n", member.getId()));
        }
        addOutputStr(String.format("\nNumber of academics: %d\n", academics.size()));
        for (LibraryMember member : academics) {
            addOutputStr(String.format("Academic [id: %d]\n", member.getId()));
        }
        addOutputStr(String.format("\nNumber of printed books: %d\n", printedBooks.size()));
        for (Book book : printedBooks) {
            addOutputStr(String.format("Printed [id: %d]\n", book.getId()));
        }
        addOutputStr(String.format("\nNumber of handwritten books: %d\n", handwrittenBooks.size()));
        for (Book book : handwrittenBooks) {
            addOutputStr(String.format("Handwritten [id: %d]\n", book.getId()));
        }
        addOutputStr(String.format("\nNumber of borrowed books: %d\n", borrowedBooks.size()));
        for (Book book : borrowedBooks) {
            addOutputStr(String.format("The book [%d] was borrowed by member [%d] at %s\n",
                    book.getId(), book.getBorrower().getId(), book.getBorrowDate()));
        }
        addOutputStr(String.format("\nNumber of books read in library: %d\n", readBooks.size()));
        for (Book book : readBooks) {
            addOutputStr(String.format("The book [%d] was read in library by member [%d] at %s\n",
                    book.getId(), book.getBorrower().getId(), book.getBorrowDate()));
        }
    }


    public String getOutputStr() {
        return outputStr;
    }

    public void addOutputStr(String s) {
        outputStr += s;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBookList(Book book) {
        this.bookList.add(book);
    }

    public List<LibraryMember> getMemberList() {
        return memberList;
    }

    public void addMemberList(LibraryMember member) {
        this.memberList.add(member);
    }

}
