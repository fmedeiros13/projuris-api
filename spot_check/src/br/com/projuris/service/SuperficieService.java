package br.com.projuris.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.projuris.model.InputZerosOnesMatrix;
import br.com.projuris.model.Superficie;
import br.com.projuris.model.ZerosOnesValidMatrix;
import br.com.projuris.repository.SuperficieRepository;

@RequestScoped
public class SuperficieService {
	
	private HashMap<String, Integer> numberOfSpotsAndAreas;
	
	@Inject
	private SuperficieRepository superficiesRepository;
	
	@Valid
	private InputZerosOnesMatrix validSuperficie;
	
	private static final Logger LOGGER = Logger.getLogger(SuperficieService.class.getPackage().getName());
	
	public Superficie doesAnalysis() {
		
		this.numberOfSpotsAndAreas = new HashMap<>();
		Superficie superficie = new Superficie();
		boolean zeroValueUpThis = false;
		
		Integer hashMapIndex = 0,
				totalAreaSpot = 0,
				totalAreaAux = 0,
				totalArea = 0;
		
		String key = "";

		for(int i = 0; i < ZerosOnesValidMatrix.getZerosOnesMatrix().size(); i++) {

	        Vector<String> row = ZerosOnesValidMatrix.getZerosOnesMatrix().get(i);
	        
	        for(int j = 0; j < row.size(); j++) {	        	
	        	
	        	// 1 means problems, spots found out...
	        	if (ZerosOnesValidMatrix.getZerosOnesMatrix().get(i).get(j).equalsIgnoreCase("1")) {
	        		totalAreaSpot++;
	        		totalArea++;
	        			
	        		// Research if the spot is bigger than now
	        		// Verify if it could have access to incremented indexes, to avoid null pointer exception
        			if (i > 0) {       				
        				// Try to verify if the matrix value for index row immediately before is 1
	        			if (ZerosOnesValidMatrix.getZerosOnesMatrix().get(i-1).get(j).equalsIgnoreCase("1")) {
	        				key = String.valueOf(i-1).concat(String.valueOf(j));
	        				
	        				// Pre-exits spot...
	        				updateNumberOfSpotsAndAreas(key, totalAreaSpot);
	        				hashMapIndex++;
	        			} else {        				
	        				zeroValueUpThis = true;
	        				totalAreaAux = totalAreaSpot;
	        				totalAreaSpot = 1;
	        				
	        				key = String.valueOf(i).concat(String.valueOf(j));
	        				
	        				// Pre-exits spot...
	        				updateNumberOfSpotsAndAreas(key, totalAreaSpot);
	        				hashMapIndex++;
	        			}
        			} else if (i == 0) {
        				key = String.valueOf(i).concat(String.valueOf(j));
	        			
        				// Pre-exits spot...
        				updateNumberOfSpotsAndAreas(key, totalAreaSpot);
        				hashMapIndex++;
        			}
        			
        			if (j < (row.size()-1)) {
        				// Try to verify if the matrix value for index column immediately after is 1
	        			if (ZerosOnesValidMatrix.getZerosOnesMatrix().get(i).get(j+1).equalsIgnoreCase("1")) {
	        				
	        				while (ZerosOnesValidMatrix.getZerosOnesMatrix().get(i).get(j+1).equalsIgnoreCase("1")) {
	        					
	        					totalArea++;
	        					
	        					if (i > 0) {
	        						if (zeroValueUpThis) {
	        							if (ZerosOnesValidMatrix.getZerosOnesMatrix().get(i-1).get(j+1).equalsIgnoreCase("1")) {
	        								totalAreaSpot = totalAreaSpot + totalAreaAux;        								
	        								Object []keyToRemove = this.numberOfSpotsAndAreas.keySet().toArray();
	        								this.numberOfSpotsAndAreas.remove(keyToRemove[hashMapIndex-2]);
	        								totalAreaAux = 0;
	        							} else {
	        								totalAreaSpot++;
	        							}
	        						} else {
	        							totalAreaSpot++;
	        						}
	        					} else {
	        						totalAreaSpot++;
	        					}
	        					
	        					// Pre-exits spot...
	        					updateNumberOfSpotsAndAreas(key, totalAreaSpot);
		        				hashMapIndex++;
	        					
	        					j++;
	        					
	        					if (j == (row.size()-1))
	        						break;
	        					
	        				}
	        				zeroValueUpThis = false;
	        			}
        			}
	        		
	        	}
	        }
	        
	        if (i == ZerosOnesValidMatrix.getZerosOnesMatrix().size())
	        	row.clear();
	    }
		
		// Setting the returned values... ///////////////////////////////////////////
		superficie.setData(ZerosOnesValidMatrix.getZerosOnesMatrix().toString());
		
		Optional<Integer> maxSpotArea = this.numberOfSpotsAndAreas.values().stream().max((n1, n2) -> n1.compareTo(n2));
		
		if (maxSpotArea.isPresent())
			superficie.setBiggestSpotArea(String.valueOf(maxSpotArea.get()));
		else
			superficie.setBiggestSpotArea("0");
		
		if (String.valueOf(totalArea).equalsIgnoreCase(superficie.getBiggestSpotArea()) && totalArea != 0)
			superficie.setNumberOfSpots(String.valueOf(this.numberOfSpotsAndAreas.size() + 1));
		else
			superficie.setNumberOfSpots(String.valueOf(this.numberOfSpotsAndAreas.size()));
		
		Double spotsAverageArea = 0.0;
				
		if (!superficie.getNumberOfSpots().equalsIgnoreCase("0"))
			spotsAverageArea = Double.valueOf(totalArea)/Double.valueOf(superficie.getNumberOfSpots());
		
		superficie.setSpotsAverageArea(String.valueOf(spotsAverageArea));
		
		superficie.setTotalArea(String.valueOf(totalArea));
		
		this.numberOfSpotsAndAreas.clear();
		
		return superficie;
	}
	
	public String insert(Superficie superficie) {		

		this.superficiesRepository.save(superficie);
		
		Object newSuperficie = this.superficiesRepository.findById(superficie.getId());
		
		if (newSuperficie != null)
			return "CREATED";
		
		return "PROBLEM";
	}
	
// USED BY FERNANDO ONLY TO CHECK/TESTS THE PERSISTENCE ON DATABASE, IT NOT NEED TO BE USED IN THIS EVALUATION
//	public List<Superficie> readAll() {		
//		return (this.superficiesRepository.findAll().isEmpty() ? null : this.superficiesRepository.findAll());
//	}
//	
//	public Object readBy(Long id) {
//		return (this.superficiesRepository.findById(id) != null ? this.superficiesRepository.findById(id) : null);
//	}
	
	public void updateNumberOfSpotsAndAreas(String key, Integer totalAreaSpot) {
		if (this.numberOfSpotsAndAreas.containsKey(key)) {
			this.numberOfSpotsAndAreas.remove(key);
			this.numberOfSpotsAndAreas.put(key, totalAreaSpot);
		} else {
			this.numberOfSpotsAndAreas.put(key, totalAreaSpot);
		}
	}
	
	public boolean verifyMatrix(String superficie) {
		
		boolean result = false;
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<InputZerosOnesMatrix>> violations = validator.validate(this.validSuperficie = new InputZerosOnesMatrix(superficie));
		
		if (violations.size() == 0) {
			LOGGER.info("No violations at the input matrix.");
            result = true;
        } else {
        	violations.stream().forEach(violation -> LOGGER.severe(violation.getMessage()));
        	LOGGER.severe("Invalid Matrix Format: " + superficie);
        }
		
		return result;
	}

}
