package expenseallocation

import java.util.UUID

import expenseallocation.model.Employee


trait EmployeeLookup {
  def lookup(employeeId: UUID): Option[Employee]

  def lookupByManagerId(employeeId: Option[UUID]): Seq[Employee]

  def cost(role: String): Double
}

class ExpenseAllocationApi(employeeLookup: EmployeeLookup) {
  def calculateAllocation(id: UUID): Double = {
    val e = employeeLookup.lookup(id).getOrElse(throw new RuntimeException("Employee does not exist"))
    calculateAllocation(e)
  }

  def calculateAllocation(e: Employee): Double = {
    val allocationForEmployee = employeeLookup.cost(e.role)
    employeeLookup.lookupByManagerId(Some(e.id)).foldLeft(allocationForEmployee)((sum, e) => {
      sum + calculateAllocation(e.id)
    })
  }
}

