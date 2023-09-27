package com.devsuperior.demo.services.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.demo.controllers.exceptions.FieldMessage;
import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.entities.Employee;
import com.devsuperior.demo.repositories.EmployeeRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeInsertValidator implements ConstraintValidator<EmployeeInsertValid, EmployeeDTO> {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Override
	public void initialize(EmployeeInsertValid ann) {
	}

	@Override
	public boolean isValid(EmployeeDTO dto, ConstraintValidatorContext context) {
		
List<FieldMessage> list = new ArrayList<>();
		
		Employee emp = repository.findByEmail(dto.getEmail());
		if (emp != null) {
			list.add(new FieldMessage("email", "Email already exists"));
		}
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}