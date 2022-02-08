import RotorManager.buildTypes.*
import RotorManager.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

version = "2020.2"

project {
    vcsRoot(RotorManagerMaster)
    vcsRoot(RotorManagerTest)
    vcsRoot(RotorManagerRelease)
    vcsRoot(RotorManagerAutoTestMerge)

    template(BackendTemplate)
    template(FrontendTemplate)

    params {
        param("env.NUGET_PACKAGES", """C:\NuGet\packages""")
        param("env.NODE_EXTRA_CA_CERTS", """C:\BuildAgent\conf\durr-ca.pem""")
    }

    buildType(PullRequest)
    buildType(ScheduledMerge)
    subProject(DevProject)
    subProject(TestProject)
    subProject(ProdProject)
}

object DevProject : Project({
    name = "Dev"

    buildType(DevBackend)
    buildType(DevFrontend)
})

object TestProject : Project({
    name = "Test"

    buildType(TestBackend)
    buildType(TestFrontend)
})

object ProdProject : Project({
    name = "Prod"

    buildType(ReleaseBackend)
    buildType(ReleaseFrontend)
})
