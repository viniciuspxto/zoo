/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "consultaBean")
@ViewScoped
public class ConsultasBean {
    public String manterConsulta () {
        return "/pages/manter-consulta";
    }
}
