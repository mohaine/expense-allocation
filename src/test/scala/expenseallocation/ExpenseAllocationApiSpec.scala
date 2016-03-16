package srat

import java.util.UUID

import expenseallocation.ExpenseAllocationApi
import expenseallocation.dao.InMemoryEmployeeLookup
import expenseallocation.model.{EmployeeTypes, Employee}
import org.scalatest.{FunSpec, Matchers}


class ExpenseAllocationApiSpec extends FunSpec with Matchers {

  // Make test easier to read
  implicit def uuidToOption(uuid: UUID) = Some(uuid)


  val managerA = new Employee(name = "Manager A", role = EmployeeTypes.manager)
  val managerB = new Employee(name = "Manager B", role = EmployeeTypes.manager, managerId = managerA.id)
  val developer = new Employee(name = "Developer", role = EmployeeTypes.developer, managerId = managerB.id)
  val tester = new Employee(name = "QA Tester", role = EmployeeTypes.tester, managerId = managerB.id)


  val lookupExample = new InMemoryEmployeeLookup(Seq(managerA, managerB, developer, tester), Map(
    (EmployeeTypes.developer, 1000),
    (EmployeeTypes.tester, 500),
    (EmployeeTypes.manager, 300)
  ))


  val expenseAllocation = new ExpenseAllocationApi(lookupExample)


  it("calculates example as given") {
    expenseAllocation.calculateAllocation(managerA.id) should be(2100.0)
    expenseAllocation.calculateAllocation(managerA) should be(2100.0)
  }


  it("calculates for sub") {
    expenseAllocation.calculateAllocation(managerB) should be(2100.0 - 300.0)
  }

  it("npe for null") {
    intercept[NullPointerException] {
      expenseAllocation.calculateAllocation(null.asInstanceOf[Employee])
    }
  }


  it("fails with missing employee id") {
    intercept[Exception] {
      expenseAllocation.calculateAllocation(UUID.randomUUID())
    }.getMessage should be("Employee does not exist")
  }
}
