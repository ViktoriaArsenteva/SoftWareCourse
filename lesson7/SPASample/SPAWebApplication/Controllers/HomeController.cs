using Microsoft.AspNetCore.Mvc;
using SPAWebApplication.Models;

namespace SPAWebApplication.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Home()
        {
            List<Employee> employees = new List<Employee>();
            employees.Add(new Employee
            {
                Name = "AAAAAA",
                Age = 30,
                Id = 1001
            });
            employees.Add(new Employee
            {
                Name = "BBBBBB",
                Age = 41,
                Id = 1002
            });
            employees.Add(new Employee
            {
                Name = "CCCCCCC",
                Age = 35,
                Id = 1003
            });

            return View(employees);
        }

        public IActionResult About()
        {
            return View();
        }

        public IActionResult Contact()
        {
            return View();
        }
    }
}
