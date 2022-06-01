package com.example.demo.ResController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.demo.Entity.Employee;
import com.example.demo.Exception.NotFoundException;
import com.example.demo.Response.EmployeeResponse;
import com.example.demo.Response.PageableEmployeeResponse;
import com.example.demo.Services.EmployeeServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Users;
import com.example.demo.Services.UserServices;

@RestController
@RequestMapping("get")
public class RestControl {
	
	@Autowired
	UserServices userServices;
	@Autowired
	EmployeeServices employeeServices;

	@GetMapping("say")
	public String employee(){
		return employeeServices.findById(3).get().getName();
	}

	@RequestMapping(value = "employee/{id}",method = RequestMethod.GET,
	produces = {
			MediaType.APPLICATION_JSON_VALUE
	}
	)
	public ResponseEntity<EmployeeResponse> get(@PathVariable Integer id){
            Optional<Employee> employee = employeeServices.findById(id);
			if (employee.isPresent()) {
				EmployeeResponse employeeResponse = new EmployeeResponse();
				BeanUtils.copyProperties(employee.get(), employeeResponse);
				return ResponseEntity.ok(employeeResponse);
			}
			throw new NotFoundException();
	}

	@RequestMapping(value = "employees", method = RequestMethod.GET,
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			}
	)
	public ResponseEntity<PageableEmployeeResponse> search(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
        PageableEmployeeResponse employeeResponse = new PageableEmployeeResponse();
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("name"));
		Page<Employee> resultPage = null;
			resultPage =employeeServices.findAll(pageable);

		int total = resultPage.getTotalPages();
		if (total>0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, total);
			if (total > 5) {
				if (end == total) start = end - 5;
				else if (start == 1) end = start + 5;
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			employeeResponse.setList(pageNumbers);
		}
		employeeResponse.setResultPage(resultPage);
		return ResponseEntity.ok(employeeResponse);
	}


	@RequestMapping(value = "employee/{id}", method = RequestMethod.DELETE,
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			}
	)
	public String dele(@PathVariable Integer id){
 		employeeServices.deleteById(id);
		return "delesuccess";
	}

	@RequestMapping(value = "employee", method = RequestMethod.POST,
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			}
	)
	public String add(@RequestBody Employee employee){
		employeeServices.save(employee);
		return "add success";
	}

	@RequestMapping(value = "employee", method = RequestMethod.PUT,
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			}
	)
	public String update(@RequestBody Employee employee){
		Optional<Employee> optional = employeeServices.findById(employee.getId());
		if (optional.isEmpty()){
			return "Emloyee is not exsis";
		}else {
			employeeServices.save(employee);
			return "update success";
		}
	}
}
