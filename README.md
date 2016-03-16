## Api implementation:

  expenseallocation.ExpenseAllocationApi

## Dependencies:
    
To calculate an Allocation, the api must be provided an instance of an EmployeeLookup. As sample in Memeory EmployeeLookup has provided at expenseallocation.dao.InMemoryEmployeeLookup

## Example usage

```scala
   val expenseAllocation = new ExpenseAllocationApi(employeeLookup)
   val allocation = expenseAllocation.calculateAllocation(managerA) should be(2100.0)
```

## To run tests:

```
sbt test
```    
    
  
