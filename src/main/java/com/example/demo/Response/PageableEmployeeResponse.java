package com.example.demo.Response;

import com.example.demo.Entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableEmployeeResponse {
    List<Integer> list;
    Page<Employee> resultPage;
}
