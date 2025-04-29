package org.example.remontpro.services;

import org.example.remontpro.dto.EmployeeRequestDTO;
import org.example.remontpro.dto.EmployeeResponseDTO;
import org.example.remontpro.entities.Employee;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.models.Role;
import org.example.remontpro.models.User;
import org.example.remontpro.repositories.EmployeeRepository;
import org.example.remontpro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private EmployeeResponseDTO convertToDto(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUser().getUsername());
        dto.setEmail(employee.getUser().getEmail());
        dto.setFullName(employee.getFullName());
        dto.setQualification(employee.getQualification());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setRole(employee.getUser().getRole().name());
        return dto;
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return convertToDto(employee);
    }

    public Employee createEmployee(EmployeeRequestDTO dto) {
        // 1. Создаем пользователя
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);

        // 2. Создаем сотрудника
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFullName(dto.getFullName());
        employee.setQualification(dto.getQualification());
        employee.setPhoneNumber(dto.getPhoneNumber());

        return employeeRepository.save(employee);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        // Обновляем данные сотрудника
        employee.setFullName(dto.getFullName());
        employee.setQualification(dto.getQualification());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee = employeeRepository.save(employee);

        // Обновляем данные пользователя (если нужно)
        User user = employee.getUser();
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);

        return convertToDto(employee);
    }


    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        // Получаем связанного пользователя
        User user = employee.getUser();


        employeeRepository.delete(employee);
        userRepository.delete(user);

    }
}
