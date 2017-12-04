package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;

import controller.ImageController;
import controller.UserController;
import model.Image;
import model.User;

@Named
public class RegistrationMb {
	
	@Inject
	private UserController userCntr;
	
	@Inject 
	private ImageController imgCntl;
	
	private Part file;
	
	private User user = new User();
	
	@NotNull
	private String confirmPass;
	
	public String register(){
		try {
			if(!confirmPass.equals(user.getPassword())){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseñas no coniciden", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}
			Image img = null;
			if(file != null && file.getSize() > 0 && file.getContentType().startsWith("image/")){
				img = imgCntl.upload(file);
			}
			user.setUserpic(img);
			userCntr.create(user);
			user = null;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registró el usuario", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "login?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
	
}
