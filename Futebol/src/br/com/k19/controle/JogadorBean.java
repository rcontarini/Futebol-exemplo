package br.com.k19.controle;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.com.k19.modelo.Jogador;
import br.com.k19.modelo.Time;
import br.com.k19.modelo.repository.JogadorRepository;
import br.com.k19.modelo.repository.TimeRepository;


@ManagedBean
public class JogadorBean {
	
private Jogador jogador = new Jogador();
	
	private Long timeID;
	
	private List<Jogador> jogadores;
	
	public void adiciona(){
		EntityManager manager = this.getManager();
		TimeRepository timeRepository = new TimeRepository(manager);
		JogadorRepository jogadorRepository = new JogadorRepository(manager);
		
		if(this.timeID != null){
			Time time = timeRepository.procura(this.timeID);
			this.jogador.setTime(time);
		}
		
		if(this.jogador.getId() == null){
			jogadorRepository.adiciona(this.jogador);
		} else {
			jogadorRepository.atualiza(this.jogador);
		}
		
		this.jogador = new Jogador();
		this.jogadores = null;
	}
	
	public void preparaAlteracao(){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long id = Long.parseLong(params.get("id"));
		
		EntityManager manager = this.getManager();
		
		JogadorRepository repository = new JogadorRepository(manager);
		this.jogador = repository.procura(id);
		
	}
	
	public void remove(){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long id = Long.parseLong(params.get("id"));
		
		EntityManager manager = this.getManager();
		
		JogadorRepository repository = new JogadorRepository(manager);
		repository.remove(id);
		this.jogadores = null;
	}
	
	public List<Jogador> getJogadores(){
		if(this.jogadores == null){
			EntityManager manager = this.getManager();
			JogadorRepository repository = new JogadorRepository(manager);
			this.jogadores = repository.getLista();
		}
		
		return this.jogadores;
	}
	
	private EntityManager getManager(){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute("EntityManager");
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Long getTimeID() {
		return timeID;
	}

	public void setTimeID(Long timeID) {
		this.timeID = timeID;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}


}
