package qlpt.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DICHVU")
public class DichVuEntity {
	@Id
	@GeneratedValue
	@Column(name = "MADV")
	private int MADV;

	@Column(name = "TENDV")
	private String TENDV;

	

	@Column(name = "DONVITINH")
	private String DONVITINH;
	
	

	@OneToMany(mappedBy = "dichVu", fetch = FetchType.EAGER)
	private List<QuyDinhEntity> dsQuyDinh;

	@OneToMany(mappedBy = "dichVu", fetch = FetchType.EAGER)
	private Collection<CTDichVuEntity> dsCT;

	public DichVuEntity(int mADV, String tENDV, String dONVITINH, List<QuyDinhEntity> dsQuyDinh,
			Collection<CTDichVuEntity> dsCT) {
		super();
		MADV = mADV;
		TENDV = tENDV;
		DONVITINH = dONVITINH;
		this.dsQuyDinh = dsQuyDinh;
		this.dsCT = dsCT;
	}

	public DichVuEntity() {
		super();
	}

	public int getMADV() {
		return MADV;
	}

	public void setMADV(int mADV) {
		MADV = mADV;
	}

	public String getTENDV() {
		return TENDV;
	}

	public void setTENDV(String tENDV) {
		TENDV = tENDV;
	}

	public String getDONVITINH() {
		return DONVITINH;
	}

	public void setDONVITINH(String dONVITINH) {
		DONVITINH = dONVITINH;
	}

	public List<QuyDinhEntity> getDsQuyDinh() {
		return dsQuyDinh;
	}

	public void setDsQuyDinh(List<QuyDinhEntity> dsQuyDinh) {
		this.dsQuyDinh = dsQuyDinh;
	}

	public Collection<CTDichVuEntity> getDsCT() {
		return dsCT;
	}

	public void setDsCT(Collection<CTDichVuEntity> dsCT) {
		this.dsCT = dsCT;
	}

	public int getQuyDinh(String maNT) {
		for (int i = 0; i < dsQuyDinh.size(); i++) {
			if (dsQuyDinh.get(i).getNhaTro().getMANT().equals(maNT)) {
				return i;
			}
		}
		return -1;
	}

}
