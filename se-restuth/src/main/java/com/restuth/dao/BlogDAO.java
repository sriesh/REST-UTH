package com.restuth.dao;

import java.util.List;
import com.restuth.entity.BlogPost;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
@Repository
public interface BlogDAO  extends JpaRepository<BlogPost, String> {



}
