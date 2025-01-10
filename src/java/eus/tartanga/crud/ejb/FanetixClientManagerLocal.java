/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.FanetixClient;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Irati
 */
@Local
public interface FanetixClientManagerLocal {

    public void createClient(FanetixClient client);

    public void updateClient(FanetixClient client);

    public void removeClient(FanetixClient client);

    public FanetixClient findClient(String email);

    public List<FanetixClient> findAllClients();
}
