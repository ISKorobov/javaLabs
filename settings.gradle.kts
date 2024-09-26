rootProject.name = "lab2"
include("lab2")
include("controller")
findProject(":lab2:controller")?.name = "controller"
include("Service")
findProject(":lab2:Service")?.name = "Service"
include("DAO")
findProject(":lab2:DAO")?.name = "DAO"
include("kitties")
findProject(":lab2:kitties")?.name = "kitties"
include("owners")
findProject(":lab2:owners")?.name = "owners"
