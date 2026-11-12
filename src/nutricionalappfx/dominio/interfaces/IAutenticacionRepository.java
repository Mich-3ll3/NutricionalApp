/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nutricionalappfx.dominio.interfaces;

import nutricionalappfx.dominio.pojo.Colaborador;
import nutricionalappfx.dominio.pojo.ColaboradorEstatus;

/**
 *
 * @author m1ch3
 */
public interface IAutenticacionRepository {
    public int verificarCredenciales(String noPersonal, String password) throws RuntimeException;
    public ColaboradorEstatus verificarEstatusColaborador(int idColaborador) throws RuntimeException;
    public Colaborador obtenerInformacionColaborador(int idColaborador) throws RuntimeException;
}
