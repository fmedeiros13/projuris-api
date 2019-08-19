package br.com.projuris.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Superficie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String biggest_spot_area;
	
	@NotEmpty
	private String data;
	
	private String number_of_spots;
	
	private String spots_average_area;
	
	private String total_area;
	
	public Superficie () {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBiggestSpotArea() {
		return biggest_spot_area;
	}

	public void setBiggestSpotArea(String biggest_spot_area) {
		this.biggest_spot_area = biggest_spot_area;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNumberOfSpots() {
		return number_of_spots;
	}

	public void setNumberOfSpots(String number_of_spots) {
		this.number_of_spots = number_of_spots;
	}

	public String getSpotsAverageArea() {
		return spots_average_area;
	}

	public void setSpotsAverageArea(String spots_average_area) {
		this.spots_average_area = spots_average_area;
	}

	public String getTotalArea() {
		return total_area;
	}

	public void setTotalArea(String total_area) {
		this.total_area = total_area;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Superficie other = (Superficie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
