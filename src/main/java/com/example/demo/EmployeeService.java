package com.example.demo;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository repo;

    // CREATE
    public EmployeeResponse create(EmployeeRequest req) {
        repo.findByEmail(req.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("Email already exists");
        });

        Employee e = new Employee();
        e.setName(req.getName());
        e.setEmail(req.getEmail());
        e.setDepartment(req.getDepartment());

        e = repo.save(e);
        return toResponse(e);
    }

    // LIST
    public List<EmployeeResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    // GET by id
    public EmployeeResponse get(Long id) {
        Employee e = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee " + id + " not found"));
        return toResponse(e);
    }

    // UPDATE
    public EmployeeResponse update(Long id, EmployeeRequest req) {
        Employee e = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee " + id + " not found"));

        if (!e.getEmail().equals(req.getEmail())) {
            repo.findByEmail(req.getEmail()).ifPresent(x -> {
                throw new IllegalArgumentException("Email already exists");
            });
        }

        e.setName(req.getName());
        e.setEmail(req.getEmail());
        e.setDepartment(req.getDepartment());

        e = repo.save(e);
        return toResponse(e);
    }

    // DELETE
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Employee " + id + " not found");
        }
        repo.deleteById(id);
    }

    // Mapper: Entity -> Response DTO
    private EmployeeResponse toResponse(Employee e) {
        EmployeeResponse r = new EmployeeResponse();
        r.setId(e.getId());
        r.setName(e.getName());
        r.setEmail(e.getEmail());
        r.setDepartment(e.getDepartment());
        r.setCreatedAt(e.getCreatedAt());
        r.setUpdatedAt(e.getUpdatedAt());
        return r;
    }
}
