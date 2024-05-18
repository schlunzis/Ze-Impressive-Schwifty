package org.schlunzis.zis.math;

public interface IMathHelper<A> {
	/**
	 * This function allows to map every value from the matrix with a custom
	 * calculation. Furthermore the row and column of values location are provided,
	 * allowing even more insights.
	 * 
	 * @param value value at the spot
	 * @param row   row index of the spot
	 * @param col   column of the spot
	 * @return new value for the spot
	 */
	A getValue(double value, int row, int col);
}