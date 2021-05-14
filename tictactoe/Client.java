import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.sql.*;


public class Client {

    public static void main(String[] args) {

        try {
            int choice = -1;
            boolean logIn = false;
            String host = "localhost";
            Registry registry = LocateRegistry.getRegistry(host);
            ITicTacToe game = (ITicTacToe) registry.lookup("TicTacToe");
            Scanner stdin = new Scanner(System.in);
            
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("Choice: ");
            choice = stdin.nextInt();
            // Register player on the remote server
            if (choice == 1) {
                System.out.println("Username: ");
                stdin.nextLine();
                String username = stdin.nextLine();
                System.out.println("Password: ");
                String password = stdin.nextLine();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:5042/database1";
                    Connection con = DriverManager.getConnection(url, 
                                                                 "user1", 
                                                                 "123456789");
                    
                    PreparedStatement statement = con.prepareStatement("INSERT INTO player(username, password) VALUES(?,?)");
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.executeUpdate();
                    statement.close();
                    con.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if(choice == 2) {
                System.out.println("Username: ");
                stdin.nextLine();
                String username = stdin.nextLine();
                System.out.println("Password");
                String password = stdin.nextLine();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:5042/database1";
                    Connection con = DriverManager.getConnection(url, 
                                                                 "user1", 
                                                                 "123456789");
                    
                    PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) AS valid FROM player WHERE username=? AND password=?");
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();
                    while(rs.next()) {
                        int valid = rs.getInt(1);
                        if(valid == 1) {
                            logIn = true;
                        }
                    }
                    statement.close();
                    con.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if(logIn) {
                    int id = game.addPlayer(username);

                    if (id == -1) {
                        System.err.println("Username already in use !");
                        System.exit(1);
                    }
    
                    if (id == -2) {
                        System.err.println("Maximum number of server players exceeded !");
                        System.exit(1);
                    }
    
                    int hasMatch = game.hasMatch(id);
    
                    System.out.println("Looking for match  ...");
    
                    // Checks if there are any active matches
                    while (hasMatch != 1 && hasMatch != 2) {
                        if (hasMatch == -2) {
                            System.err.println("Timeout !");
                            System.exit(1);
                        }
    
                        if (hasMatch == -1) {
                                System.err.println("Server error !");
                            System.exit(1);
                        }
    
                        // Check every 1 second
                        Thread.sleep(1000);
                        hasMatch = game.hasMatch(id);
                    }
    
                        System.out.println("Second player  " + game.getOpponent(id) + " came in ....");
    
                    int isMyTurn;
                    String message = null;
    
                    // Game Loop
                    while (true) {
    
                        // Checks if it's that player's turn
                        isMyTurn = game.isMyTurn(id);
    
                        if (isMyTurn == -2) {
                            System.err.println("There are no two players in this match !");
                            game.endMatch(id);
                            System.exit(1);
                        }
    
                        if (isMyTurn == -1) {
                            System.err.println("Server error !");
                            game.endMatch(id);
                            System.exit(1);
                        }
    
                        switch (isMyTurn) {
                            case 2:
                                    message = "You won!";
                                break;
                            case 3:
                                message = "You lost";
                                break;
                            case 4:
                                message = "Tie!";
                                break;
                            case 5:
                                message = "You won by WO !";
                                break;
                            case 6:
                                message = "You lost by WO !";
                                break;
                        }
    
                        // End of game, displays result and closes
                        if (isMyTurn > 1 && isMyTurn < 7) {
                            System.out.println(message);
    
                            if (game.endMatch(id) == -1) {
                                System.err.println("Error when ending game !");
                                System.exit(1);
                            } else {
                                System.out.println("Game over !");
                                System.exit(0);
                            }
                        }
    
                        // You can only move the pieces on the board
                        int ret_movePiece = -1;
    
                        while ((ret_movePiece != 1) && (ret_movePiece != -3) && (isMyTurn == 1)) {
    
                            System.out.println(game.getBoard(id));
    
                            System.out.println("Enter the position of the part to be moved. ");
                            System.out.print("Move : ");
                            int line = stdin.nextInt();
    
                            System.out.print("Column: ");
                            int column = stdin.nextInt();
    
                            ret_movePiece = game.move(id, line, column);
    
                            switch (ret_movePiece) {
                                case 2:
                                    System.out.println("You lost !");
                                    game.endMatch(id);
                                    System.exit(0);
                                case 1:
                                    System.out.println("Successfully completed play ");
                                    System.out.println(game.getBoard(id));
                                    break;
                                case 0:
                                    System.out.println("Invalid position !");
                                    break;
                                case -1:
                                    System.out.println("Invalid parameters !");
                                    break;
                                case -2:
                                    System.err.println("There are no two players in this match !");
                                    game.endMatch(id);
                                    System.exit(1);
                                case -3:
                                    System.out.println("Invalid parameters !");
                                    break;
                                case -4:
                                    System.out.println("It's not your turn to move !");
                                    break;
                            }
    
                        }
                    }
                }
                

            }
            
        } catch (Exception e) {
            System.err.println("TicTacToe client failed!");
            System.err.println(e.toString());
        }
    }
}