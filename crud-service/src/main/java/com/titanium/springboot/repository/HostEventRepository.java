package com.titanium.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.titanium.springboot.model.HostEvent;

@Repository
public interface HostEventRepository extends JpaRepository<HostEvent, Long>{	

	List<HostEvent> findByDateAndTime(String date,String time);
}
