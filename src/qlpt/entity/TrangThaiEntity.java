package qlpt.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TRANGTHAI")
public class TrangThaiEntity {
	@Id
	@GeneratedValue
	@Column(name = "MATT")
	private int MATT;
	
	@Column(name = "TENTT")
	private String TENTT;
	
	@OneToMany(mappedBy = "trangThai", fetch = FetchType.EAGER)
	private Collection<PhongEntity> dsPhong;

	public int getMATT() {
		return MATT;
	}

	public void setMATT(int mATT) {
		MATT = mATT;
	}

	public String getTENTT() {
		return TENTT;
	}

	public void setTENTT(String tENTT) {
		TENTT = tENTT;
	}

	public Collection<PhongEntity> getDsPhong() {
		return dsPhong;
	}

	public void setDsPhong(Collection<PhongEntity> dsPhong) {
		this.dsPhong = dsPhong;
	}

	public TrangThaiEntity() {
		super();
	}
	
	
}
