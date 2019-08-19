package br.com.projuris.validator;

import java.util.Vector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.projuris.model.ZerosOnesValidMatrix;

public class SquareMatrixValidator implements ConstraintValidator<SquareMatrix, String> {
    
    @Override
    public boolean isValid(String origMatrix, ConstraintValidatorContext constraintValidatorContext) {
    	
    	boolean resp = true;
    	
    	Vector<Vector<String>>  matrix= new Vector<Vector<String>>();

    	// Removing end lines special characters
    	origMatrix = origMatrix.replaceAll("\\r|\\n", "");
    	
    	// Hope to clean the begin and the end removing special characters
    	origMatrix = origMatrix.replaceAll("'\\[+|‘\\[+|“\\[+|\"\\[+", "[").replaceAll("]'+|]’+|]”+|]\"+", "]");
    	    	
        if ( (origMatrix == null) || (origMatrix.isEmpty()) )
            return false;
        
        // Cleaning up to do the splits and to check up if it is a square matrix
        // Expected Matrix Format like this:
        // [[1, 1, 0, 0], [1, 1, 0, 0], [0, 0, 1, 1], [0, 0, 1, 1]]
        origMatrix = origMatrix.replaceAll(" ", "").replaceAll("\\],\\[+", ";");
        
        // Expected Matrix Format so far:
        // [[1,1,0,0;1,1,0,0;0,0,1,1;0,0,1,1]]
        String [] matrixDimension = origMatrix.split(";");
        
        for (String intString : matrixDimension) {
        	
        	Vector<String> r= new Vector<>();
        	
        	// Removing the [ and ] remaining...
        	intString = intString.replaceAll("\\[+|\\]+", "");
        	
        	String[] vectorDimension = intString.split(",");
        	
        	if (vectorDimension.length != matrixDimension.length) {
        		resp = false;
        		break;
        	}
        	
        	for (int i = 0; i < vectorDimension.length; i++) {        		
        		if ( (!vectorDimension[i].equalsIgnoreCase("0")) && (!vectorDimension[i].equalsIgnoreCase("1")) ) {
            		resp = false;
            		break;
            	}
        		r.add(vectorDimension[i]);
        	}
        	matrix.add(r);
		}
        
        if (resp)
        	ZerosOnesValidMatrix.setZerosOnesMatrix(matrix);
        
        return resp;
    }

}
