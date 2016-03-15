package srat

import java.util.UUID

import expenseallocation.dao.InMemoryEmployeeLookup
import expenseallocation.model.{EmployeeTypes, Employee}
import org.scalatest.{FunSpec, Matchers}


class InMemoryEmployeeLookupSpec extends FunSpec with Matchers {

  // Make test easier to read
  implicit def uuidToOption(uuid: UUID) = Some(uuid)


  val topManager = new Employee(name = "TopManager", role = EmployeeTypes.manager)
  val developerTop = new Employee(name = "DeveloperTop", role = EmployeeTypes.developer, managerId = Some(topManager.id))

  val subManager = new Employee(name = "SubManager", role = EmployeeTypes.manager, managerId = Some(topManager.id))
  val developer1 = new Employee(name = "Developer1", role = EmployeeTypes.developer, managerId = Some(subManager.id))
  val developer2 = new Employee(name = "Developer2", role = EmployeeTypes.developer, managerId = Some(subManager.id))

  it("finds by id with results") {
    val lookup = new InMemoryEmployeeLookup(Seq(topManager, subManager, developer1, developer2, developerTop))
    lookup.lookup(topManager.id) should be(Some(topManager))
    lookup.lookup(subManager.id) should be(Some(subManager))
    lookup.lookup(developer1.id) should be(Some(developer1))
  }

  it("finds by id with no results") {
    val lookup = new InMemoryEmployeeLookup(Seq(topManager, subManager, developer1, developer2, developerTop))

    lookup.lookup(null) should be(None)

    new InMemoryEmployeeLookup(Seq()).lookup(UUID.randomUUID()) should be(None)
  }


  it("finds by manager id") {
    val lookup = new InMemoryEmployeeLookup(Seq(topManager, subManager, developer1, developer2, developerTop))
    lookup.lookupByManagerId(developer1.id).size should be(0)
    lookup.lookupByManagerId(developer2.id).size should be(0)
    lookup.lookupByManagerId(developerTop.id).size should be(0)

    lookup.lookupByManagerId(subManager.id).size should be(2)
    lookup.lookupByManagerId(subManager.id) should contain(developer1)
    lookup.lookupByManagerId(subManager.id) should contain(developer2)

    lookup.lookupByManagerId(topManager.id).size should be(2)
    lookup.lookupByManagerId(topManager.id) should contain(developerTop)
    lookup.lookupByManagerId(topManager.id) should contain(subManager)


    lookup.lookupByManagerId(None).size should be(1)
    lookup.lookupByManagerId(None) should contain(topManager)
  }

}
