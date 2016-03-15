package expenseallocation.model

import java.util.UUID


object EmployeeTypes {
  val manager = "manager"
  val developer = "developer"
}

case class Employee(id: UUID = UUID.randomUUID(), name: String, role: String, managerId: Option[UUID] = None)

