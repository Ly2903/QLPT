package qlpt.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "KHACHTHUE")
public class KhachThueEntity {
	@Id
	private String MAKT;
	private String HO;
	private String TEN;
	private int NAMSINH;
	private String GIOITINH;
	private String CCCD;
	private String DIACHI;
	private String EMAIL;
	private String SDT;
	private String NGHENGHIEP;
	
	@OneToMany(mappedBy = "khachThueDaiDien",fetch = FetchType.LAZY)
	private Collection<HopDongEntity> dsHopDong;// đây là danh sách những hợp đồng mà khách thuê đó đại diện đứng tên
	
	@OneToMany(mappedBy = "khachThue",fetch = FetchType.EAGER)
	private Collection<CTKhachThueEntity> dsCTKT;

	public KhachThueEntity(String mAKT, String hO, String tEN, int nAMSINH, String gIOITINH, String cCCD, String dIACHI,
			String eMAIL, String sDT, String nGHENGHIEP, Collection<HopDongEntity> dsHopDong,
			Collection<CTKhachThueEntity> dsCTKT) {
		super();
		MAKT = mAKT;
		HO = hO;
		TEN = tEN;
		NAMSINH = nAMSINH;
		GIOITINH = gIOITINH;
		CCCD = cCCD;
		DIACHI = dIACHI;
		EMAIL = eMAIL;
		SDT = sDT;
		NGHENGHIEP = nGHENGHIEP;
		this.dsHopDong = dsHopDong;
		this.dsCTKT = dsCTKT;
	}

	public KhachThueEntity() {
		super();
	}

	public String getMAKT() {
		return MAKT;
	}

	public void setMAKT(String mAKT) {
		MAKT = mAKT;
	}

	public String getHO() {
		return HO;
	}

	public void setHO(String hO) {
		HO = hO;
	}

	public String getTEN() {
		return TEN;
	}

	public void setTEN(String tEN) {
		TEN = tEN;
	}

	public int getNAMSINH() {
		return NAMSINH;
	}

	public void setNAMSINH(int nAMSINH) {
		NAMSINH = nAMSINH;
	}

	public String getGIOITINH() {
		return GIOITINH;
	}

	public void setGIOITINH(String gIOITINH) {
		GIOITINH = gIOITINH;
	}

	public String getCCCD() {
		return CCCD;
	}

	public void setCCCD(String cCCD) {
		CCCD = cCCD;
	}

	public String getDIACHI() {
		return DIACHI;
	}

	public void setDIACHI(String dIACHI) {
		DIACHI = dIACHI;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public String getNGHENGHIEP() {
		return NGHENGHIEP;
	}

	public void setNGHENGHIEP(String nGHENGHIEP) {
		NGHENGHIEP = nGHENGHIEP;
	}

	public Collection<HopDongEntity> getDsHopDong() {
		return dsHopDong;
	}

	public void setDsHopDong(Collection<HopDongEntity> dsHopDong) {
		this.dsHopDong = dsHopDong;
	}

	public Collection<CTKhachThueEntity> getDsCTKT() {
		return dsCTKT;
	}

	public void setDsCTKT(Collection<CTKhachThueEntity> dsCTKT) {
		this.dsCTKT = dsCTKT;
	}
	
	

	
}
