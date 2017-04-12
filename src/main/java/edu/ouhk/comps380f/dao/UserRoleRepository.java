/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rick Cheung
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
