package ar.com.fitlandia.fitlandia.utils;

import ar.com.fitlandia.fitlandia.models.EjercicioModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.models.RutinaModel;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;

public class ApplicationGlobal
{

    private static ApplicationGlobal _applicationGlobal = null;

    private EjercicioModel ejercicioSelected;
    private RutinaModel rutinaSelected;
    private LogroModel logroSelected;

    private UsuarioModel usuario;

    public UsuarioModel getUsuario() {
        return usuario;
    }


    //si no hay user logeado devuelvo el fit default, parche temporal(?)
    public String getUsername() {
        if(hayUnUsuarioLogeado())
            return usuario.getUsername();
        else {
            return "fit";
        }

    }

    public boolean hayUnUsuarioLogeado(){
        return getUsuario()!=null;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    private ApplicationGlobal(){

    }

    public static ApplicationGlobal getInstance(){
        if(_applicationGlobal==null)
            _applicationGlobal = new ApplicationGlobal();
        return _applicationGlobal;
    }

    public EjercicioModel getEjercicioSelected() {
        return ejercicioSelected;
    }

    public void setEjercicioSelected(EjercicioModel ejercicioSelected) {
        this.ejercicioSelected = ejercicioSelected;
    }

    public RutinaModel getRutinaSelected() {
        return rutinaSelected;
    }

    public void setRutinaSelected(RutinaModel rutinaSelected) {
        this.rutinaSelected = rutinaSelected;
    }

    public LogroModel getLogroSelected() {
        return logroSelected;
    }

    public void setLogroSelected(LogroModel logroSelected) {
        this.logroSelected = logroSelected;
    }
}
