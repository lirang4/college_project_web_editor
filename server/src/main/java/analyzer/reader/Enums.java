package analyzer.reader;

public class Enums {
    public enum LineType
    {
        Var,
        If,
        EndIf,
        Else,
        EndElse,
        Do,
        While,
        EndWhile,
        For,
        EndFor,
        EndLoop,
        StartLoop,
        Put,      //Change Value Of Variable => x=6, x=x+6, ...
        Return,
        ReturnValue,
    }



}
