package expenseallocation

import java.util.UUID

import expenseallocation.model.Employee


trait EmployeeLookup {
  def lookup(employeeId: UUID): Option[Employee]

  def lookupByManagerId(employeeId: Option[UUID]): Seq[Employee]
}

class ExpenseAllocationApi(employeeLookup: EmployeeLookup) {

}


