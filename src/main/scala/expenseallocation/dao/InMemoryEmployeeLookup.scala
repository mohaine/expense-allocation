package expenseallocation.dao

import java.util.UUID

import expenseallocation.EmployeeLookup
import expenseallocation.model.Employee

/**
  * Created by graessle on 3/15/16.
  */
class InMemoryEmployeeLookup(employees: Seq[Employee]) extends EmployeeLookup {

  private lazy val employeesById = employees.map(e => (e.id, e)).toMap

  private lazy val employeesByManagerId = employees.groupBy(e => e.managerId)


  override def lookup(employeeId: UUID): Option[Employee] = employeesById.get(employeeId)

  override def lookupByManagerId(employeeId: Option[UUID]): Seq[Employee] = {
    employeesByManagerId.getOrElse(employeeId, Seq[Employee]())
  }
}
