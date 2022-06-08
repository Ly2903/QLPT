package qlpt.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PHONG")
public class PhongEntity implements Serializable{
	@Id
	@GeneratedValue
	private int MAPHONG;

	private String MOTARIENG;
	
	private String TENPHONG;
	@ManyToOne
	@JoinColumn(name = "MATT")
	TrangThaiEntity trangThai;
	
	@ManyToOne
	@JoinColumn(name = "MANT")
	private NhaTroEntity nhatro;
	
	@ManyToOne
	@JoinColumn(name = "MALOAI")
	private LoaiPhongEntity loaiPhong;
	
	@OneToMany(mappedBy = "phong",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	Collection<HopDongEntity> dsHopDong;

	@OneToMany(mappedBy = "phong",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	Collection<BaoTriEntity> dsBaoTri;
	

	public PhongEntity(int mAPHONG, String mOTARIENG, TrangThaiEntity trangThai, NhaTroEntity nhatro,
			LoaiPhongEntity loaiPhong, Collection<HopDongEntity> dsHopDong, Collection<BaoTriEntity> dsBaoTri) {
		super();
		MAPHONG = mAPHONG;
		MOTARIENG = mOTARIENG;
		this.trangThai = trangThai;
		this.nhatro = nhatro;
		this.loaiPhong = loaiPhong;
		this.dsHopDong = dsHopDong;
		this.dsBaoTri = dsBaoTri;
	}
	
	public PhongEntity(int mAPHONG, String mOTARIENG, String tENPHONG, TrangThaiEntity trangThai, NhaTroEntity nhatro,
			LoaiPhongEntity loaiPhong, Collection<HopDongEntity> dsHopDong, Collection<BaoTriEntity> dsBaoTri) {
		super();
		MAPHONG = mAPHONG;
		MOTARIENG = mOTARIENG;
		TENPHONG = tENPHONG;
		this.trangThai = trangThai;
		this.nhatro = nhatro;
		this.loaiPhong = loaiPhong;
		this.dsHopDong = dsHopDong;
		this.dsBaoTri = dsBaoTri;
	}

	public PhongEntity() {
		super();
	}

	public int getMAPHONG() {
		return MAPHONG;
	}

	public void setMAPHONG(int mAPHONG) {
		MAPHONG = mAPHONG;
	}

	public String getMOTARIENG() {
		return MOTARIENG;
	}

	public void setMOTARIENG(String mOTARIENG) {
		MOTARIENG = mOTARIENG;
	}

	public TrangThaiEntity getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiEntity trangThai) {
		this.trangThai = trangThai;
	}

	public NhaTroEntity getNhatro() {
		return nhatro;
	}

	public void setNhatro(NhaTroEntity nhatro) {
		this.nhatro = nhatro;
	}

	public LoaiPhongEntity getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(LoaiPhongEntity loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

	public Collection<HopDongEntity> getDsHopDong() {
		return dsHopDong;
	}

	public void setDsHopDong(Collection<HopDongEntity> dsHopDong) {
		this.dsHopDong = dsHopDong;
	}

	public Collection<BaoTriEntity> getDsBaoTri() {
		return dsBaoTri;
	}

	public void setDsBaoTri(Collection<BaoTriEntity> dsBaoTri) {
		this.dsBaoTri = dsBaoTri;
	}

	public String getTENPHONG() {
		return TENPHONG;
	}

	public void setTENPHONG(String tENPHONG) {
		TENPHONG = tENPHONG;
	}
	

	
	
}
