package ar.com.fitlandia.fitlandia.utils;

import ar.com.fitlandia.fitlandia.models.EjercicioModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.models.RutinaModel;

public class ApplicationGlobal
{

    private static ApplicationGlobal _applicationGlobal = null;

    private EjercicioModel ejercicioSelected;
    private RutinaModel rutinaSelected;
    private LogroModel logroSelected;

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
