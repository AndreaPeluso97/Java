package gioco;

import java.io.Serializable;

public class Phrases implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tema;
	private String corpo;

	public Phrases(String corpo,String tema){
	this.corpo=corpo;
	this.tema=tema;
	}
	
	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

}

