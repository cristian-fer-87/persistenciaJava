/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author cristian
 */
@Entity
@Table(name="estacionamientos")
public class Estacionamiento implements Serializable{
    
    @Id
    @Column(name="codigo")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int codigo;
    
    @Column(name="direccion")
    private String direccion;
    
    @ManyToMany
    	@JoinTable(name="vehiculos_por_estacionamientos",
	 joinColumns=@JoinColumn(name="estacionamiento"),
	 inverseJoinColumns=@JoinColumn(name="vehiculo"))
    private List<Vehiculo>vehiculos;

    public Estacionamiento() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
}
