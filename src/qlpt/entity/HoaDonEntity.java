package qlpt.entity;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "HOADON")
public class HoaDonEntity {
	@Id
	@GeneratedValue
	private int MAHOADON;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date NGAYLAP;
	
	private double THANHTIEN;
	private double TONGPHUTHU;
	private Boolean TRANGTHAI;
	
	@ManyToOne
	@JoinColumn(name="MAHOPDONG")
	private HopDongEntity hopDong;

	public HoaDonEntity(int mAHOADON, Date nGAYLAP, double tHANHTIEN, double tONGPHUTHU,
			Boolean tRANGTHAI, HopDongEntity hopDong) {
		super();
		MAHOADON = mAHOADON;
		NGAYLAP = nGAYLAP;
		THANHTIEN = tHANHTIEN;
		TONGPHUTHU = tONGPHUTHU;
		TRANGTHAI = tRANGTHAI;
		this.hopDong = hopDong;
	}
	public HoaDonEntity(Date nGAYLAP, double tHANHTIEN, double tONGPHUTHU,
			Boolean tRANGTHAI, HopDongEntity hopDong) {
		super();
		NGAYLAP = nGAYLAP;
		THANHTIEN = tHANHTIEN;
		TONGPHUTHU = tONGPHUTHU;
		TRANGTHAI = tRANGTHAI;
		this.hopDong = hopDong;
	}

	public HoaDonEntity() {
		super();
	}

	public int getMAHOADON() {
		return MAHOADON;
	}

	public void setMAHOADON(int mAHOADON) {
		MAHOADON = mAHOADON;
	}

	public Date getNGAYLAP() {
		return NGAYLAP;
	}

	public void setNGAYLAP(Date nGAYLAP) {
		NGAYLAP = nGAYLAP;
	}

	public double getTHANHTIEN() {
		return THANHTIEN;
	}

	public void setTHANHTIEN(double tHANHTIEN) {
		THANHTIEN = tHANHTIEN;
	}

	public double getTONGPHUTHU() {
		return TONGPHUTHU;
	}

	public void setTONGPHUTHU(double tONGPHUTHU) {
		TONGPHUTHU = tONGPHUTHU;
	}

	public Boolean getTRANGTHAI() {
		return TRANGTHAI;
	}

	public void setTRANGTHAI(Boolean tRANGTHAI) {
		TRANGTHAI = tRANGTHAI;
	}

	public HopDongEntity getHopDong() {
		return hopDong;
	}

	public void setHopDong(HopDongEntity hopDong) {
		this.hopDong = hopDong;
	}
	public int getThang() {
		return NGAYLAP.getMonth()+1;
	}
	
	public int getNam() {
		return NGAYLAP.getYear()+1900;
	}
	public Double getTienNha() {
		if (this.getNGAYLAP().getMonth() == this.getHopDong().getNGAYKY().getMonth()
				&& this.getNGAYLAP().getYear() == this.getHopDong().getNGAYKY().getYear()) {
			if (this.getHopDong().getNGAYKY().getDate() < 15) {
				return (double) Math.round(this.getHopDong().getPhong().getLoaiPhong().getDONGIA());

			} else {
				return (double) Math.round(
						this.getHopDong().getPhong().getLoaiPhong().getDONGIA() / this.getHopDong().ngayCuaThang()
								* (this.getHopDong().ngayCuaThang() - this.getHopDong().getNGAYKY().getDate()));
			}
		} else {
			return (double) Math.round(this.getHopDong().getPhong().getLoaiPhong().getDONGIA());
		}
	}
	public int ngayCuaThang() {
		YearMonth yearMonthObject = YearMonth.of(this.NGAYLAP.getYear()+1900, this.getNGAYLAP().getMonth()+1);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		return daysInMonth;
	}
	
}
