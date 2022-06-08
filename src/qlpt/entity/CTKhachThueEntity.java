package qlpt.entity;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CT_KHACHTHUE")
public class CTKhachThueEntity implements Serializable{
	@Id
	@ManyToOne
	@JoinColumn(name = "MAKT")
	private KhachThueEntity khachThue;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "MAHOPDONG")
	private HopDongEntity hopDong;

	private Boolean TRANGTHAI;

	public CTKhachThueEntity() {
		super();
	}

	public CTKhachThueEntity(KhachThueEntity khachThue, HopDongEntity hopDong, Boolean tRANGTHAI) {
		super();
		this.khachThue = khachThue;
		this.hopDong = hopDong;
		TRANGTHAI = tRANGTHAI;
	}

	public KhachThueEntity getKhachThue() {
		return khachThue;
	}

	public void setKhachThue(KhachThueEntity khachThue) {
		this.khachThue = khachThue;
	}

	public HopDongEntity getHopDong() {
		return hopDong;
	}

	public void setHopDong(HopDongEntity hopDong) {
		this.hopDong = hopDong;
	}

	public Boolean getTRANGTHAI() {
		return TRANGTHAI;
	}

	public void setTRANGTHAI(Boolean tRANGTHAI) {
		TRANGTHAI = tRANGTHAI;
	}

}
