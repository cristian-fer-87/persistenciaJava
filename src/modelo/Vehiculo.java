/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Leo
 */
@Entity
@Table(name="vehiculos")
public class Vehiculo implements Serializable{
    @Id
    @Column(name="codigo")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int codigo;

    @Column(name="modelo")
    private String modelo;
    
    @OneToOne
    @JoinColumn(name="motor")
    private Motor motor;
    
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.REMOVE)
    private List<Rueda> ruedas;
    
    public Vehiculo(){}

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public List<Rueda> getRuedas() {
        return ruedas;
    }

    public void setRuedas(List<Rueda> ruedas) {
        this.ruedas = ruedas;
    }

}
