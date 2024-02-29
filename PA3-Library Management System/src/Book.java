import java.time.LocalDate;

public class Book {

    private String bookType;
    private LibraryMember borrower;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean borrowed;
    private boolean beingRead;
    private int id;
    private boolean extendable = true;


    public Book(String bookType, int id) {
        setBookType(bookType);
        setId(id);
    }

    public void borrow(LibraryMember member, LocalDate date) {
        setBorrowed(true);
        setBorrower(member);
        setBorrowDate(date);
        // When a book is borrowed, lower the member's book limit by one
        member.setBookLimit(member.getBookLimit()-1);
        // Decide the book's max return date
        if (member instanceof Student) {
            setReturnDate(date.plusWeeks(1));
        }
        else {
            setReturnDate(date.plusWeeks(2));
        }
    }

    public void read(LibraryMember member, LocalDate date) {
        // Reading is essentially borrowing, but one more flag is added to identify if it's being read
        borrow(member, date);
        setBeingRead(true);
    }

    public void _return(LibraryMember member) {
        member.setBookLimit(member.getBookLimit()+1);
        // Set everything to its initial value
        setBorrowDate(null);
        setBorrowed(false);
        setBeingRead(false);
        setBorrower(null);
        setReturnDate(null);
        setExtendable(true);
    }

    public void extend(LibraryMember member) {
        setExtendable(false);
        if (member instanceof Student) {
            setReturnDate(getReturnDate().plusWeeks(1));
        }
        else {
            setReturnDate(getReturnDate().plusWeeks(2));
        }
    }


    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public LibraryMember getBorrower() {
        return borrower;
    }

    public void setBorrower(LibraryMember borrower) {
        this.borrower = borrower;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
    }

    public boolean isBeingRead() {
        return beingRead;
    }

    public void setBeingRead(boolean beingRead) {
        this.beingRead = beingRead;
    }
}
