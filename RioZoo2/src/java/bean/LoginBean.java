/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;



import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.servlet.http.HttpServletResponse;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean {
    public void login() {
        try {
            ((HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().
                    getResponse()).sendRedirect("pages/dash.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
