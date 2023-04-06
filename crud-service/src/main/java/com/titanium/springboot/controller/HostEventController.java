package com.titanium.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.titanium.springboot.exception.ResourceNotFoundException;
import com.titanium.springboot.model.HostEvent;
import com.titanium.springboot.repository.HostEventRepository;

@RestController
@RequestMapping("/api/v1/")
public class HostEventController {

	@Autowired
	private HostEventRepository hostEventRepository;

	@GetMapping("/hostevents")
	public List<HostEvent> getAllHostEvents() {
		return hostEventRepository.findAll();
	} 

	@PostMapping("/hostevents")
	public HostEvent createHostEvents(@RequestBody HostEvent hostEvent) {
		return hostEventRepository.save(hostEvent);
	}

	@GetMapping("/hostevents/search")
	public ResponseEntity<List<HostEvent>> searchHostsEvents(@RequestParam("data") String date, @RequestParam("time") String time ){
		List<HostEvent> hostEvents = hostEventRepository.findByDateAndTime(date,time);
		if (hostEvents == null) {
			throw new ResourceNotFoundException("Resource not found on server date " + date);
		}
		return ResponseEntity.ok(hostEvents);
	}
	
	@GetMapping("/hostevents/{id}")
	public ResponseEntity<HostEvent> getFoodTruckById(@PathVariable long id) {
		HostEvent hostEvent = hostEventRepository.findById(id).get();
		if (hostEvent == null) {
			throw new ResourceNotFoundException("Resource not found on server id " + id);
		}
		return ResponseEntity.ok(hostEvent);
	}

	@PutMapping("/hostevents/{id}")
	public ResponseEntity<HostEvent> updateFoodTruck(@PathVariable Long id, @RequestBody HostEvent hostEventDetails) {
		HostEvent hostEvent = hostEventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("foodtruck not exist with id :" + id));

		hostEventDetails.setHost(hostEvent.getHost());
		hostEventDetails.setDate(hostEvent.getDate());
		hostEventDetails.setMessage(hostEvent.getMessage());
		hostEventDetails.setTime(hostEvent.getTime());

		HostEvent updatedHostEvent = hostEventRepository.save(hostEventDetails);
		return ResponseEntity.ok(updatedHostEvent);
	}
	
	@DeleteMapping("/hostevents/{id}")
	public ResponseEntity<HostEvent> deleteHostEvent(@PathVariable Long id) {
		HostEvent hostEvent = hostEventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("foodtruck not exist with id :" + id));

		hostEventRepository.deleteById(hostEvent.getId());
		return ResponseEntity.ok(hostEvent);
	}


}
