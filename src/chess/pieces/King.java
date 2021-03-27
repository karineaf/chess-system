package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean canMove(Position position){
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null  || piece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position){
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position auxPosition = new Position(0,0);

        //above
        auxPosition.setValues(position.getRow() - 1, position.getColumn());
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //below
        auxPosition.setValues(position.getRow() + 1, position.getColumn());
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //left
        auxPosition.setValues(position.getRow(), position.getColumn() - 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //right
        auxPosition.setValues(position.getRow(), position.getColumn() + 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //north west
        auxPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //north east
        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //south weast
        auxPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //south east
        auxPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //special move castiling
        if(getMoveCount() == 0 && !chessMatch.isCheck()){
            //castling kingside rook
            Position postT1 = new Position(position.getRow(), position.getColumn() + 3);
            if(testRookCastling(postT1)){
                Position position1 = new Position(position.getRow(), position.getColumn() + 1);
                Position position2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null)
                    matriz[position.getRow()][position.getColumn() + 2] = true;
            }

            //castling queenside rook
            Position postT2 = new Position(position.getRow(), position.getColumn() -4);
            if(testRookCastling(postT2)){
                Position position1 = new Position(position.getRow(), position.getColumn() - 1);
                Position position2 = new Position(position.getRow(), position.getColumn() - 2);
                Position position3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null
                        && getBoard().piece(position3) == null)
                    matriz[position.getRow()][position.getColumn() - 2] = true;
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "K";
    }
}
