package qlpt.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "THOIGIAN")
public class ThoiGianEntity {
	@Id
	@GeneratedValue
	private int MATG;
	
	private Integer THANG;
	private Integer NAM;
	
	@OneToMany(mappedBy = "thoiGian",fetch = FetchType.EAGER)
	private Collection<CTDichVuEntity> dsCTDV;

	public ThoiGianEntity(int mATG, Integer tHANG, Integer nAM, Collection<CTDichVuEntity> dsCTDV) {
		super();
		MATG = mATG;
		THANG = tHANG;
		NAM = nAM;
		this.dsCTDV = dsCTDV;
	}
	

	public ThoiGianEntity(Integer tHANG, Integer nAM) {
		super();
		THANG = tHANG;
		NAM = nAM;
	}



	public ThoiGianEntity() {
		super();
	}

	public int getMATG() {
		return MATG;
	}

	public void setMATG(int mATG) {
		MATG = mATG;
	}

	public Integer getTHANG() {
		return THANG;
	}

	public void setTHANG(Integer tHANG) {
		THANG = tHANG;
	}

	public Integer getNAM() {
		return NAM;
	}

	public void setNAM(Integer nAM) {
		NAM = nAM;
	}

	public Collection<CTDichVuEntity> getDsCTDV() {
		return dsCTDV;
	}

	public void setDsCTDV(Collection<CTDichVuEntity> dsCTDV) {
		this.dsCTDV = dsCTDV;
	}
	
	
	
}