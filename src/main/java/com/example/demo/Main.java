package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("{customerId}")
    public Optional<Customer> getCustomer(@PathVariable ("customerId") Integer id) {

        if(customerRepository.findById(id)!=null) {
            return customerRepository.findById(id);
        } else {
            throw new RuntimeException("Bitch there ain't no user.");
        }

    }

    record NewCustomerRequest(String emailAddress, String name, Integer age) {};

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setEmailAddress(request.emailAddress());
        customer.setName(request.name());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable ("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    @PatchMapping("{customerId}")
    public void updateCustomerDetails(@PathVariable ("customerId") Integer id, @RequestBody Map<String, Object> fields) {
        Customer customer = customerRepository.getReferenceById(id);

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Customer.class, key);
                if(field!=null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, customer, value);
                }
            });

            customerRepository.save(customer);
    }

    @PutMapping("{customerId}")
    public void updateAllCustomerDetails(@PathVariable ("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        Customer customer = customerRepository.getReferenceById(id);
        customer.setEmailAddress(request.emailAddress());
        customer.setName(request.name());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
}
