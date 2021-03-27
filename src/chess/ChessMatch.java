package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Integer turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces = new ArrayList<>();


    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnTheBoard = new ArrayList<>();
        check = false;
        initialSetup();
    }

    public Integer getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matriz = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++)
            for (int j = 0; j < board.getColumns(); j++) {
                matriz[i][j] = (ChessPiece) board.piece(i, j);
            }
        return matriz;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validadeSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validadeSourcePosition(source);
        validadeTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer)))
            checkMate = true;

        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target){
        ChessPiece piece = (ChessPiece) board.removePiece(source);
        piece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);

         if (capturedPiece != null){
             piecesOnTheBoard.remove(capturedPiece);
             capturedPieces.add(capturedPiece);
         }

        board.placePiece(piece, target);
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece piece = (ChessPiece) board.removePiece(target);
        piece.decreaseMoveCount();
        board.placePiece(piece, source);

        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE)? Color.BLACK : Color.WHITE;
    }

    private void validadeSourcePosition(Position position) {
        if (!board.thereIsAPiece(position))
            throw new ChessException("There is no piece on source position");

        if(currentPlayer!=((ChessPiece) board.piece(position)).getColor())
            throw new ChessException("The chosen piece is not yours");

        if(!board.piece(position).isThereAnyPossibleMove())
            throw new ChessException("There is no possible moves for the chosen piece");
    }

    private void placeNewPiece(char column, Integer row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<Piece> pieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());

        for (Piece piece : pieces)
            if(piece instanceof King)
                return (ChessPiece) piece;

        throw new IllegalStateException("There is no " + color + " king on the board.");
    }

    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();

        List<Piece> oponnentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());

        for(Piece piece : oponnentPieces) {
            boolean[][] matriz = piece.possibleMoves();
            if (matriz[kingPosition.getRow()][kingPosition.getColumn()])
                return true;
        }
        return false;
    }

    private boolean testCheckMate(Color color){
        if (!testCheck(color))
            return false;

        List<Piece> pieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());

        for(Piece piece : pieces){
            boolean[][] matriz = piece.possibleMoves();
            for(int i=0; i<board.getRows(); i++)
                for(int j=0; j<board.getColumns(); j++)
                    if (matriz[i][j]){
                        Position source = ((ChessPiece) piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if(!testCheck)
                            return false;
                    }
        }
        return true;
    }

    private void validadeTargetPosition(Position source, Position target){
        if(!board.piece(source).possibleMove(target))
            throw new ChessException("The chosen piece can't move to targe");

    }

    private void initialSetup() {
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
    }
}
