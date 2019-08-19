package br.com.projuris.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.projuris.validator.SquareMatrix;

public class InputZerosOnesMatrix implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@NotNull(message = "The matrix may not be empty.")
	@SquareMatrix
	private String matrix;
	
	public InputZerosOnesMatrix(String matrix) {
		this.matrix = matrix;
	}

	public String getMatrix() {
		return this.matrix;
	}

}
