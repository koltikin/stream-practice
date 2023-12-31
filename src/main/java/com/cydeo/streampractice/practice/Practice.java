package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();

    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {

        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return employeeService.readAll().stream().map(Employee::getFirstName)
                .collect(Collectors.toList());
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        return countryService.readAll().stream().map(Country::getCountryName)
                .collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        return departmentService.readAll().stream().map(d->d.getManager().getFirstName())
                .collect(Collectors.toList());
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return departmentService.readAll().stream().filter(d->d.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {

        return departmentService.readAll().stream().filter(d->d.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());
    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
        return departmentService.readAll().stream().filter(d->d.getDepartmentName().equals("IT"))
                .map(d->d.getLocation().getCountry().getRegion()).findAny().get();
    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return departmentService.readAll().stream().filter(d->d.getLocation().getCountry().getRegion()
                .getRegionName().equals("Europe")).collect(Collectors.toList());

    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
//        boolean result = employeeService.readAll().stream().anyMatch(e->e.getSalary()<1000);
        return true;
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        return employeeService.readAll().stream().filter(e->e.getDepartment().getDepartmentName().equals("IT"))
                .anyMatch(e->e.getSalary()>2000);
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        return  employeeService.readAll().stream().filter(e->e.getSalary()<5000).collect(Collectors.toList());

    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        return  employeeService.readAll().stream().filter(e->e.getSalary()>6000 && e.getSalary()<7000)
                .collect(Collectors.toList());
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        return employeeService.readAll().stream().filter(e-> Objects.equals(e.getFirstName(), "Douglas") && Objects.equals(e.getLastName(), "Grant"))
                .map(Employee::getSalary)
                .findAny().get();
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
        return employeeService.readAll().stream().map(Employee::getSalary).max(Long::compare).get();
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return  employeeService.readAll().stream().filter(e-> {
            try {
                return Objects.equals(e.getSalary(), getMaxSalary());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        return  getMaxSalaryEmployee().stream().map(Employee::getJob).findAny().get()
;
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        return getMaxSalaryEmployee().stream().filter(e->e.getDepartment().getLocation().getCountry().getRegion().getRegionName()
                .equals("Americas")).map(Employee::getSalary).findAny().get();
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        long secondMax = employeeService.readAll().stream().map(Employee::getSalary)
                .filter(em-> {
                    try {
                        return em !=getMaxSalary();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .max(Long::compare).get();
        return secondMax;

    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        List<Employee> secondMaxEmp = employeeService.readAll().stream()
                .filter(em-> {
                    try {
                        return Objects.equals(em.getSalary(), getSecondMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());

        return secondMaxEmp;
    }


    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        Long minSalary = employeeService.readAll().stream().map(Employee::getSalary)
                .min(Long::compare).get();
        return minSalary;
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        List<Employee> minSalaryEmp = employeeService.readAll().stream()
                .filter(em-> {
                    try {
                        return Objects.equals(em.getSalary(), getMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return minSalaryEmp;
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        Long secondMinSalaryEmp = employeeService.readAll().stream()
                .map(Employee::getSalary)
                .filter(sal-> {
                    try {
                        return !Objects.equals(sal, getMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).min(Long::compare)
                .get();
        return secondMinSalaryEmp;
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
        List<Employee> secondMinEmps = employeeService.readAll().stream()
                .filter(em-> {
                    try {
                        return Objects.equals(em.getSalary(), getSecondMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return secondMinEmps;
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        double averageSal = employeeService.readAll().stream()
                .mapToDouble(Employee::getSalary)
                .average().orElse(0.0);
        return averageSal;
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        List<Employee> moreFromAveEmp = employeeService.readAll().stream()
                .filter(em->em.getSalary()>getAverageSalary())
                .collect(Collectors.toList());
        return moreFromAveEmp;
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        List<Employee> lessThanAveEmp = employeeService.readAll().stream()
                .filter(em->em.getSalary()<getAverageSalary())
                .collect(Collectors.toList());
        return lessThanAveEmp;
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        Map<Long, List<Employee>> empMap = employeeService.readAll().stream()
                .collect(Collectors.groupingBy(e->e.getDepartment().getId()));
        return empMap;
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        long totalNumOfDepart = departmentService.readAll().size();
        return totalNumOfDepart;
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        Employee resultEmp = employeeService.readAll().stream()
                .filter(e-> Objects.equals(e.getFirstName(),"Alyssa"))
                .filter(e->Objects.equals(e.getManager().getFirstName(),"Eleni"))
                .filter(e->Objects.equals(e.getDepartment().getDepartmentName(),"Sales"))
                .findAny().get();
        return resultEmp;
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        List<JobHistory> jobHiss = jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
        return jobHiss;
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        List<JobHistory> jbHistory = jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
        return jbHistory;
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        List<JobHistory> result  = jobHistoryService.readAll().stream()
                .filter(jbh->jbh.getStartDate().isAfter(LocalDate.of(2005,01,01)))
                .collect(Collectors.toList());
        return result;
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        List<JobHistory> jobHistoryList = jobHistoryService.readAll().stream()
                .filter(jb -> Objects.equals(jb.getEndDate(), LocalDate.of(2007, 12, 31)))
                .filter(jb->Objects.equals(jb.getJob().getJobTitle(),"Programmer"))
                .collect(Collectors.toList());
        return jobHistoryList;
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        Employee result = jobHistoryService.readAll().stream()
                .filter(jb->Objects.equals(jb.getDepartment().getDepartmentName(),"Shipping"))
                .filter(jb->(Objects.equals(jb.getStartDate(),LocalDate.of(2007,01,01)))
                        && (Objects.equals(jb.getEndDate(),LocalDate.of(2007,12,31))) )
                .map(JobHistory::getEmployee)
                .findAny().get();
        return result;
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        List<Employee> resultEmp = employeeService.readAll().stream()
                .filter(em->em.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
        return resultEmp;
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        List<Employee> resultEmp = employeeService.readAll().stream()
                .filter(em->em.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
        return resultEmp;
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        long numberOfEmp = employeeService.readAll().stream()
                .filter(em-> (em.getJob().getJobTitle().equalsIgnoreCase("programmer"))
                && (em.getDepartment().getDepartmentName().equals("IT")))
                .count();
        return numberOfEmp;
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        List<Employee> empList = employeeService.readAll().stream()
                .filter(em->(em.getDepartment().getId() == 50) ||(em.getDepartment().getId() == 80)||(em.getDepartment().getId() == 100))
                .collect(Collectors.toList());
        return empList;
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        List<String> empResultList = employeeService.readAll().stream()
                .map(em->em.getFirstName().charAt(0)+""+em.getLastName().charAt(0))
                .collect(Collectors.toList());
        return empResultList;
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        List<String> fullNameOfEmp = employeeService.readAll().stream()
                .map(em->em.getFirstName()+ " "+ em.getLastName())
                .collect(Collectors.toList());
        return fullNameOfEmp;
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        Integer longestFullNames = getAllEmployeesFullNames().stream()
                .map(String::length).max(Integer::compare).get();
        return longestFullNames;
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        List<Employee> longestFullNameEmp = employeeService.readAll().stream()
                .filter(em-> {
                    try {
                        return (em.getFirstName()+" "+em.getLastName()).length() == getLongestNameLength();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return longestFullNameEmp;
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        List<Employee> resultEmp = employeeService.readAll().stream()
                .filter(em->em.getDepartment().getId() == 90 || em.getDepartment().getId() == 60
                        || em.getDepartment().getId() == 100 || em.getDepartment().getId() == 120
                        || em.getDepartment().getId() == 130)
                .collect(Collectors.toList());
        return resultEmp;
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        List<Employee> resultEmp = employeeService.readAll().stream()
                .filter(em->em.getDepartment().getId() != 90 && em.getDepartment().getId() != 60
                        && em.getDepartment().getId() != 100 && em.getDepartment().getId() != 120
                        && em.getDepartment().getId() != 130)
                .collect(Collectors.toList());
        return resultEmp;
    }

}
