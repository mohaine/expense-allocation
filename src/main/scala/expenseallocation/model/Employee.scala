package expenseallocation.model

import java.util.UUID


object EmployeeTypes {
  val manager = "manager"
  val developer = "developer"
  val tester = "tester"
}

case class Employee(id: UUID = UUID.randomUUID(), name: String, role: String, managerId: Option[UUID] = None)

