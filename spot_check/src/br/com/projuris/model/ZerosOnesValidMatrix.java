package br.com.projuris.model;

import java.io.Serializable;
import java.util.Vector;

public class ZerosOnesValidMatrix implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Vector<Vector<String>> zerosOnesMatrix;

	public static Vector<Vector<String>> getZerosOnesMatrix() {
		return zerosOnesMatrix;
	}

	public static void setZerosOnesMatrix(Vector<Vector<String>> zerosOnesMatrix) {
		ZerosOnesValidMatrix.zerosOnesMatrix = zerosOnesMatrix;
	}

}
