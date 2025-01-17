package com.agrestina.repository;

import com.agrestina.domain.client.Client;
import com.agrestina.domain.product.Product;
import com.agrestina.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByName(String name);

    @Query("SELECT c FROM Client c WHERE c.active=true")
    Page<Client> findActive(Pageable pageable);

    @Query("SELECT c FROM Client c WHERE c.active=false")
    Page<Client> findInactive(Pageable pageable);

    @Query("SELECT c FROM Client c WHERE c.name = :name")
    Page<Client> findClientByName(Pageable pageable, String name);

    @Query("SELECT c FROM Client c WHERE c.id = :id")
    Page<Client> findClientById(Pageable pageable, String id);

    @Query("SELECT c FROM Client c")
    Page<Client> findAllClients(Pageable pageable);

}
