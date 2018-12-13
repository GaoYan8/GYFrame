# GYFrame
源码框架集合

Step 1. Add the JitPack repository to your build file 
gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Copy
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.GaoYan8:GYFrame:Tag'
	}
Copy
Share this release:
