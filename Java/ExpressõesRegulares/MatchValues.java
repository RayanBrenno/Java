public class MatchValues {
    private int id;
    private String text;
    private int positionStart;
    private int positionEnd;

    // Construtor
    public MatchValues(int id, String text, int positionStart, int positionEnd) {
        this.id = id;
        this.text = text;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPositionStart() {
        return positionStart;
    }

    public int getPositionEnd() {
        return positionEnd;
    }

    @Override
    public String toString() {
        return "{" + id + ", " + text + ", " + positionStart + ", " + positionEnd + "}";
    }
}
