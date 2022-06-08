package qlpt.entity;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "HOPDONG")
public class HopDongEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int MAHOPDONG;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date NGAYKY;
	
	private double TIENCOC;
	private Boolean DAHUY;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date THOIHAN;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MAKTDAIDIEN",referencedColumnName="MAKT")
	private KhachThueEntity khachThueDaiDien;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MAPHONG")
	private PhongEntity phong;
	
	@OneToMany(mappedBy = "hopDong",fetch = FetchType.EAGER)
	private Collection<HoaDonEntity> dsHoaDon;
	
	@OneToMany(mappedBy = "hopDong",fetch = FetchType.EAGER)
	private Collection<CTDichVuEntity> dsCTDichVu;
	
	@OneToMany(mappedBy = "hopDong",fetch = FetchType.EAGER)
	private Collection<CTKhachThueEntity> dsCTKT;

	public int getMAHOPDONG() {
		return MAHOPDONG;
	}

	public void setMAHOPDONG(int mAHOPDONG) {
		MAHOPDONG = mAHOPDONG;
	}

	public Date getNGAYKY() {
		return NGAYKY;
	}

	public void setNGAYKY(Date nGAYKY) {
		NGAYKY = nGAYKY;
	}

	public double getTIENCOC() {
		return TIENCOC;
	}

	public void setTIENCOC(double tIENCOC) {
		TIENCOC = tIENCOC;
	}

	public Boolean getDAHUY() {
		return DAHUY;
	}

	public void setDAHUY(Boolean dAHUY) {
		DAHUY = dAHUY;
	}

	public Date getTHOIHAN() {
		return THOIHAN;
	}

	public void setTHOIHAN(Date tHOIHAN) {
		THOIHAN = tHOIHAN;
	}



	public KhachThueEntity getKhachThueDaiDien() {
		return khachThueDaiDien;
	}

	public void setKhachThueDaiDien(KhachThueEntity khachThueDaiDien) {
		this.khachThueDaiDien = khachThueDaiDien;
	}

	public PhongEntity getPhong() {
		return phong;
	}

	public void setPhong(PhongEntity phong) {
		this.phong = phong;
	}

	public Collection<HoaDonEntity> getDsHoaDon() {
		return dsHoaDon;
	}

	public void setDsHoaDon(Collection<HoaDonEntity> dsHoaDon) {
		this.dsHoaDon = dsHoaDon;
	}

	public Collection<CTDichVuEntity> getDsCTDichVu() {
		return dsCTDichVu;
	}

	public void setDsCTDichVu(Collection<CTDichVuEntity> dsCTDichVu) {
		this.dsCTDichVu = dsCTDichVu;
	}

	public Collection<CTKhachThueEntity> getDsCTKT() {
		return dsCTKT;
	}

	public void setDsCTKT(Collection<CTKhachThueEntity> dsCTKT) {
		this.dsCTKT = dsCTKT;
	}

	public HopDongEntity() {
		super();
	}

	public HopDongEntity(Date nGAYKY, double tIENCOC, Boolean dAHUY, Date tHOIHAN,
			KhachThueEntity khachThueDaiDien, PhongEntity phong, Collection<HoaDonEntity> dsHoaDon,
			Collection<CTDichVuEntity> dsCTDichVu, Collection<CTKhachThueEntity> dsCTKT) {
		super();
		NGAYKY = nGAYKY;
		TIENCOC = tIENCOC;
		DAHUY = dAHUY;
		THOIHAN = tHOIHAN;
		this.khachThueDaiDien = khachThueDaiDien;
		this.phong = phong;
		this.dsHoaDon = dsHoaDon;
		this.dsCTDichVu = dsCTDichVu;
		this.dsCTKT = dsCTKT;
	}

	public HopDongEntity(Date nGAYKY, double tIENCOC, Boolean dAHUY, Date tHOIHAN, KhachThueEntity khachThueDaiDien,
			PhongEntity phong) {
		super();
		NGAYKY = nGAYKY;
		TIENCOC = tIENCOC;
		DAHUY = dAHUY;
		THOIHAN = tHOIHAN;
		this.khachThueDaiDien = khachThueDaiDien;
		this.phong = phong;
	}
	public HopDongEntity(int mAHOPDONG, Date nGAYKY, double tIENCOC, Boolean dAHUY, Date tHOIHAN,
			KhachThueEntity khachThue, PhongEntity phong, Collection<HoaDonEntity> dsHoaDon,
			Collection<CTDichVuEntity> dsCTDichVu, Collection<CTKhachThueEntity> dsCTKT) {
		super();
		MAHOPDONG = mAHOPDONG;
		NGAYKY = nGAYKY;
		TIENCOC = tIENCOC;
		DAHUY = dAHUY;
		THOIHAN = tHOIHAN;
		this.khachThueDaiDien = khachThue;
		this.phong = phong;
		this.dsHoaDon = dsHoaDon;
		this.dsCTDichVu = dsCTDichVu;
		this.dsCTKT = dsCTKT;
	}

	public List<CTDichVuEntity> getCTDVTheoThangNam(int THANG, int NAM, int MAHOPDONG) {
		List<CTDichVuEntity> a ;
		a = (List<CTDichVuEntity>) getDsCTDichVu();
		List<CTDichVuEntity> k = new ArrayList<CTDichVuEntity>();
		for (CTDichVuEntity x : a) {
			if (x.getThoiGian().getTHANG() == THANG && x.getThoiGian().getNAM() == NAM
					 && x.getHopDong().getMAHOPDONG() == MAHOPDONG) {
				k.add(x);
			}
			System.out.println("dichvu"+x.getDichVu().getMADV());
			System.out.println("hopdong"+x.getHopDong().getMAHOPDONG());
		}
		for (CTDichVuEntity b : k) {
			System.out.println("dichvuuuuuuuuu"+b.getDichVu().getMADV());
			System.out.println("hopdonggggggggg"+b.getHopDong().getMAHOPDONG());
		}
		return k;
		
	}

	public int ngayCuaThang() {
		YearMonth yearMonthObject = YearMonth.of(this.NGAYKY.getYear()+1900, this.NGAYKY.getMonth()+1);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		return daysInMonth;
	}
	
}
