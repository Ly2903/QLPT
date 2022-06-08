package qlpt.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "NHATRO")
public class NhaTroEntity {
	@Id
	private String MANT;
	private String TENNT;
	private String TINH_TP;
	private String QUAN_HUYEN;
	private String PHUONG_XA;
	private String DIACHI;
	
	@ManyToOne
	@JoinColumn(name="MACT")
	private ChuTroEntity chuTro;
	
	@OneToMany(mappedBy = "nhatro",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Collection<PhongEntity> dsPhong;
	
	@OneToMany(mappedBy = "nhaTro", fetch = FetchType.EAGER)
	private Collection<QuyDinhEntity> dsQuyDinh;

	public NhaTroEntity(String mANT, String tENNT, String tINH_TP, String qUAN_HUYEN, String pHUONG_XA, String dIACHI,
			ChuTroEntity chutro, Collection<PhongEntity> dsPhong, Collection<QuyDinhEntity> dsQuyDinh) {
		super();
		MANT = mANT;
		TENNT = tENNT;
		TINH_TP = tINH_TP;
		QUAN_HUYEN = qUAN_HUYEN;
		PHUONG_XA = pHUONG_XA;
		DIACHI = dIACHI;
		this.chuTro = chutro;
		this.dsPhong = dsPhong;
		this.dsQuyDinh = dsQuyDinh;
	}

	public NhaTroEntity() {
		super();
	}

	public String getMANT() {
		return MANT;
	}

	public void setMANT(String mANT) {
		MANT = mANT;
	}

	public String getTENNT() {
		return TENNT;
	}

	public void setTENNT(String tENNT) {
		TENNT = tENNT;
	}

	public String getTINH_TP() {
		return TINH_TP;
	}

	public void setTINH_TP(String tINH_TP) {
		TINH_TP = tINH_TP;
	}

	public String getQUAN_HUYEN() {
		return QUAN_HUYEN;
	}

	public void setQUAN_HUYEN(String qUAN_HUYEN) {
		QUAN_HUYEN = qUAN_HUYEN;
	}

	public String getPHUONG_XA() {
		return PHUONG_XA;
	}

	public void setPHUONG_XA(String pHUONG_XA) {
		PHUONG_XA = pHUONG_XA;
	}

	public String getDIACHI() {
		return DIACHI;
	}

	public void setDIACHI(String dIACHI) {
		DIACHI = dIACHI;
	}

	public ChuTroEntity getChutro() {
		return chuTro;
	}

	public void setChutro(ChuTroEntity chutro) {
		this.chuTro = chutro;
	}

	public Collection<PhongEntity> getDsPhong() {
		return dsPhong;
	}

	public void setDsPhong(Collection<PhongEntity> dsPhong) {
		this.dsPhong = dsPhong;
	}

	public Collection<QuyDinhEntity> getDsQuyDinh() {
		return dsQuyDinh;
	}

	public void setDsQuyDinh(Collection<QuyDinhEntity> dsQuyDinh) {
		this.dsQuyDinh = dsQuyDinh;
	}
	
	
	
}
