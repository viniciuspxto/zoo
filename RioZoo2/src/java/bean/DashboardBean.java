/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "dashBean")
@ViewScoped
public class DashboardBean {
    public String dashboard () {
        return "/pages/dash";
    }
}
