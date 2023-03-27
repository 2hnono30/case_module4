package com.cg.repository;

import com.cg.model.Avatar;
import com.cg.model.Product;
import com.cg.model.dto.ProductAvatarDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, String> {
}
