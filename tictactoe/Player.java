public class Player {
  private int id;
  private String name;
  private long timestamp;
  private Match match;


  public Player(int id, String name) {
    this.id = id;
    this.name = name;
    this.match = null;
    this.timestamp = System.currentTimeMillis();
  }

  public int getId() { return this.id; }
  public String getName() { return this.name; }
  public Match getMatch() { return this.match; }
  public void setMatch(Match match) { this.match = match; }


  public void updateTimestamp() {
      this.timestamp = System.currentTimeMillis();
  }


  public boolean hasTimedOut() {
    return (System.currentTimeMillis() - this.timestamp) >= TicTacToe.TIMEOUT_MATCH * 1000;
  }
}
