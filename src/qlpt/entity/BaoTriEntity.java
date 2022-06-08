package qlpt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BAOTRI")
public class BaoTriEntity {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer MABAOTRI;
	private double CHIPHI;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date NGAY;
	private String MOTA;
	
	@ManyToOne
	@JoinColumn(name="MAPHONG")
	private PhongEntity phong;

	public BaoTriEntity(Integer mABAOTRI, double cHIPHI, Date nGAY, String mOTA, PhongEntity phong) {
		super();
		MABAOTRI = mABAOTRI;
		CHIPHI = cHIPHI;
		NGAY = nGAY;
		MOTA = mOTA;
		this.phong = phong;
	}

	public BaoTriEntity() {
		super();
	}

	public Integer getMABAOTRI() {
		return MABAOTRI;
	}

	public void setMABAOTRI(Integer mABAOTRI) {
		MABAOTRI = mABAOTRI;
	}

	public double getCHIPHI() {
		return CHIPHI;
	}

	public void setCHIPHI(double cHIPHI) {
		CHIPHI = cHIPHI;
	}

	public Date getNGAY() {
		return NGAY;
	}

	public void setNGAY(Date nGAY) {
		NGAY = nGAY;
	}

	public String getMOTA() {
		return MOTA;
	}

	public void setMOTA(String mOTA) {
		MOTA = mOTA;
	}

	public PhongEntity getPhong() {
		return phong;
	}

	public void setPhong(PhongEntity phong) {
		this.phong = phong;
	}
	
	
}
