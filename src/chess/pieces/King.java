package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position){
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null  || piece.getColor() != getColor();
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

        return matriz;
    }

    @Override
    public String toString() {
        return "K";
    }
}
