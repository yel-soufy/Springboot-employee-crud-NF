package com.example.demo;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // CREATE -> 201 + Location header
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid EmployeeRequest req) {
        EmployeeResponse saved = service.create(req);
        return ResponseEntity.created(URI.create("/employees/" + saved.getId()))
                .body(saved);
    }

    // LIST -> 200
    @GetMapping
    public List<EmployeeResponse> list() {
        return service.list();
    }

    // GET by id -> 200 (404 handled by GlobalExceptionHandler)
    @GetMapping("/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    // UPDATE -> 200
    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id,
            @RequestBody @Valid EmployeeRequest req) {
        return service.update(id, req);
    }

    // DELETE -> 204 (no body)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
