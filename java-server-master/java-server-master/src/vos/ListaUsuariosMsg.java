package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaUsuariosMsg 
{
	@JsonProperty(value="usuariosMsg")
	private List<UsuarioMsg> usuarios;
	
	public ListaUsuariosMsg( @JsonProperty(value="vuelosMsg")List<UsuarioMsg> usuarios){
		this.usuarios = usuarios;
	}

	public List<UsuarioMsg> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioMsg> usuarios) {
		this.usuarios = usuarios;
	}
}
