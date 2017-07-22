package pe.gob.sunat.recurso2.administracion.siga.firma.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
//T7076DELEGACFIRMA (Nombre de tabla)
public class T7076DelegacFirma implements Serializable{
	private static final long serialVersionUID = 1L;

    private BigDecimal codDelegacion; 		//COD_DELEGACION
    private String codUsurespo;				//COD_USURESPO	
    private String codUsudeleg;				//COD_USUDELEG
    private Date fecInideleg;				//FEC_INIDELEG
    private Date fecFindeleg;				//FEC_FINDELEG
    private String codUuoo;					//COD_UUOO
    private String codRegisdeleg;			//COD_REGISDELEG
    private String desDocref;				//DES_DOCREF
    private String indEstdelega;			//IND_ESTDELEGA
    private String indSuspenci;				//IND_SUSPENCI
    private Date fecSuspencion;				//FEC_SUSPENCION
    private Date fecRegis;					//FEC_REGIS
    private String codUsuregis;				//COD_USUREGIS
    private String dirIpusuregis;			//DIR_IPUSUREGIS
    private Date fecModif;					//FEC_MODIF
    private String codUsumodif;				//COD_USUMODIF
    private String dirIpusumodif;			//DIR_IPUSUMODIF
  //DATOS ADICIONALES
    private String numRegDeleg;				//Numero de registro del delegado
    private String nomUsudeleg;				//Nombre del usuario delegado
    private String uuoo;					//UUOO
    private String desUuoo;					//Descripcion de UUOO
    public String getNumRegDeleg() {
		return numRegDeleg;
	}
	public void setNumRegDeleg(String numRegDeleg) {
		this.numRegDeleg = numRegDeleg;
	}
	public String getNomUsudeleg() {
		return nomUsudeleg;
	}
	public void setNomUsudeleg(String nomUsudeleg) {
		this.nomUsudeleg = nomUsudeleg;
	}
	public String getUuoo() {
		return uuoo;
	}
	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}
	public String getDesUuoo() {
		return desUuoo;
	}
	public void setDesUuoo(String desUuoo) {
		this.desUuoo = desUuoo;
	}
	public BigDecimal getCodDelegacion() {
        return codDelegacion;
    }
    public void setCodDelegacion(BigDecimal codDelegacion) {
        this.codDelegacion = codDelegacion;
    }

    public String getCodUsurespo() {
        return codUsurespo;
    }
    public void setCodUsurespo(String codUsurespo) {
        this.codUsurespo = codUsurespo == null ? null : codUsurespo.trim();
    }

    public String getCodUsudeleg() {
        return codUsudeleg;
    }
    public void setCodUsudeleg(String codUsudeleg) {
        this.codUsudeleg = codUsudeleg == null ? null : codUsudeleg.trim();
    }

    public Date getFecInideleg() {
        return fecInideleg;
    }
    public void setFecInideleg(Date fecInideleg) {
        this.fecInideleg = fecInideleg;
    }

    public Date getFecFindeleg() {
        return fecFindeleg;
    }
    public void setFecFindeleg(Date fecFindeleg) {
        this.fecFindeleg = fecFindeleg;
    }

    public String getCodUuoo() {
        return codUuoo;
    }
    public void setCodUuoo(String codUuoo) {
        this.codUuoo = codUuoo == null ? null : codUuoo.trim();
    }

    public String getCodRegisdeleg() {
        return codRegisdeleg;
    }

    public void setCodRegisdeleg(String codRegisdeleg) {
        this.codRegisdeleg = codRegisdeleg == null ? null : codRegisdeleg.trim();
    }
    public String getDesDocref() {
        return desDocref;
    }
    public void setDesDocref(String desDocref) {
        this.desDocref = desDocref == null ? null : desDocref.trim();
    }
    public String getIndEstdelega() {
        return indEstdelega;
    }
    public void setIndEstdelega(String indEstdelega) {
        this.indEstdelega = indEstdelega == null ? null : indEstdelega.trim();
    }
    public String getIndSuspenci() {
        return indSuspenci;
    }
    public void setIndSuspenci(String indSuspenci) {
        this.indSuspenci = indSuspenci == null ? null : indSuspenci.trim();
    }

    public Date getFecSuspencion() {
        return fecSuspencion;
    }
    public void setFecSuspencion(Date fecSuspencion) {
        this.fecSuspencion = fecSuspencion;
    }
    public Date getFecRegis() {
        return fecRegis;
    }
    public void setFecRegis(Date fecRegis) {
        this.fecRegis = fecRegis;
    }
    public String getCodUsuregis() {
        return codUsuregis;
    }
    public void setCodUsuregis(String codUsuregis) {
        this.codUsuregis = codUsuregis == null ? null : codUsuregis.trim();
    }
    public String getDirIpusuregis() {
        return dirIpusuregis;
    }
    public void setDirIpusuregis(String dirIpusuregis) {
        this.dirIpusuregis = dirIpusuregis == null ? null : dirIpusuregis.trim();
    }
    public Date getFecModif() {
        return fecModif;
    }
    public void setFecModif(Date fecModif) {
        this.fecModif = fecModif;
    }
    public String getCodUsumodif() {
        return codUsumodif;
    }
    public void setCodUsumodif(String codUsumodif) {
        this.codUsumodif = codUsumodif == null ? null : codUsumodif.trim();
    }
    public String getDirIpusumodif() {
        return dirIpusumodif;
    }
    public void setDirIpusumodif(String dirIpusumodif) {
        this.dirIpusumodif = dirIpusumodif == null ? null : dirIpusumodif.trim();
    }
}