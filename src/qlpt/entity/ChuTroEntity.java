package qlpt.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CHUTRO")
public class ChuTroEntity {
	@Id
	private String MACT;
	private String HO;
	private String TEN;
	private int NAMSINH;
	private String GIOITINH;
	private String CCCD;
	private String DIACHI;
	private String EMAIL;
	private String SDT;
	private String TAIKHOAN;
	private String PASSWORD;
	
	@OneToMany(mappedBy = "chuTro", fetch = FetchType.EAGER)
	private Collection<NhaTroEntity> dsNhaTro;

	public ChuTroEntity(String mACT, String hO, String tEN, int nAMSINH, String gIOITINH, String cCCD, String dIACHI,
			String eMAIL, String sDT, String tAIKHOAN, String pASSWORD, Collection<NhaTroEntity> dsNhaTro) {
		super();
		MACT = mACT;
		HO = hO;
		TEN = tEN;
		NAMSINH = nAMSINH;
		GIOITINH = gIOITINH;
		CCCD = cCCD;
		DIACHI = dIACHI;
		EMAIL = eMAIL;
		SDT = sDT;
		TAIKHOAN = tAIKHOAN;
		PASSWORD = pASSWORD;
		this.dsNhaTro = dsNhaTro;
	}

	public ChuTroEntity() {
		super();
	}

	public String getMACT() {
		return MACT;
	}

	public void setMACT(String mACT) {
		MACT = mACT;
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

	public String getTAIKHOAN() {
		return TAIKHOAN;
	}

	public void setTAIKHOAN(String tAIKHOAN) {
		TAIKHOAN = tAIKHOAN;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public Collection<NhaTroEntity> getDsNhaTro() {
		return dsNhaTro;
	}

	public void setDsNhaTro(Collection<NhaTroEntity> dsNhaTro) {
		this.dsNhaTro = dsNhaTro;
	}
	
	
}
