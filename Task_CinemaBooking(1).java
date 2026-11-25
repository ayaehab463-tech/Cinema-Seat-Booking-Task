/*
    =======================================================================
    TASK: Cinema Seat Booking  -  a HashMap with your own class as the key
    =======================================================================

    A cinema hall has rows A to E, with seats 1 to 6 in each row.
    You will store the bookings in a HashMap:

        key   = Seat     (a class you finish below, for example B5)
        value = String   (the name of the customer who booked it)

    STEP 1  Complete Parts A to G in main(). Each part says what to print.
    STEP 2  Run the program BEFORE writing equals() and hashCode().
            Some results will be wrong. That is expected - write down which ones.
    STEP 3  Write equals() and hashCode() in the Seat class.
    STEP 4  Run it again and compare with the EXPECTED OUTPUT below.
    STEP 5  Answer the questions at the bottom of this file.

    RULES
    - Every time you use a seat, create a NEW object: new Seat('B', 5).
        A real program works the same way: when a customer types "B5",
        the program makes a new Seat object for it.
    - Do not change printChart().

    EXPECTED OUTPUT (after STEP 3)
    The order inside { } may be different in your output. HashMap has no order.

    Bookings:           {A1=Ali, C3=Mona, A2=Sara, B5=Omar}
    Who booked C3:      Mona
    Is A2 taken:        true
    Is E6 taken:        false
    Youssef -> B5:      refused, taken by Omar
    After Ali moved:    {C3=Mona, A2=Sara, B5=Omar, D2=Ali}
    Hana -> E1:         refused, seat is broken
    Seats booked:       4

        1  2  3  4  5  6
    A    .  X  .  .  .  .
    B    .  .  .  .  X  .
    C    .  .  X  .  .  .
    D    .  X  .  .  .  .
    E    #  #  .  .  .  .
*/
import java.util.*;
class Task_CinemaBooking {

    // A seat in the cinema hall, for example B5 = row B, seat number 5.
    // static so that main() can create Seat objects directly.
    static class Seat {
        final char row;     // 'A' to 'E'
        final int number;   // 1 to 6

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        // TODO 1: override equals()
        //   Two Seat objects are the same seat when the row AND the number are the same.
        //   The parameter must be of type Object, not Seat. Write @Override above it.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Seat)) return false;
            Seat other = (Seat) o;
            return this.row == other.row && this.number == other.number;
        }

        // TODO 2: override hashCode()
        //   Equal seats must return the same number.
        //   Use the same fields you used in equals().
        @Override
        public int hashCode() {
            return Objects.hash(row, number);
        }
    }

    public static void main(String[] args) {
        
        // Key = Seat, Value = name of the customer who booked it
        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A: book these seats using put()
        //   A1 -> Ali     A2 -> Sara     B5 -> Omar     C3 -> Mona
        // Print:  Bookings:           <the map>
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");
        System.out.println("Bookings:           " + bookings);

        // Part B: who booked seat C3?  Use get().
        // Print:  Who booked C3:      <name>
        String c3Booker = bookings.get(new Seat('C', 3));
        System.out.println("Who booked C3:      " + c3Booker);

        // Part C: are seats A2 and E6 taken?  Use containsKey().
        // Print:  Is A2 taken:        <true or false>
        //         Is E6 taken:        <true or false>
        System.out.println("Is A2 taken:        " + bookings.containsKey(new Seat('A', 2)));
        System.out.println("Is E6 taken:        " + bookings.containsKey(new Seat('E', 6)));

        // Part D: Youssef wants seat B5. Use putIfAbsent(), so a booked seat is not replaced.
        //   putIfAbsent() returns null if the seat was free,
        //   otherwise it returns the name that is already there.
        // Print:  Youssef -> B5:      booked
        //    or:  Youssef -> B5:      refused, taken by <name>
        String existingB5 = bookings.putIfAbsent(new Seat('B', 5), "Youssef");
        if (existingB5 == null) {
            System.out.println("Youssef -> B5:      booked");
        } else {
            System.out.println("Youssef -> B5:      refused, taken by " + existingB5);
        }

        // Part E: Ali moves from seat A1 to seat D2.  Use remove() and put().
        // Print:  After Ali moved:    <the map>
        bookings.remove(new Seat('A', 1));
        bookings.put(new Seat('D', 2), "Ali");
        System.out.println("After Ali moved:    " + bookings);

        // Part F: seats E1 and E2 are broken. Add them to the HashSet "broken".
        //   Hana wants seat E1. Book it only if it is not broken.  Use contains().
        // Print:  Hana -> E1:         booked
        //    or:  Hana -> E1:         refused, seat is broken
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));

        Seat hanaSeat = new Seat('E', 1);
        if (!broken.contains(hanaSeat)) {
            bookings.put(hanaSeat, "Hana");
            System.out.println("Hana -> E1:         booked");
        } else {
            System.out.println("Hana -> E1:         refused, seat is broken");
        }

        // Part G: how many seats are booked?  Use size().
        // Print:  Seats booked:       <number>
        System.out.println("Seats booked:       " + bookings.size());

        // GIVEN - prints the seating chart
        System.out.println();
        printChart(bookings, broken);
    }

    // GIVEN - do not change.  Prints the hall:  X = booked   # = broken   . = free
    static void printChart(HashMap<Seat, String> bookings, HashSet<Seat> broken) {
        System.out.println("     1  2  3  4  5  6");
        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + "  ");
            for (int number = 1; number <= 6; number++) {
                Seat seat = new Seat(row, number);
                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }
            System.out.println();
        }
    }
}


/*
    QUESTIONS - write your answers here


    --------------------------------------------------------------------

    Q1.Why did we have to override hashcode() and equals()?

    --> Because the program creates a new seat object every time it 
        performes a lookup, hashmap and hashset will treat two objects
        with the same row and number as different keys. So overriding
        hashcode() ensures equal seats map to the same bucket and 
        overriding equals() confirms they represent the same seats by
        comparing row and number.

    --------------------------------------------------------------------

    Q2. Delete your hashCode() but keep equals(), and run again.
        What happens? Why?

    --> Lookups fail : get(C3) returns null, containsKey(A2)
        returns false, Youssef is mistakenly allowed to book B5 
        (creating a duplicate B5 entry), remove(A1) fails to remove
        Ali's old seat, Hana is allowed to book broken seat E1, 
        size() becomes 7 instead of 4, and the seating chart prints 
        all dots (.).

        Why: HashMap and HashSet always call hashCode() first to locate
        the bucket. Without an overridden hashCode(), each new Seat() 
        produces a different default hash code and lands in a different 
        bucket, so equals() is never even called to compare it against 
        the existing seat. This breaks the contract that equal objects 
        must have the same hash code.
    
    --------------------------------------------------------------------

    Q3. Why are row and number final? What could go wrong if the number
        of a booked seat could be changed after it was put in the map?

    --> row and number are final so that Seat objects are immutable and
    their hashCode() never changes after being inserted into the map.
    If a seat's number were changed after putting it in the HashMap, 
    its hashCode() would change while the entry remains stuck in its 
    original bucket. Subsequent lookups using the new seat will search 
    the wrong bucket, and lookups using the old seat will fail equals(),
    making the booking effectively lost and impossible to retrieve or 
    remove by key.

    -------------------------------------------------------------------


    Q4. Change equals(Object o) to equals(Seat o). What happens?
        Then also remove @Override and run again. What happens now?

    --> With @Override: The program fails to compile with an error 
    because equals(Seat o) does not match the signature of Object.
    equals(Object o), so it is not actually overriding it.

    After removing @Override: The code compiles and runs, but all 
    lookups fail (producing the same broken output as if equals() 
    were never written). Because equals(Seat o) overloads the method 
    instead of overriding it, HashMap and HashSet still call Object.
    equals(Object) which checks memory references (==) and completely 
    ignore equals(Seat).

    --------------------------------------------------------------------

    BONUS. Replace the whole Seat class with this one line:
            record Seat(char row, int number) {}
        Run it. What happens, and why?

    --> The program compiles and all booking logic works correctly 
    (get, containsKey, putIfAbsent, remove, broken.contains, 
    size() == 4, and the seating chart all match the expected output). 
    The only visible difference is the printed format of the map: keys 
    print as Seat[row=A, number=1] instead of A1.
    
    Why: A Java record automatically generates private final fields, 
    a constructor, accessor methods, value-based equals() and hashCode()
    using all fields, and a default toString() method (which prints the 
    class name and field names unless you override it).
*/