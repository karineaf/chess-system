package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxPosition = new Position(0, 0);

        //north weast
        auxPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setValues(auxPosition.getRow() -1, auxPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //north east
        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setValues(auxPosition.getRow() - 1, auxPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //south east
        auxPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setValues(auxPosition.getRow() + 1, auxPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        //south weast
        auxPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setValues(auxPosition.getRow() + 1, auxPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition))
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;

        return matriz;
    }

    @Override
    public String toString() {
        return "B";
    }
}
