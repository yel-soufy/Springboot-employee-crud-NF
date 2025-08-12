package com.example.demo;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    // CREATE -> 201 + Location header
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid EmployeeRequest req) {
        EmployeeResponse saved = service.create(req);
        return ResponseEntity.created(URI.create("/employees/" + saved.getId()))
                .body(saved);
    }

    // LIST -> 200
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> list() {
        return ResponseEntity.ok(service.list());
    }

    // GET by id -> 200 (404 handled globally)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> get(@PathVariable Long id) {
        EmployeeResponse employee = service.get(id);
        return ResponseEntity.ok(employee);
    }

    // UPDATE -> 200
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid EmployeeRequest req) {
        EmployeeResponse updated = service.update(id, req);
        return ResponseEntity.ok(updated);
    }

    // DELETE -> 204 (no body)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
