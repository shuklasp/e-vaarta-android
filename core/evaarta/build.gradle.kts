plugins {
    id(ThunderbirdPlugins.Library.kmp)
}

kotlin {
    android {
        namespace = "net.thunderbird.core.evaarta"
    }

    sourceSets {
        commonMain.dependencies {
        }

        commonTest.dependencies {
            implementation(projects.core.testing)
        }
    }
}
