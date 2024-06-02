package org.schlunzis.zis.math.linear;


/**
 * LSSolver, short for Linear System Solver, is a class that provides methods to solve systems of linear equations.
 * The systems need to be provided as matrices.
 *
 * @author JayPi4c
 * @since tbd
 */
public class LSSolver {

    /**
     * Dieser Code stammt von: <a href=
     * "https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     *
     * @param m the matrix to solve
     * @param v the result vector
     * @return the solution vector
     */
    public static Vector getSolution(Matrix m, Vector v) {
        Vector vector = v.copy();
        // Das Gleichungssystem hat keine eindeutige Loesung!
        if (m.getData().length < m.getData()[0].length)
            throw new IllegalArgumentException("Gleichungssystem nicht eindeutig loesbar!");

        // Merken der Spalte, welche eine Zahl ungleich null besitzt
        int tmpColumn = -1;

        // Alle Zeilen durchgehen: Ziel der for-Schleife -> Matrix in
        // Zeilenstufenform bringen!
        // -> Alle Zahlen unterhalb der Diagonale sind null
        for (int line = 0; line < m.getData().length; line++) {
            tmpColumn = -1;

            // Umformungsschritt 1: Finden einer Spalte mit einem Wert ungleich
            // null
            for (int column = 0; column < m.getData()[line].length; column++) {
                for (int row = line; row < m.getData().length; row++) {
                    if (m.get(row, column) != 0) {
                        tmpColumn = column;
                        break;
                    }
                }

                // Abbruch, zahl ungleich null wurde gefunden
                if (tmpColumn != -1) {
                    break;
                }
            }

            // NullZeile(n) entdeckt!
            if (tmpColumn == -1) {
                for (int row = line; row < m.getData().length; row++) {
                    // Gleichungssystem hat keine Loesung!
                    if (vector.get(line) != 0)
                        throw new IllegalArgumentException("Gleichungssystem besitzt keine Loesung!");
                }
                // Nullzeile(n) vorhanden -> Ist das System noch eindeutig
                // loesbar?
                if (m.getData()[0].length - 1 >= line) {
                    // System nicht eindeutig loesbar.
                    throw new IllegalArgumentException("Gleichungssystem nicht eindeutig loesbar!");
                }
                break;
            }

            // Umformungsschritt 2: Die Zahl matrix[line][tmpColumn] soll
            // UNgleich null sein
            if (m.get(line, tmpColumn) == 0) {
                for (int row = line + 1; row < m.getData().length; row++) {
                    if (m.get(row, tmpColumn) != 0) {

                        // Vertauschen von Zeilen -> matrix[line][tmpColumn]
                        // wird dann ungleich null
                        swapTwoLines(line, row, m.getData(), vector);
                        break;
                    }
                }
            }

            // Umformungsschritt 3: matrix[line][tmpColumn] soll gleich 1 sein.
            if (m.get(line, tmpColumn) != 0) {

                // Division der Zeile mit matrix[line][tmpColumn]
                divideLine(line, m.get(line, tmpColumn), m.getData(), vector);
            }

            // Umformungsschritt 4: Alle Zahlen unter matrix[line][tmpColumn]
            // sollen null sein.
            for (int row = line + 1; row < m.getData().length; row++) {

                // Subtraktion damit unter der Zahl im Umformungsschritt 3 nur
                // nullen stehen
                removeRowLeadingNumber(m.get(row, tmpColumn), line, row, m.getData(), vector);
            }
        }

        // Umformungsschritt 6: Matrix in Normalform bringen (Zahlen oberhalb
        // der Diagonale werden ebenfalls zu null)
        for (int column = m.getData()[0].length - 1; column > 0; column--) {

            // Alle Werte oberhalb von "column" werden zu null
            for (int row = column; row > 0; row--) {
                // Dazu wird Subtraktion angewandt
                removeRowLeadingNumber(m.get(row - 1, column), column, row - 1, m.getData(), vector);
            }
        }

        // Unser ehemaliger Loesungsvektor ist jetzt zu unserem Zielvektor
        // geworden :)
        return vector;
    }

    /**
     * Dieser Code stammt von: <a href=
     * "https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     *
     * @param rowOne
     * @param rowTwo
     * @param matrix
     * @param vector
     */
    private static void swapTwoLines(int rowOne, int rowTwo, double[][] matrix, Vector vector) {
        double[] tmpLine;
        double tmpVar;

        tmpLine = matrix[rowOne];
        tmpVar = vector.get(rowOne);

        matrix[rowOne] = matrix[rowTwo];
        vector.set(rowOne, vector.get(rowTwo));

        matrix[rowTwo] = tmpLine;
        vector.set(rowTwo, tmpVar);
    }

    /**
     * Dieser Code stammt von: <a href=
     * "https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     *
     * @param row
     * @param div
     * @param matrix
     * @param vector
     */
    private static void divideLine(int row, double div, double[][] matrix, Vector vector) {
        for (int column = 0; column < matrix[row].length; column++) {
            matrix[row][column] = matrix[row][column] / div;
        }
        vector.set(row, vector.get(row) / div);
    }

    /**
     * Dieser Code stammt von: <a href=
     * "https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     *
     * @param factor
     * @param rowRoot
     * @param row
     * @param matrix
     * @param vector
     */
    private static void removeRowLeadingNumber(double factor, int rowRoot, int row, double[][] matrix, Vector vector) {
        for (int column = 0; column < matrix[row].length; column++) {
            matrix[row][column] = matrix[row][column] - factor * matrix[rowRoot][column];
        }
        vector.set(row, vector.get(row) - factor * vector.get(rowRoot));
    }
}
