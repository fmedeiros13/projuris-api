package br.com.projuris.rest;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.projuris.model.Superficie;
import br.com.projuris.model.ZerosOnesValidMatrix;
import br.com.projuris.service.SuperficieService;
import br.com.projuris.validator.SquareMatrix;

@Path("/")
@RequestScoped
public class SuperficieRest {
	
	@Inject
	private SuperficieService superficieService;
	
	@SquareMatrix
	private String validMatrix;
	
	private static final Logger LOGGER = Logger.getLogger(SuperficieRest.class.getPackage().getName());
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response createSuperficie(String superficie) {
		
		boolean verifyIfContinue = superficieService.verifyMatrix(superficie);
		
		Superficie data = null;
		
		if (verifyIfContinue == true)
			data = superficieService.doesAnalysis();
		
		if (data != null) {
			
			String status = superficieService.insert(data);
			
			if (status.equalsIgnoreCase("CREATED")) {
				LOGGER.info("'" + data.getData() + "' ---> CREATED INTO DATABASE.");
				
				superficie = "{\"total_area\":" + data.getTotalArea() + ", " +
						"\"number_of_spots\":" + data.getNumberOfSpots() + ", " + 
						"\"spots_average_area\":" + data.getSpotsAverageArea() + ", " + 
						"\"biggest_spot_area\":" + data.getBiggestSpotArea() + "}";
				
				ZerosOnesValidMatrix.getZerosOnesMatrix().clear();
				
				return Response.status(201).entity(superficie).build();
			}
		}
		
		if (verifyIfContinue == false)
			superficie = "Invalid Matrix Format";
		
		if (ZerosOnesValidMatrix.getZerosOnesMatrix() != null)
			ZerosOnesValidMatrix.getZerosOnesMatrix().clear();
		
		//Bad Request . . .
		return Response.status(400).entity(superficie).build();
	}
	
	@DELETE
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSuperficie() {
		
		return Response.status(403).entity("Invalid Method").build();
		
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSuperficies() {
		
		return Response.status(403).entity("Invalid Method").build();
		
    }
	
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSuperficie(@PathParam("id") Long id) {
		
		return Response.status(403).entity("Invalid Method").build();
		
    }
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSuperficie(String superficie) {
		
		return Response.status(403).entity("Invalid Method").build();
		
    }

}
