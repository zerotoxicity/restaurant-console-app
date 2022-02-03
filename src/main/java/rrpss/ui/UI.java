package rrpss.ui;

import rrpss.error.InputError;
import rrpss.ui.app.Navigable;
import rrpss.util.validation.Predicate;

import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *  Class containing scanner in functions for different data types
 *  <br>
 *  Functions contain error handling
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public abstract class UI implements View {


    final protected Console console = System.console();
    final protected Scanner scanner = new Scanner(System.in);
    final protected DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final protected DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk:mm");
    final protected DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/M/yyyy kk:mm");

    /**
     * Display boundary classes
     */
    public abstract void display() ;

    /**
     * Display boundary classes with error handling
     * @throws Navigable
     * throwing Navigable allows breaking of lifecycle loops
     * Can be used in navigating out of a page
     */
    public void displayN() throws Navigable {};

    /**
     * Prints a cursor
     */
    private void cursor() {
        System.out.print("> ");
    }

    /**
     * promptInt provides a stdin interface for integers
     * @return intput int
     */
    protected int promptInt() {
        return _promptInt(false);
    }

    /**
     * promptInt provides a stdin interface for integers with predicate
     * @param message print message
     * @param predicate validation
     * @return input int
     */
    protected int promptInt(String message, Predicate<Integer> predicate) {
        print(message);
        return _promptInt(false, predicate);
    }

    /**
     * Prints a message and provides a stdin interface for integers
     * @param message print message
     * @return input int
     */
    protected int promptInt(String message) {
        print(message);
        return _promptInt(false);
    }


    /**
     * promptInt provides a stdin interface for integers
     *
     * @param caret when true, shows the > icon on a new line
     * @return int from user's input
     */
    protected int promptInt(boolean caret) {
        return _promptInt(caret);
    }

    /**
     * promptInt provides a stdin interface for integers with constraint on min and max values
     *
     * @param min sets a constraint on the minimum value for integers
     * @param max sets a constraint on the maximum value for integers
     * @return int from user's input
     */
    protected int promptInt(int min, int max) {
        return _promptInt(false, min, max, null);
    }

    /**
     * Prints a message and provides a stdin interface for integers with constraint on min and max values
     * @param message print message
     * @param min min value of stdin
     * @param max max value of stdin
     * @return int
     */
    protected int promptInt(String message, int min, int max) {
        print(message);
        return _promptInt(false, min, max, null);
    }

    /**
     * Provides a stdin interface for integers with constraint on min and max values
     * @param min min value of stdin
     * @param max max value of stdin
     * @param predicate validation for input
     * @return int
     */
    protected int promptInt(int min, int max, Predicate<Integer> predicate) {
        return _promptInt(false, min, max, predicate);
    }


    /**
     * Provides a stdin interface for integers with constraint on min and max values
     * @param min min value of stdin
     * @param max max value of stdin
     * @param predicate validation for input
     * @return int
     */
    protected int promptInt(String message, int min, int max, Predicate<Integer> predicate) {
        print(message);
        return _promptInt(false, min, max, predicate);
    }

    /**
     * Provides a stdin interface for integers with constraint on min and max values
     * @param caret option to display breaking caret
     * @param min min value of std in
     * @param max max value of std out
     * @return int
     */
    protected int promptInt(boolean caret, int min, int max) {
        return _promptInt(caret, min, max, null);
    }

    /**
     * Prints a cursor and provides a stdin interface for integers
     * @param caret option to display breaking caret
     * @return int
     */
    private int _promptInt(boolean caret) {
        return _promptInt(true, null);
    }

    /**
     * Provides a stdin interface for integers
     * @param caret option to display breaking caret
     * @param predicate validation for input
     * @return int
     */
    private int _promptInt(boolean caret,  Predicate<Integer> predicate) {
        int i;
        while (true) {
            try {
                if (caret) cursor();
                i = scanner.nextInt();
                if (predicate != null && !predicate.val(i).isEmpty())
                    throw new InputError(predicate.val(i));
                break;
            } catch (InputError input) {
                println(input.getMessage());
            } catch (java.util.InputMismatchException e) {
                println("Invalid type entry, try again");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return i;
    }

    /**
     * Provides a stdin interface for integers, with constraints
     * @param caret
     * @param min
     * @param max
     * @param predicate
     * @return int
     */
    private int _promptInt(boolean caret, int min, int max, Predicate<Integer> predicate) {
        int i;
        while (true) {
            try {
                if (caret) cursor();
                i = scanner.nextInt();
                if (i < min || i > max)
                    throw new InputError(String.format("Values must be within %d and %d", min, max));
                if (predicate != null && !predicate.val(i).isEmpty())
                    throw new InputError(predicate.val(i));
                // If input has no errors, exit loop
                break;
            } catch (InputError input) {
                println(input.getMessage());
            } catch (java.util.InputMismatchException e) {
                println("Invalid type entry, try again");
                scanner.nextLine();
            }
            caret = true;
        }
        scanner.nextLine();
        return i;
    }

    /**
     * Provides a stdin interface for double
     * @return  double
     */
    protected double promptDouble() {
        return _promptDouble(false);
    }

    /**
     * Provides a stdin interface for double
     * @param caret if true, prints a cursor
     * @return double
     */
    protected double promptDouble(boolean caret) {
        return _promptDouble(caret);
    }

    /**
     * Provides a stdin interface for double with constraint on min and max value
     * @param min
     * @param max
     * @return double
     */
    protected double promptDouble(double min, double max) {
        return _promptDouble(false, min, max);
    }

    /**
     * Prints a cursor and provides a stdin interface for double with constraint on min and max value
     * @param caret if true, prints a cursor
     * @param min
     * @param max
     * @return double
     */
    protected double promptDouble(boolean caret, double min, double max) {
        return _promptDouble(caret, min, max);
    }

    /**
     * Prints a cursor and provides a stdin interface for double
     * @param caret
     * @return double
     */
    protected double _promptDouble(boolean caret) {
        if (caret) cursor();
        return scanner.nextDouble();
    }

    /**
     * Prints a cursor and provides a stdin interface for double with constraint on min and max value
     * @param caret if true, prints a cursor
     * @param min
     * @param max
     * @return double
     */
    private double _promptDouble(boolean caret, double min, double max) {
        double d;
        while (true) {
            try {
                if (caret) cursor();
                d = Math.round(scanner.nextDouble()*100.0)/100.0;
                if (d < min || d > max)
                    throw new InputError(String.format("Values must be within %.2f and %.2f", min, max));
                break;
            } catch (InputError input) {
                println(input.getMessage());
            } catch (java.util.InputMismatchException e) {
                println("Invalid type entry, try again");
                scanner.nextLine();
            }
            caret = true;
        }
        scanner.nextLine();
        return d;
    }

    /**
     * Print the message and a line then prompt user to input a String
     * @param message
     * @return String
     */
    protected String promptStrLn(String message) {
        println(message);
        return _promptStr(false, null);
    }

    /**
     * Prints the message then prompt user to input a String
     * @param message message to be printed before stdin interface
     * @return  String
     */
    protected String promptStr(String message) {
        print(message);
        return _promptStr(false, null);
    }

    /**
     * Print a message then a cursor and prompt the user to input a String
     * @param message
     * @param caret if true, prints a cursor
     * @return String
     */
    protected String promptStr(String message, boolean caret) {
        print(message);
        return _promptStr(caret, null);
    }

    /**
     * Print a message then a cursor and prompt the user to input a String with predicate
     * @param message
     * @param caret
     * @param predicate
     * @return String
     */
    protected String promptStr(String message, boolean caret, Predicate<String> predicate) {
        print(message);
        return _promptStr(caret, predicate);
    }

    /**
     * Print a message then prompt the user to input a String with predicate
     * @param message
     * @param predicate
     * @return String
     */
    protected String promptStr(String message, Predicate<String> predicate) {
        print(message);
        return _promptStr(false, predicate);
    }

    /**
     * Prints a cursor and prompt user to input a String
     * @param caret if true, print cursor
     * @return String
     */
    protected String promptStr(boolean caret) {
        return _promptStr(true, null);
    }

    /**
     * Prompt user to input a String
     * @return String
     */
    protected String promptStr() {
        return _promptStr(false, null);
    }

    /**
     * Prompt user to input a String with predicate
     * @param predicate
     * @return String
     */
    protected String promptStr(Predicate<String> predicate) {
        return _promptStr(false, predicate);
    }

    /**
     * Prints a cursor and prompt user to input a string with predicate
     * @param caret if true, prints a cursor
     * @param predicate
     * @return String
     */
    protected String promptStr(boolean caret, Predicate<String> predicate) {
        return _promptStr(caret, predicate);
    }

    /**
     * Prompt user for string
     * @param caret if true, prints a cursor
     * @param predicate
     * @return String
     */
    private String _promptStr(boolean caret, Predicate predicate) {
        String str;
        while (true) {
            try {
                if (caret) cursor();
                str = scanner.nextLine();
                if (predicate != null && predicate.val(str) != null && !predicate.val(str).isEmpty())
                    throw new InputError(predicate.val(str));
                break;
            } catch (java.util.InputMismatchException e) {
                println("Invalid entry");
            } catch (InputError e) {
                println(e.getMessage());
            }
            caret = true;
        }
        return str;
    }

    /**
     * Prompt user for date only
     * @param predicate
     * @return LocalDate
     */
    protected LocalDate promptDateOnly(Predicate<LocalDate> predicate) {
        return _promptDateOnly(predicate);
    }

    /**
     * Prompt user for date without predicate
     * @return  LocalDate
     */
    protected LocalDate promptDateOnly() {
        return _promptDateOnly(null);
    }

    /**
     * Prompt user for date
     * @param predicate
     * @return LocalDate
     */
    private LocalDate _promptDateOnly(Predicate<LocalDate> predicate) {
        printf("Enter Date in such format DD/MM/YYYY I.E. %s\n", LocalDateTime.now().format(dateFormat));
        while(true) {
            try {
                cursor();
                String text = promptStr();
                // Test pattern
                LocalDate date = LocalDate.parse(text, dateFormat);
                if (predicate != null && predicate.val(date) != null && !predicate.val(date).isEmpty())
                    throw new InputError(predicate.val(date));
                return date;
            } catch (DateTimeParseException e) {
                println("Invalid format try again");
            } catch (InputError e) {
                println(e.getMessage());
            }
        }
    }

    /**
     * Prompt user for date only
     * @param predicate
     * @return LocalDate
     */
    protected LocalTime promptTimeOnly(Predicate<LocalTime> predicate) {
        return _promptTimeOnly(predicate);
    }

    /**
     * Prompt user for date without predicate
     * @return  LocalDate
     */
    protected LocalTime promptTimeOnly() {
        return _promptTimeOnly(null);
    }


    /**
     * Prompt user for time
     * @param predicate
     * @return LocalDate
     */
    private LocalTime _promptTimeOnly(Predicate<LocalTime> predicate) {
        printf("Enter Time in such format 14:30 I.E. %s\n", LocalDateTime.now().format(timeFormat));
        while(true) {
            try {
                cursor();
                String text = promptStr();
                // Test pattern
                LocalTime time = LocalTime.parse(text, timeFormat);
                if (predicate != null && predicate.val(time) != null && !predicate.val(time).isEmpty())
                    throw new InputError(predicate.val(time));
                return time;
            } catch (DateTimeParseException e) {
                println("Invalid format try again");
            } catch (InputError e) {
                println(e.getMessage());
            }
        }
    }

    /**
     * Prompt user for datetime, with predicate
     * @param predicate
     * @return LocalDateTime
     */
    protected LocalDateTime promptDateTime(Predicate<LocalDateTime> predicate) {
        return _promptDateTime(predicate);
    }

    /**
     * Prompt user for datetime without predicate
     * @return  LocalDateTime
     */
    protected LocalDateTime promptDateTime() {
        return _promptDateTime(null);
    }

    /**
     * Prompt user for datetime
     * @param predicate
     * @return LocalDateTime
     */
    private LocalDateTime _promptDateTime(Predicate<LocalDateTime> predicate) {
        printf("Enter DateTime in such format DD/MM/YYYY kk:mm I.E. %s\n", LocalDateTime.now().format(dateTimeFormatter));
        while(true) {
            try {
                cursor();
                String text = promptStr();
                // Test pattern
                LocalDateTime date = LocalDateTime.parse(text, dateTimeFormatter);
                if (predicate != null && predicate.val(date) != null && !predicate.val(date).isEmpty())
                    throw new InputError(predicate.val(date));
                return date;
            } catch (DateTimeParseException e) {
                println("Invalid format try again");
            } catch (InputError e) {
                println(e.getMessage());
            }
        }
    }

    /**
     * promptInt provides a stdin interface for censored String
     * @return  string
     */
    protected String promptHidden() {
        if (console == null) return promptStr();
        return _promptHidden(false).toString();
    }

    /**
     * Prompt user for a string and censor it
     * @param caret
     * @return
     */
    protected char[] promptHidden(boolean caret) {
        return _promptHidden(caret);
    }

    /**
     * Prompt user for a string and censor it
     * @param caret if true, print a cursor
     * @return
     */
    private char[] _promptHidden(boolean caret) {
        if (caret) cursor();
        return console.readPassword();
    }

    /**
     * Print message
     * @param message message to be printed
     */
    protected void print(String message) {
        System.out.print(message);
    }

    protected void println() {
        System.out.println("");
    }

    /**
     * Print message and a new line
     * @param message message to be printed
     */
    protected void println(String message) {
        System.out.println(message);
    }

    /**
     * Print message with printf
     * @param message
     * @param args
     */
    protected void printf(String message, Object... args) {
        System.out.printf(message, args);
    }

    /**
     * Print message with title heading
     * @param message
     */
    protected void title(String message) {
        System.out.println("\n---------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------");
    }

    /**
     * Print message with title heading in printf
     * @param message
     * @param args
     */
    protected void title(String message, Object... args) {
        System.out.println("\n---------------------------------------------------");
        System.out.printf(message, args);
        System.out.println("\n---------------------------------------------------");
    }

    /**
     * Print message with medium title heading
     * @param message
     */
    protected void mediumTitle(String message) {
        System.out.println("\n------------------------");
        System.out.println(message);
        System.out.println("------------------------");
    }

    /**
     * Print message with medium title heading in printf
     * @param message
     * @param args
     */
    protected void mediumTitle(String message, Object... args) {
        System.out.println("\n------------------------");
        System.out.printf(message, args);
        System.out.println("\n------------------------");
    }

    /**
     * Print message with subtitle heading
     * @param message
     */
    protected void subtitle(String message) {
        System.out.println(String.format("\n[%s]", message));
    }

    /**
     * Print message and prompt user to input a date
     * @param message
     * @return date
     */
    protected LocalDate promptDate(String message) {
        return _promptDate(message,true);
    }



    /**
     *  Prompt user for LocalDate
     * @param message message to be printed to user
     * @param year if year==true, inputted year cannot be earlier than current year
     * @return date
     */
    private LocalDate _promptDate(String message,boolean year) {
        String date;
        println(message);
        while (true) {
            try {
                cursor();
                date = promptStr();
                int count = 0;
                for (int i = 0; i < date.length(); i++) {
                    if ('/' == date.charAt(i)) count++;
                }
                LocalDate now = LocalDate.now();
                String[] nowString = now.format(dateFormat).split("/");
                if (count != 2) throw new InputError("Invalid date, date format: DD/MM/YYYY");
                else {
                    String dateSplit[] = date.split("/");
                    if (dateSplit.length != 3) throw new InputError("Invalid date, date format: dd/mm/yyyy");
                    else if (dateSplit[0].length() != 2 || dateSplit[1].length() != 2 || dateSplit[2].length() != 4)
                        throw new InputError("Invalid date, date format: dd/mm/yyyy");
                    else if (Integer.valueOf(dateSplit[0]) < 1 || Integer.valueOf(dateSplit[0]) > 31) {
                        throw new InputError(String.format("Day must be within %d and %d", 1, 31));
                    } else if (Integer.valueOf(dateSplit[1]) < 1 || Integer.valueOf(dateSplit[1]) > 12) {
                        throw new InputError(String.format("Month must be within %d and %d", 1, 12));
                    }else if (year && Integer.valueOf(dateSplit[2]) < Integer.valueOf(nowString[2]) ) {
                        throw new InputError(String.format("Year cannot be earlier than current year"));
                    }
                }
                break;
            } catch (InputError input) {
                println(input.getMessage());
            } catch (java.util.InputMismatchException e) {
                println("Invalid type entry, try again");
            }
        }
        return LocalDate.parse(date, dateFormat);
    }

    /**
     * Prompt user for a month and year
     * @return  MonthYear
     */
    protected YearMonth promptMonthYear(){
        String[] dateSplit;
        int count =0;
        while(true) {
            try {
                cursor();
                String date = promptStr();
                for (int i = 0; i < date.length(); i++) {
                    if ('/' == date.charAt(i)) count++;
                }
                dateSplit = date.split("/");

                if (count != 1) throw new InputError("Invalid month, date format: mm/yyyy");
                else if (Integer.valueOf(dateSplit[0]) < 1 || Integer.valueOf(dateSplit[0]) > 12) {
                    throw new InputError(String.format("Month must be within %d and %d", 1, 12));
                } else if (dateSplit[1].length() != 4) {
                    throw new InputError(String.format("Invalid year, date format: mm/yyyy"));
                }
                break;
            } catch (InputError inputError) {
                println(inputError.getMessage());
            } catch (java.util.InputMismatchException e) {
                println("Invalid type entry, try again");
            }

        }
        return YearMonth.of(Integer.valueOf(dateSplit[1]),Integer.valueOf(dateSplit[0]));
    }
}