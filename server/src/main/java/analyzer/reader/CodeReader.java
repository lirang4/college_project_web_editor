package analyzer.reader;

public class CodeReader {

    private int Position;
    private String code;
    private String[] lines;

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public CodeReader(String code)
    {
        this.Position = 0;
        this.code = code;
        this.lines = code.split("\\r?\\n");
    }

    public CodeLine ReadLine()
    {
        if (Position > lines.length -1 )
            return null;

        CodeLine line = new CodeLine(lines[Position],Position);
        Position++;
        return line;
    }
}
