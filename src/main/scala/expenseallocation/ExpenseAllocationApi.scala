package expenseallocation

import expenseallocation.model.Employee


trait EmployeeLookup {
  def lookup(employeeId: String): Employee
  def lookupByManagerId(employeeId: String): Employee
}

class ExpenseAllocationApi(employeeLookup: EmployeeLookup) {

}


