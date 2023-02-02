package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @GetMapping("/")
    public greetResponse greet() {
        greetResponse gr = new greetResponse("Hey", List.of("Java","Golang","JavaScript"), new Person("Joseph", 24, 500000));
        return gr;
    }

    record greetResponse(String greet,
                         List<String> listOfProgrammingLanguages,
                         Person person) {};

    class Person {
        private String name;
        private int age;
        private double savings;

        Person(String name, int age, double savings) {
            this.name = name;
            this.age = age;
            this.savings = savings;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getSavings() {
            return savings;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Double.compare(person.savings, savings) == 0 && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, savings);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", savings=" + savings +
                    '}';
        }
    }
}
